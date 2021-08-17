package com.farmersbyte.enterprise.usermodule.user;

import com.authy.AuthyException;
import com.farmersbyte.enterprise.usermodule.config.RestClient;
import com.farmersbyte.enterprise.usermodule.config.service.MFAService;
import com.farmersbyte.enterprise.usermodule.dto.AuthenticateUserDto;
import com.farmersbyte.enterprise.usermodule.dto.ChangePasswordDto;
import com.farmersbyte.enterprise.usermodule.dto.UserPrincipal;
import com.farmersbyte.enterprise.usermodule.dto.UserTokenDto;
import com.farmersbyte.enterprise.usermodule.exception.DuplicateUserException;
import com.farmersbyte.enterprise.usermodule.exception.UserBusinessException;
import com.farmersbyte.enterprise.usermodule.model.AdminData;
import com.farmersbyte.enterprise.usermodule.model.BaseEntity;
import com.farmersbyte.enterprise.usermodule.user.security.RequestContextUser;
import com.fasterxml.uuid.Generators;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements UserDetailsService {

	public static final String GROCERY_TENANT = new ObjectId("111111111111111111111111").toHexString();
	@Autowired private UserMapper userMapper;
	@Autowired private UserRepository userRepository;
	@Autowired private RequestContextUser requestContextUser;
	@Autowired private MFAService mFAService;
	@Autowired private RestClient restClient;

	@Value("${user.token.url}") private String defaultTokenUrl;
	@Value("${oauth.clientId}") private String clientName;
	@Value("${oauth.clientSecret}") private String clientSecret;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//TODO - How? How to Fix this
//		String organizationId = SystemTenants.getSystemTenant(application);
		User user = userRepository.findOneByOrganizationIdAndUserName(new ObjectId(GROCERY_TENANT), username);
		UserPrincipal principal = new UserPrincipal(user.getUserName(), user.getPwdHash(), getAuthority());
		principal.setUsername(user.getUserName());
		principal.setPhone(user.getPhone());
		principal.setOrganizationId(new ObjectId(GROCERY_TENANT).toString());
		principal.setUserId(user.getId().toString());
		principal.setApplication(GROCERY_TENANT);
		return principal;
	}
	
	public UserDto create(final UserDto userDto, String application) {
		User user = userMapper.dtoToEntity(userDto);
		final UUID uuid = Generators.timeBasedGenerator().generate();
		user.setId(uuid);
		String organizationId = SystemTenants.getSystemTenant(application);
		user.setOrganizationId(new ObjectId(organizationId));
		setAdminData(user);
		user = userRepository.save(user);
		userDto.setId(user.getId().toString());
		return userDto;
	}

	public UserDto createNonOrgUser(final UserDto userDto, String application) {
		User user = userMapper.dtoToEntity(userDto);
		final UUID uuid = Generators.timeBasedGenerator().generate();
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPwdHash(passwordEncoder.encode(userDto.getPwdHash()));
		user.setId(uuid);
		String organizationId = SystemTenants.getSystemTenant(application);
		user.setOrganizationId(new ObjectId(organizationId));
		setAdminData(user);
		user = userRepository.save(user);
		userDto.setId(user.getId().toString());

		return userDto;
	}

	public List<UserDto> findAll(String application) {
		List<UserDto> userDtos = new ArrayList<>();
		String organizationId = SystemTenants.getSystemTenant(application);
		List<User> users = userRepository.findAllByOrganizationId(new ObjectId(organizationId));
		if (!users.isEmpty()) {
			userDtos = users.stream().map(User -> userMapper.entityToDto(User)).collect(Collectors.toList());
		}
		return userDtos;
	}

	public UserDto findById(final String id, String application) {
		String organizationId = SystemTenants.getSystemTenant(application);
		final User user = userRepository.findOneByOrganizationIdAndId(new ObjectId(organizationId), UUID.fromString(id));
		final UserDto userDto = userMapper.entityToDto(user);
		userDto.setId(user.getId().toString());
		return userDto;
	}

	public UserDto update(final String id, final UserDto dto, String application) {
		String organizationId = SystemTenants.getSystemTenant(application);
		User user = userMapper.dtoToEntity(dto);
		user.setId(UUID.fromString(id));
		User oldImage = userRepository.findOneByOrganizationIdAndId(new ObjectId(organizationId), user.getId());
		user.setAdminData(oldImage.getAdminData());
		user.setOrganizationId(new ObjectId(organizationId));
		user.setDbId(oldImage.getDbId());
		updateAdminData(user);
		userRepository.save(user);
		dto.setId(user.getId().toString());
		return dto;
	}

	public void updateAdminData(final User currImage) {
		currImage.getAdminData().setUpdatedBy(currImage.getId());
		currImage.getAdminData().setUpdatedOn(Instant.now());
	}

	public void delete(String id, String application) {
		String organizationId = SystemTenants.getSystemTenant(application);
		final User user = userRepository.findOneByOrganizationIdAndId(
				new ObjectId(organizationId), UUID.fromString(id));
		userRepository.delete(user);
	}

	private void setAdminData(final BaseEntity baseEntity) {
		
		baseEntity.setAdminData(AdminData.builder()
				.createdBy(baseEntity.getId()).createdOn(Instant.now())
				.updatedBy(baseEntity.getId()).updatedOn(Instant.now())
				.build());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	public UserDto resetPassword(String userId, String application) {
		UserDto userDto = null;
		String organizationId = SystemTenants.getSystemTenant(application);
		User user = userRepository.findOneByOrganizationIdAndId(new ObjectId(organizationId), UUID.fromString(userId));
		if (user != null) {
			userDto = userMapper.entityToDto(user);
			userDto = setDefaultPassword(userDto);
//			userDto = save(userDto);
			userDto = update(user.getId().toString(), userDto, application);
		} else {
			throw new UsernameNotFoundException("user not found with userId::" + userId);
		}
		return userDto;
	}
	
	public UserDto resetPasswordByName(String username, String application) {
		UserDto userDto = null;
		String organizationId = SystemTenants.getSystemTenant(application);
		User user = userRepository.findOneByOrganizationIdAndUserName(new ObjectId(organizationId), username);
		if (user != null) {
			userDto = userMapper.entityToDto(user);
			userDto = setDefaultPassword(userDto);
//			userDto = save(userDto);
			userDto = update(user.getId().toString(), userDto, application);
		} else {
			throw new UsernameNotFoundException("user not found with userId::" + username);
		}
		return userDto;
	}

	public UserDto changePassword(ChangePasswordDto changePasswordDto, String application) {
		UserDto userDto = null;
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String organizationId = SystemTenants.getSystemTenant(application);
		User user = userRepository.findOneByOrganizationIdAndUserName(new ObjectId(organizationId),
				changePasswordDto.getUserName());
		if (null != user) {
			userDto = userMapper.entityToDto(user);
			if (passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPwdHash())) {
				userDto.setPwdHash(passwordEncoder.encode(changePasswordDto.getNewPassword()));
//				user = userMapper.dtoToEntity(userDto);
//				updatedUser = userRepository.save(user);
				userDto = update(user.getId().toString(), userDto, application);
			} else {
				throw new UserDeniedAuthorizationException("User old password authentication failed");
			}
		} else {
			throw new UsernameNotFoundException("User not found");
		}
		return userDto;
	}

	public UserDto save(UserDto userDto) {
		User user = userMapper.dtoToEntity(userDto);
		user = userRepository.save(user);
		return userMapper.entityToDto(user);
	}

	public UserDto setDefaultPassword(UserDto userDto) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		userDto.setPwdHash(passwordEncoder.encode("farmer@123"));
		return userDto;
	}

	public UserSignupDto userSignUpWithMfa(UserSignupDto userSignupDto, String application) {
		String organizationId = SystemTenants.getSystemTenant(application);
		User user = userRepository.findOneByOrganizationIdAndUserName(new ObjectId(organizationId),
				userSignupDto.getUserName());
		if (user == null) {
			if (StringUtils.isNotBlank(userSignupDto.getUserName()) && StringUtils.isNotBlank(userSignupDto.getUserName())
					&& StringUtils.isNotBlank(userSignupDto.getPhone()) && StringUtils.isNotBlank(userSignupDto.getCountryCode())) {
				int userId = 0;
				try {
					userId = mFAService.sendOTP(userSignupDto.getEmail(), userSignupDto.getPhone(),
							userSignupDto.getCountryCode(),"SMS");
				} catch (AuthyException e) {
					throw new UserBusinessException("sending OTP failed with error::" + e.getMessage());
				}
				userSignupDto.setMfaUserId(userId);
			}
		} else {
			throw new DuplicateUserException("User already exists with the given username::" + userSignupDto.getUserName());
		}
		return userSignupDto;
	}

	public UserSignupDto userSignUp(UserSignupDto userSignupDto, String application) {
		String organizationId = SystemTenants.getSystemTenant(application);
		User user = userRepository.findOneByOrganizationIdAndUserName(new ObjectId(organizationId),
				userSignupDto.getUserName());
		if (user == null) {
			if (StringUtils.isNotBlank(userSignupDto.getUserName()) && StringUtils.isNotBlank(userSignupDto.getUserName())
					&& StringUtils.isNotBlank(userSignupDto.getPhone()) && StringUtils.isNotBlank(userSignupDto.getCountryCode())) {
				userSignupDto.setMfaUserId(0);
			}
		} else {
			throw new DuplicateUserException("User already exists with the given username::" + userSignupDto.getUserName());
		}
		return userSignupDto;
	}

	public UserTokenDto userSignUpVerificationWithMFA(UserSignupDto userSignupDto, String application) {
		UserTokenDto userTokenDto = null;
		try {
			String organizationId = SystemTenants.getSystemTenant(application);
			User user = userRepository.findOneByOrganizationIdAndUserName(new ObjectId(organizationId),
					userSignupDto.getUserName());
			if (user == null) {
				if (StringUtils.isNotBlank(userSignupDto.getUserName()) && StringUtils.isNotBlank(userSignupDto.getUserName())
						&& StringUtils.isNotBlank(userSignupDto.getPhone())
						&& StringUtils.isNotBlank(userSignupDto.getCountryCode())) {
					boolean isvalid = mFAService.verifyToken(
							userSignupDto.getMfaUserId(),
							userSignupDto.getMfaToken());
					if (isvalid) {
						UserDto userDto = UserDto.builder().userName(userSignupDto.getUserName())
								.email(userSignupDto.getEmail())
								.phone(userSignupDto.getPhone())
								.pwdHash(userSignupDto.getPassword())
								.countryCode(userSignupDto.getCountryCode()).build();

						UserDto newuserDto = createNonOrgUser(userDto, application);
						if (StringUtils.isNotBlank(newuserDto.getId())) {
							userTokenDto = autheticateUser(newuserDto.getUserName(), newuserDto.getPwdHash(), application);
						}
					} else {
						throw new UserBusinessException("Invalid OTP");
					}
				}
			} else {
				throw new DuplicateUserException("User already exists with the given username::" + userSignupDto.getUserName());
			}
		} catch (AuthyException e) {
			throw new UserBusinessException("OTP verification failed with error::" + e.getMessage());
		}
		return userTokenDto;
	}

	public UserTokenDto userSignUpVerification(UserSignupDto userSignupDto, String application) {
		UserTokenDto userTokenDto = null;
		String organizationId = SystemTenants.getSystemTenant(application);
		User user = userRepository.findOneByOrganizationIdAndUserName(new ObjectId(organizationId),
				userSignupDto.getUserName());

		if (user == null) {
				if (StringUtils.isNotBlank(userSignupDto.getUserName()) && StringUtils.isNotBlank(userSignupDto.getUserName())
						&& StringUtils.isNotBlank(userSignupDto.getPhone())
						&& StringUtils.isNotBlank(userSignupDto.getCountryCode())) {
					UserDto userDto = UserDto.builder().userName(userSignupDto.getUserName())
								.email(userSignupDto.getEmail())
								.phone(userSignupDto.getPhone())
								.pwdHash(userSignupDto.getPassword())
								.countryCode(userSignupDto.getCountryCode()).build();
						UserDto newuserDto = createNonOrgUser(userDto, application);

						if (StringUtils.isNotBlank(newuserDto.getId())) {
							userTokenDto = autheticateUser(newuserDto.getUserName(), newuserDto.getPwdHash(), application);
						}
				}
			} else {
				throw new DuplicateUserException("User already exists with the given username::" + userSignupDto.getUserName());
			}
		return userTokenDto;
	}

	public UserTokenDto autheticateUser(String userName, String password, String application) {
		String response = "";
		String organizationId = SystemTenants.getSystemTenant(application);
		User user = userRepository.findOneByOrganizationIdAndUserName(new ObjectId(organizationId), userName);
		UserDto userDto = userMapper.entityToDto(user);
		if (userDto != null) {
			try {
				//TODO: Need confirmation. Refactor the code
			response = invokeOauthToken(userName, password);
			} catch (Exception e) {
				throw new UserBusinessException(ExceptionUtils.getRootCauseMessage(e));
			}
			userDto.setUserTokenDetails(buildUserToken(response));
		} else {
			throw new UsernameNotFoundException("User not found with the username::" + userName);
		}
		userDto.getUserTokenDetails().setUserId(user.getId());
		return userDto.getUserTokenDetails();
	}
	
	public UserTokenDto userRefreshToken(String refreshToken) {
		UserTokenDto userTokenDto = null;
		try {
			String response = invokeOauthRefreshToken(refreshToken);
			userTokenDto = buildUserToken(response);
			} catch (Exception e) {
				throw new UserBusinessException(ExceptionUtils.getRootCauseMessage(e));
			}
		return userTokenDto;
	}

	private String invokeOauthToken(String userName, String password) {
		AuthenticateUserDto authenticateUserdto = new AuthenticateUserDto();
		authenticateUserdto.setUsername(userName);
		authenticateUserdto.setPassword(password);
		authenticateUserdto.setGrant_type("password");
		String requestQuery = String.format("username=%s&password=%s&grant_type=password", userName, password);
		String response = null;
		try {
			response = restClient.postForOauth(defaultTokenUrl, clientName, clientSecret, requestQuery,
					MediaType.APPLICATION_FORM_URLENCODED, String.class);
		} catch (Exception e) {
			throw new UserBusinessException("user authentication failed::" + ExceptionUtils.getRootCauseMessage(e));
		}
		return response;
	}
	
	private String invokeOauthRefreshToken(String token) {
		String requestQuery = String.format("refresh_token=%s&grant_type=refresh_token", token);
		String response = null;
		try {
			response = restClient.postForOauth(defaultTokenUrl, clientName, clientSecret, requestQuery,
					MediaType.APPLICATION_FORM_URLENCODED, String.class);
		} catch (Exception e) {
			throw new UserBusinessException("user authentication failed::" + ExceptionUtils.getRootCauseMessage(e));
		}
		return response;
	}

	private UserTokenDto buildUserToken(String response) {
		JSONObject json = new JSONObject(response);
		String access_token = (String) json.get("access_token");
		String token_type = (String) json.get("token_type");
		String refresh_token = (String) json.get("refresh_token");
		String scope = (String) json.get("scope");
		Integer expires_in = (Integer) json.get("expires_in");
		UserTokenDto userTokenDTO = new UserTokenDto();
		userTokenDTO.setAccessToken(access_token);
		userTokenDTO.setRefreshToken(refresh_token);
		userTokenDTO.setTokenType(token_type);
		userTokenDTO.setScope(scope);
		userTokenDTO.setExpiresIn(expires_in);
		return userTokenDTO;
	}
}

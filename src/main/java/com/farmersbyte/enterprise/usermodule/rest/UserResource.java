package com.farmersbyte.enterprise.usermodule.rest;

import com.farmersbyte.enterprise.usermodule.dto.AuthenticateUserDto;
import com.farmersbyte.enterprise.usermodule.dto.ChangePasswordDto;
import com.farmersbyte.enterprise.usermodule.dto.UserTokenDto;
import com.farmersbyte.enterprise.usermodule.user.UserDto;
import com.farmersbyte.enterprise.usermodule.user.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Api(value = "User Management API", tags = {"User Management API"})
@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserResource {
	
	@Autowired private UserService userService;
	@Autowired private TokenStore tokenStore;

	@GetMapping(value = "/principal")
	public ResponseEntity<Principal> getUserPrincipal
			(@RequestHeader("Authorization") String token, Principal principal,
			 @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
		return new ResponseEntity<Principal>(principal, HttpStatus.OK);
	}
	
	@PostMapping(path = "/changePassword")
	public UserDto changePassword(
			@RequestHeader("Authorization") String token,
			@RequestBody ChangePasswordDto changePasswordDto,
			@RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
		UserDto  userDto = userService.changePassword(changePasswordDto, application);
		return userDto;
	}
	
	@PostMapping(path = "/{userId}/resetPassword")
	public UserDto resetPassword(
			@RequestHeader("Authorization") String token,
			@PathVariable("userId") String userId,
			@RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
		UserDto userDto =  userService.resetPassword(userId, application);
		return userDto;
	}
	
	@PostMapping(path = "/resetPassword")
	public UserDto resetPasswordByName(
			@RequestBody AuthenticateUserDto dto,
			@RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
		UserDto userDto = userService.resetPasswordByName(dto.getUsername(), application);
		return userDto;
	}

	@GetMapping("/refreshToken/{refreshToken}")
	public UserTokenDto refreshToken(
			@RequestHeader("Authorization") String token,
			@PathVariable(name="refreshToken", required = true) String refreshToken) {
		return userService.userRefreshToken(refreshToken);
	}
	
	@DeleteMapping("/logout")
	public void revokeToken(@RequestHeader(name = "Authorization", required = true) String authToken,
							@RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
		if (null != SecurityContextHolder.getContext().getAuthentication()) {
			String tokenValue = ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication()
					.getDetails()).getTokenValue();
			if (null != tokenValue) {
				OAuth2AccessToken token = tokenStore.readAccessToken(tokenValue);
				tokenStore.removeAccessToken(token);
			}
		}
	}
	
}

package com.farmersbyte.enterprise.usermodule.rest;

import com.farmersbyte.enterprise.usermodule.dto.UserTokenDto;
import com.farmersbyte.enterprise.usermodule.exception.DuplicateUserException;
import com.farmersbyte.enterprise.usermodule.user.UserService;
import com.farmersbyte.enterprise.usermodule.user.UserSignupDto;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "User Authentication API", tags = {"User Authentication API"})
@RestController
@CrossOrigin
@RequestMapping("/api/v2/user")
@Slf4j
public class AuthenticationServiceApi {
	
	@Autowired private UserService userService;
	
    @PostMapping("signup")
    public ResponseEntity<UserSignupDto> signup(
            @RequestBody UserSignupDto userSignupDto,
            @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
        try {
            userService.userSignUp(userSignupDto, application);
        } catch (DuplicateUserException dup) {
            log.error(dup.getMessage(), dup);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(userSignupDto);
    }
    
    @PostMapping("signup/verify")
    public ResponseEntity<UserTokenDto> verify(
            @RequestBody UserSignupDto userSignupDto,
            @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application)
    {
        UserTokenDto token = null;
        try {
            token = userService.userSignUpVerification(userSignupDto, application);
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(token);
    }

    @PostMapping("signupMfa")
    public ResponseEntity<UserSignupDto> signupMfa(
            @RequestBody UserSignupDto userSignupDto,
            @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
        try {
            userService.userSignUpWithMfa(userSignupDto, application);
        } catch (DuplicateUserException dup) {
            log.error(dup.getMessage(), dup);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(userSignupDto);
    }

    @PostMapping("signupMfa/verify")
    public ResponseEntity<UserTokenDto> verifyMfa(
            @RequestBody UserSignupDto userSignupDto,
            @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
        UserTokenDto token = null;
        try {
            token = userService.userSignUpVerificationWithMFA(userSignupDto, application);
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(token);
    }
    
    @PostMapping("signin")
    public ResponseEntity<UserTokenDto> signin(
            @RequestBody UserSignupDto userDto,
            @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
        UserTokenDto token = null;
        try {
            token = userService.autheticateUser(userDto.getUserName(), userDto.getPassword(), application);
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(token);
    }
}

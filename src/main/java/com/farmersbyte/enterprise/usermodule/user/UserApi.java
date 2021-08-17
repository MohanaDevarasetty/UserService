package com.farmersbyte.enterprise.usermodule.user;

import com.farmersbyte.enterprise.usermodule.user.family.FamilyService;
import com.farmersbyte.enterprise.usermodule.user.preferences.UserPreferenceDto;
import com.farmersbyte.enterprise.usermodule.user.preferences.UserPreferenceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "User API", tags = {"User API"})
@RestController
@RequestMapping("fb/v1/user-service")
public class UserApi {

    @Autowired private UserService userService;
    @Autowired private UserPreferenceService userPreferenceService;
    @Autowired private FamilyService familyService;

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getById(
            @PathVariable String id,
            @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
        return userService.findById(id, application);
    }

    @ApiOperation(value = "Update details of existing user (by id)")
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto update(
            @PathVariable String id, @Validated @RequestBody UserDto dto,
            @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
        return userService.update(id, dto, application);
    }

    @ApiOperation(value = "Get userpreferences based on filter")
    @GetMapping("/users/{userId}/userpreferences")
    @ResponseStatus(HttpStatus.OK)
    public List<UserPreferenceDto> getUserPreferences(
            @PathVariable String userId,
            @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
        return userPreferenceService.findAll(userId);
    }

    @GetMapping("/userpreferences/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserPreferenceDto getByUserPreferenceId(
            @PathVariable String id,
            @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
        return userPreferenceService.findById(id);
    }

    @ApiOperation(value = "Update details of existing userpreferences (by id)")
    @PutMapping("/userpreferences/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserPreferenceDto updateUserPreference(
            @PathVariable String id,
            @Validated @RequestBody UserPreferenceDto dto,
            @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
        return userPreferenceService.update(id, dto);
    }
}

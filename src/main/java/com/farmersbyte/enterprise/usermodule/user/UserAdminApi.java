package com.farmersbyte.enterprise.usermodule.user;

import com.farmersbyte.enterprise.usermodule.user.family.FamilyDto;
import com.farmersbyte.enterprise.usermodule.user.family.FamilyService;
import com.farmersbyte.enterprise.usermodule.user.preferences.UserPreferenceDto;
import com.farmersbyte.enterprise.usermodule.user.preferences.UserPreferenceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "User API", tags = {"User API"})
@RestController
@RequestMapping("fb/v1/user-service/admin")
public class UserAdminApi {

    @Autowired private UserService userService;
    @Autowired private UserPreferenceService userPreferenceService;
    @Autowired private FamilyService familyService;

    @ApiOperation(value = "Create a user")
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(
            @Validated @RequestBody UserDto dto,
            @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
        return userService.create(dto, application);
    }

    @ApiOperation(value = "Get users based on filter")
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> get(
            @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application
    ) {
        return userService.findAll(application);
    }

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

    @ApiOperation(value = "Delete a user")
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(
            @PathVariable String id,
            @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
        userService.delete(id, application);
    }

    // CRUD Family
    @ApiOperation(value = "Create a family")
    @PostMapping("/users/{id}/family")
    @ResponseStatus(HttpStatus.CREATED)
    public FamilyDto createFamily(
            @Validated @RequestBody FamilyDto dto,
            @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
        return familyService.create(dto);
    }

    @ApiOperation(value = "Get family based on filter")
    @GetMapping("/users/{userId}/family")
    @ResponseStatus(HttpStatus.OK)
    public List<FamilyDto> getFamily(
            @PathVariable String userId,
            @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
        return familyService.findAll(userId);
    }

    @GetMapping("/users/{id}/family/{familyId}")
    @ResponseStatus(HttpStatus.OK)
    public FamilyDto getByFamilyId(
            @PathVariable String familyId,
            @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
        return familyService.findById(familyId);
    }

    @ApiOperation(value = "Update details of existing family (by id)")
    @PutMapping("/users/{id}/family/{familyId}")
    @ResponseStatus(HttpStatus.OK)
    public FamilyDto update(
            @PathVariable String id, @PathVariable String familyId,
            @Validated @RequestBody FamilyDto dto,
            @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
        return familyService.update(familyId, dto);
    }

    @ApiOperation(value = "Delete a family")
    @DeleteMapping("/users/{id}/family/{familyId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteByFamilyId(
            @PathVariable String familyId,
            @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
        familyService.delete(familyId);
    }


    // CRUD UserPreference
    @ApiOperation(value = "Create a userpreferences")
    @PostMapping("/userpreferences")
    @ResponseStatus(HttpStatus.CREATED)
    public UserPreferenceDto createUserPreference(
            @Validated @RequestBody UserPreferenceDto dto,
            @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
        return userPreferenceService.create(dto);
    }

    @ApiOperation(value = "Get userpreferences based on filter")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "family id", required = false, dataType = "String", paramType = "query")})
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

    @ApiOperation(value = "Delete a userpreferences")
    @DeleteMapping("/userpreferences/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteByUserPreferenceId(
            @PathVariable String id,
            @RequestParam(value = "application", required = false, defaultValue = "FarmersByteDirect") String application) {
        userPreferenceService.delete(id);
    }

}

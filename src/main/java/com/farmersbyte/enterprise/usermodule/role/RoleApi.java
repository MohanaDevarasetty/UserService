package com.farmersbyte.enterprise.usermodule.role;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Role API", tags = {"Role API"})
@RestController
@RequestMapping("fb/v1/role-service")
public class RoleApi {

    @Autowired private RoleService roleService;

    @ApiOperation(value = "Create a role")
    @PostMapping("/roles")
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDto create(@Validated @RequestBody RoleDto dto) {
        return roleService.create(dto);
    }

    @ApiOperation(value = "Get roles based on filter")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "role id", required = false, dataType = "String", paramType = "query")})
    @GetMapping("/roles")
    @ResponseStatus(HttpStatus.OK)
    public List<RoleDto> get() {
        return roleService.findAll();
    }

    @GetMapping("/roles/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoleDto getById(@PathVariable String id) {
        return roleService.findById(id);
    }

    @ApiOperation(value = "Update details of existing role (by id)")
    @PutMapping("/roles/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoleDto update(
            @PathVariable String id, @Validated @RequestBody RoleDto dto) {
        return roleService.update(id, dto);
    }

    @ApiOperation(value = "Delete a role")
    @DeleteMapping("/roles/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id) {
        roleService.delete(id);
    }
}

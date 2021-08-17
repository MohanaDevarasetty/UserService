package com.farmersbyte.enterprise.usermodule.policy;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Policy API", tags = {"Policy API"})
@RestController
@RequestMapping("fb/v1/policy-service")
public class PolicyApi {

    @Autowired private PolicyService policyService;

    @ApiOperation(value = "Create a policy")
    @PostMapping("/policies")
    @ResponseStatus(HttpStatus.CREATED)
    public PolicyDto create(@Validated @RequestBody PolicyDto dto) {
        return policyService.create(dto);
    }

    @ApiOperation(value = "Get policies based on filter")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "policy id", required = false, dataType = "String", paramType = "query")})
    @GetMapping("/policies")
    @ResponseStatus(HttpStatus.OK)
    public List<PolicyDto> get() {
        return policyService.findAll();
    }

    @GetMapping("/policies/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PolicyDto getById(@PathVariable String id) {
        return policyService.findById(id);
    }

    @ApiOperation(value = "Update details of existing policy (by id)")
    @PutMapping("/policies/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PolicyDto update(
            @PathVariable String id, @Validated @RequestBody PolicyDto dto) {
        return policyService.update(id, dto);
    }

    @ApiOperation(value = "Delete a policy")
    @DeleteMapping("/policies/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id) {
        policyService.delete(id);
    }
}

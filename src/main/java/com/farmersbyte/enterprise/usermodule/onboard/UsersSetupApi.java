package com.farmersbyte.enterprise.usermodule.onboard;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Setup API", tags = {"Setup API"})
@RestController
@RequestMapping("fb/v1/setup-service")
public class UsersSetupApi {

    @Autowired private FarmerDataImportService farmerDataImportService;
    @Autowired private EmployeeDataImportService employeeDataImportService;

    @ApiOperation(value = "Setup Farmers")
    @PostMapping("/farmersSetup")
    @ResponseStatus(HttpStatus.OK)
    public void createFarmers() {
        farmerDataImportService.refreshData();
    }

    @ApiOperation(value = "Setup Employees")
    @PostMapping("/employeesSetup")
    @ResponseStatus(HttpStatus.OK)
    public void createEmployees() {
        employeeDataImportService.refreshData();
    }

}

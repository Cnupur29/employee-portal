package com.schaudha.employeeportal.controller;

import com.schaudha.employeeportal.model.Employee;
import com.schaudha.employeeportal.repository.EmployeeRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(value = "/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @GetMapping(value = "/employees/{id}")
    @ApiOperation(value = "Get employee by id",
            notes = "Api to fetch employee by its id",
            response = Employee.class,
            produces = "application/json",
            httpMethod = "GET",
            authorizations = {
                @Authorization(value = "oauth2scheme", scopes = {
                        @AuthorizationScope(scope = "", description = "fetch employee by id")
                })
            })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 400, message = "bad request")
    })
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id ){
        Optional<Employee> employee = employeeRepository.findById(Long.valueOf(id));
        if(!employee.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(null);
        }
        return ResponseEntity.ok(employee.get());
    }

    @PostMapping(value = "/employee")
    @ResponseStatus(HttpStatus.OK)
    public void addEmployeeDetails(@RequestBody Employee employee){
        employeeRepository.save(employee);
    }


}

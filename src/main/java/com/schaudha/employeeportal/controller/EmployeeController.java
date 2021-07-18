package com.schaudha.employeeportal.controller;

import com.schaudha.employeeportal.model.Employee;
import com.schaudha.employeeportal.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public Employee getEmployeeById(@PathVariable("id") long id ){
        return employeeRepository.findById(Long.valueOf(id)).get();
    }

    @PostMapping(value = "/employee")
    @ResponseStatus(HttpStatus.OK)
    public void addEmployeeDetails(@RequestBody Employee employee){
        employeeRepository.save(employee);
    }


}

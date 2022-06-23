package com.schaudha.employeeportal.controller;

import com.schaudha.employeeportal.model.Employee;
import com.schaudha.employeeportal.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

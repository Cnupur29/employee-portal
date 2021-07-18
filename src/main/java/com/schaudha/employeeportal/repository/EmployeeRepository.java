package com.schaudha.employeeportal.repository;

import com.schaudha.employeeportal.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface EmployeeRepository extends JpaRepository<Employee , Long> {
}

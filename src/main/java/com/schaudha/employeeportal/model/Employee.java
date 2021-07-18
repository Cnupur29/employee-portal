package com.schaudha.employeeportal.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "employeeDetails")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
    @SequenceGenerator(name = "seq_generator", sequenceName = "hibernate_sequence", allocationSize = 1)
    private Long id;
    private String firstName;
    private String lastName;
    private String designation;
    private String manager;
    private String department;
    private String email;
    private String phone;
    private String extensionNo;
}

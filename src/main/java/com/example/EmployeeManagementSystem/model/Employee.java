package com.example.EmployeeManagementSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tbl_employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;
    @NotBlank
    private String employeeName;
    @NotBlank
    private String employeeAddress;
    @Email
    private String employeeEmail;
    @NotBlank
    private String employeePassword;
    @Pattern(regexp = "\\d{2}-\\d{10}", message = "Phone number should be in the format XX-XXXXXXXXXX first two digit will be country code other will be 10 digit phone number")
    private String employeeContact;
    @NotBlank
    private String employeeJobRole;

    private Integer employeeSalary;

    public Employee(String employeeName, String employeeAddress, String employeeEmail, String employeePassword, String employeeContact, String employeeJobRole, Integer employeeSalary) {
        this.employeeName = employeeName;
        this.employeeAddress = employeeAddress;
        this.employeeEmail = employeeEmail;
        this.employeePassword = employeePassword;
        this.employeeContact = employeeContact;
        this.employeeJobRole = employeeJobRole;
        this.employeeSalary = employeeSalary;
    }
}

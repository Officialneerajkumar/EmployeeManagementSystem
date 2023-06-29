package com.example.EmployeeManagementSystem.controller;

import com.example.EmployeeManagementSystem.dto.SignInInput;
import com.example.EmployeeManagementSystem.dto.SignInOutput;
import com.example.EmployeeManagementSystem.dto.SignUpOutput;
import com.example.EmployeeManagementSystem.model.Employee;
import com.example.EmployeeManagementSystem.service.EmployeeManagementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/employee-management")
public class EmployeeManagementController {

    @Autowired
    private EmployeeManagementService employeeManagementService;

    @PostMapping("/sign-up")
    public SignUpOutput signUp(@Valid @RequestBody Employee employee){
        return employeeManagementService.signup(employee);
    }

    @PostMapping("/sign-in")
    public SignInOutput signIn(@RequestBody SignInInput signInInput){
        return employeeManagementService.signIn(signInInput);
    }



}

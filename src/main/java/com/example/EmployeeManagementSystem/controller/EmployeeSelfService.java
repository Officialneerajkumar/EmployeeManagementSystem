package com.example.EmployeeManagementSystem.controller;

import com.example.EmployeeManagementSystem.dto.SignInInput;
import com.example.EmployeeManagementSystem.dto.SignInOutput;
import com.example.EmployeeManagementSystem.dto.SignUpOutput;
import com.example.EmployeeManagementSystem.model.Employee;
import com.example.EmployeeManagementSystem.service.EmployeeManagementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeSelfService {
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

    @GetMapping("/get-employee-by-id")
    public ResponseEntity<Employee> getEmployee(@RequestParam String userEmail,@RequestParam String token){
        Employee employee = employeeManagementService.getEmployee(userEmail,token);
        HttpStatus status ;
        if(employee!=null){
            status=HttpStatus.FOUND;
        }else{
            status=HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(employee,status);
    }
    @PutMapping("/update-employee")
    public ResponseEntity<String> updateEmployee(@RequestParam String userEmail,@RequestParam String token,@RequestBody Employee employee){
        String message = employeeManagementService.updateEmployeeInfo(userEmail,token,employee);
        HttpStatus status;
        if(message!=null){
            status=HttpStatus.OK;
        }else{
            status=HttpStatus.BAD_REQUEST;
            message="Invalid User";
        }
        return new ResponseEntity<>(message,status);
    }
}

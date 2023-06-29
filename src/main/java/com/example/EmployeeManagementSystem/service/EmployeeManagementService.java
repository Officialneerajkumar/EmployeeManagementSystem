package com.example.EmployeeManagementSystem.service;

import com.example.EmployeeManagementSystem.dto.SignInInput;
import com.example.EmployeeManagementSystem.dto.SignInOutput;
import com.example.EmployeeManagementSystem.dto.SignUpOutput;
import com.example.EmployeeManagementSystem.model.AuthenticationToken;
import com.example.EmployeeManagementSystem.model.Employee;
import com.example.EmployeeManagementSystem.repo.AuthTokenRepo;
import com.example.EmployeeManagementSystem.repo.EmployeeRepo;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;

@Service
public class EmployeeManagementService {

    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private AuthTokenRepo authTokenRepo;
    @Autowired
    private TokenService tokenService;

    public SignUpOutput signup(Employee employee) {
        // check employee exist or not by email
        Employee employee1 = employeeRepo.findFirstByEmployeeEmail(employee.getEmployeeEmail());

        if(employee1 != null)
        {
            throw new IllegalStateException("Employee already exists!!!!...sign in instead");
        }
        //encryption
        String encryptedPassword = null;
        try {
            encryptedPassword = encryptPassword(employee.getEmployeePassword());//takes  a string and encrypts it...
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }
        //save the user

        employee1 = new Employee(employee.getEmployeeName(),
                employee.getEmployeeAddress(),employee.getEmployeeEmail(),
                encryptedPassword, employee.getEmployeeContact(),employee.getEmployeeJobRole(),employee.getEmployeeSalary());

        employeeRepo.save(employee1);

        //token creation and saving

        AuthenticationToken token = new AuthenticationToken(employee1);

        tokenService.saveToken(token);

        return new SignUpOutput("Employee Registered successfully");

    }
    private String encryptPassword(String userPassword) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(userPassword.getBytes());
        byte[] digested =  md5.digest();

        String hash = DatatypeConverter.printHexBinary(digested);
        return hash;
    }


    public SignInOutput signIn(SignInInput signInInput) {

        Employee employee = employeeRepo.findFirstByEmployeeEmail(signInInput.getEmployeeEmail());

        if(employee == null)
        {
            throw new IllegalStateException("User invalid!!!!...sign up instead");
        }

        //encrypt the password

        String encryptedPassword = null;

        try {
            encryptedPassword = encryptPassword(signInInput.getEmployeePassword());
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }
        //match it with database encrypted password

        boolean isPasswordValid = encryptedPassword.equals(employee.getEmployeePassword());

        if(!isPasswordValid)
        {
            throw new IllegalStateException("User invalid!!!!...sign up instead");
        }

        //figure out the token

        AuthenticationToken authToken = tokenService.getToken(employee);

        //set up output response

        return new SignInOutput("Authentication Successfull !!!",authToken.getToken());


    }

    public Employee getEmployee(String userEmail, String token) {
        Employee employee = null;
        if(tokenService.authenticate(userEmail,token)){
            employee = employeeRepo.findFirstByEmployeeEmail(userEmail);
        }
        return employee;
    }

    public String updateEmployeeInfo(String userEmail, String token, Employee employee) {
        String message = null;
        if(tokenService.authenticate(userEmail,token)){
            Employee savedEmployee = employeeRepo.findFirstByEmployeeEmail(userEmail);
            savedEmployee.setEmployeeName(employee.getEmployeeName());
            savedEmployee.setEmployeeAddress(employee.getEmployeeAddress());
            savedEmployee.setEmployeeContact(employee.getEmployeeContact());
            savedEmployee.setEmployeeEmail(employee.getEmployeeEmail());
            savedEmployee.setEmployeePassword(employee.getEmployeePassword());
            savedEmployee.setEmployeeSalary(employee.getEmployeeSalary());
            savedEmployee.setEmployeeJobRole(employee.getEmployeeJobRole());
            employeeRepo.save(savedEmployee);
            message="Employee updated successfully";
        }
        return message;
    }
}

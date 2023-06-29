package com.example.EmployeeManagementSystem.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpInput {

    private String userName;
    private String userEmail;
    private String userPassword;
    private String userContact;
}

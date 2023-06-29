package com.example.EmployeeManagementSystem.service;

import com.example.EmployeeManagementSystem.model.AuthenticationToken;
import com.example.EmployeeManagementSystem.model.Employee;
import com.example.EmployeeManagementSystem.repo.AuthTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private AuthTokenRepo authTokenRepo;
    public void saveToken(AuthenticationToken token) {
        authTokenRepo.save(token);
    }

    public AuthenticationToken getToken(Employee employee) {
        return  authTokenRepo.findByEmployee(employee);
    }
    public boolean authenticate(String userEmail, String token) {

        AuthenticationToken authToken = authTokenRepo.findFirstByToken(token);//find token object via token string
        if(authToken == null)
        {
            return false;
        }
        String expectedEmail = authToken.getEmployee().getEmployeeEmail();
        if(expectedEmail == null)
            return false;

        return expectedEmail.equals(userEmail);

    }
}

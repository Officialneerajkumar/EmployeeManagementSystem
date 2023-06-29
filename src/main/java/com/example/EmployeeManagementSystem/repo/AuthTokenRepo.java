package com.example.EmployeeManagementSystem.repo;

import com.example.EmployeeManagementSystem.model.AuthenticationToken;
import com.example.EmployeeManagementSystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthTokenRepo extends JpaRepository<AuthenticationToken,Integer> {

    AuthenticationToken findByEmployee(Employee employee);

    AuthenticationToken findFirstByToken(String token);
}

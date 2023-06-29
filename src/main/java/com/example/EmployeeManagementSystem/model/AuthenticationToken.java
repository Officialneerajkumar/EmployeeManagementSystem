package com.example.EmployeeManagementSystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class AuthenticationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;
    private String token;
    private LocalDate creationDate;
    @OneToOne
    @JoinColumn(nullable = false,name = "fk_employeeId")
    private Employee employee;

    public AuthenticationToken(Employee employee){
        this.employee = employee;
        this.token= UUID.randomUUID().toString();
        this.creationDate=LocalDate.now();
    }
}

package com.hiberus.employee_management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeResponseDTO {
    private String fullName;
    private int age;
    private double salary;
    private String role;
}

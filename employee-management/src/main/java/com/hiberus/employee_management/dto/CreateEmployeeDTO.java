package com.hiberus.employee_management.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateEmployeeDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;

    @Min(value = 18, message = "La edad mínima es 18")
    @Max(value = 65, message = "La edad máxima es 65")
    private int age;

    @NotBlank(message = "El rol es obligatorio")
    private String role;

    @Positive(message = "El salario debe ser positivo")
    private double salary;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    private LocalDate initDate;
}

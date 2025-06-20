package com.hiberus.employee_management.controller;

import com.hiberus.employee_management.dto.CreateEmployeeDTO;
import com.hiberus.employee_management.dto.EmployeeResponseDTO;
import com.hiberus.employee_management.entity.Employee;
import com.hiberus.employee_management.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/create/{departmentId}")
    public ResponseEntity<EmployeeResponseDTO> createEmployee(
            @PathVariable Long departmentId,
            @Valid @RequestBody CreateEmployeeDTO dto) {

        Employee employee = employeeService.createEmployee(departmentId, dto);

        EmployeeResponseDTO response = EmployeeResponseDTO.builder()
                .fullName(employee.getName() + " " + employee.getLastName())
                .age(employee.getAge())
                .salary(employee.getSalary())
                .role(employee.getRole())
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long employeeId) {
        boolean deleted = employeeService.deleteEmployee(employeeId);
        return deleted
                ? ResponseEntity.ok("Empleado eliminado lógicamente")
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/highestSalary")
    public ResponseEntity<EmployeeResponseDTO> getHighestSalary() {
        return employeeService.getHighestSalaryEmployee()
                .map(employee -> ResponseEntity.ok(EmployeeResponseDTO.builder()
                        .fullName(employee.getName() + " " + employee.getLastName())
                        .age(employee.getAge())
                        .salary(employee.getSalary())
                        .role(employee.getRole())
                        .build()))
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/lowerAge")
    public ResponseEntity<EmployeeResponseDTO> getYoungestEmployee() {
        return employeeService.getYoungestEmployee()
                .map(employee -> ResponseEntity.ok(EmployeeResponseDTO.builder()
                        .fullName(employee.getName() + " " + employee.getLastName())
                        .age(employee.getAge())
                        .salary(employee.getSalary())
                        .role(employee.getRole())
                        .build()))
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/countLastMonth")
    public ResponseEntity<Long> countLastMonth() {
        return ResponseEntity.ok(employeeService.countEmployeesLastMonth());
    }
}

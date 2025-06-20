package com.hiberus.employee_management.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hiberus.employee_management.dto.CreateEmployeeDTO;

import com.hiberus.employee_management.entity.Department;
import com.hiberus.employee_management.entity.Employee;
import com.hiberus.employee_management.exceptions.ResourceNotFoundException;
import com.hiberus.employee_management.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private ObjectMapper objectMapper;
    private Department department;
    private Employee employee1, employee2;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        department = Department.builder().id(1L).name("Sistemas").status("A").build();

        employee1 = Employee.builder()
                .id(1L).name("Luis").lastName("Pérez").age(22).role("Dev").salary(500)
                .initDate(LocalDate.of(2023, 2, 10)).status("A").department(department)
                .build();
        employee2 = Employee.builder()
                .id(2L).name("José").lastName("López").age(20).role("Intern").salary(300)
                .initDate(LocalDate.now().minusDays(10)).status("A").department(department)
                .build();
    }

    @Test
    @DisplayName("POST /employee/create/{departmentId} - Crear empleado exitosamente")
    void testCreateEmployeeSuccess() throws Exception {
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setName("Ana");
        dto.setLastName("Martínez");
        dto.setAge(22);
        dto.setRole("QA");
        dto.setSalary(600.0);
        dto.setInitDate(LocalDate.of(2024, 1, 15));

        Employee employee = Employee.builder()
                .id(1L).name("Ana").lastName("Martínez").age(22).role("QA").salary(600.0)
                .initDate(LocalDate.of(2024, 1, 15)).status("A").department(department)
                .build();

        when(employeeService.createEmployee(eq(1L), any(CreateEmployeeDTO.class))).thenReturn(employee);

        mockMvc.perform(post("/employee/create/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Ana Martínez"))
                .andExpect(jsonPath("$.age").value(22))
                .andExpect(jsonPath("$.salary").value(600.0))
                .andExpect(jsonPath("$.role").value("QA"));
    }

    @Test
    @DisplayName("POST /employee/create/{departmentId} - Fallo al crear empleado (departamento no encontrado)")
    void testCreateEmployeeDepartmentNotFound() throws Exception {
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setName("Ana");
        dto.setLastName("Martínez");
        dto.setAge(22);
        dto.setRole("QA");
        dto.setSalary(600.0);
        dto.setInitDate(LocalDate.of(2024, 1, 15));

        when(employeeService.createEmployee(eq(99L), any(CreateEmployeeDTO.class)))
                .thenThrow(new ResourceNotFoundException("Departamento con ID 99 no encontrado"));

        mockMvc.perform(post("/employee/create/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /employee/delete/{employeeId} - Eliminar empleado lógicamente exitosamente")
    void testDeleteEmployeeSuccess() throws Exception {
        when(employeeService.deleteEmployee(1L)).thenReturn(true);

        mockMvc.perform(post("/employee/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Empleado eliminado lógicamente"));
    }

    @Test
    @DisplayName("POST /employee/delete/{employeeId} - Eliminar empleado lógicamente (no encontrado)")
    void testDeleteEmployeeNotFound() throws Exception {
        when(employeeService.deleteEmployee(99L)).thenReturn(false);

        mockMvc.perform(post("/employee/delete/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /employee/highestSalary - Obtener empleado con mayor salario")
    void testGetHighestSalary() throws Exception {
        when(employeeService.getHighestSalaryEmployee()).thenReturn(Optional.of(employee1));

        mockMvc.perform(get("/employee/highestSalary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Luis Pérez"))
                .andExpect(jsonPath("$.salary").value(500.0));
    }

    @Test
    @DisplayName("GET /employee/highestSalary - No hay empleados (sin contenido)")
    void testGetHighestSalaryNoContent() throws Exception {
        when(employeeService.getHighestSalaryEmployee()).thenReturn(Optional.empty());

        mockMvc.perform(get("/employee/highestSalary"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /employee/lowerAge - Obtener empleado más joven")
    void testGetYoungestEmployee() throws Exception {
        when(employeeService.getYoungestEmployee()).thenReturn(Optional.of(employee2));

        mockMvc.perform(get("/employee/lowerAge"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("José López"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    @DisplayName("GET /employee/lowerAge - No hay empleados (sin contenido)")
    void testGetYoungestEmployeeNoContent() throws Exception {
        when(employeeService.getYoungestEmployee()).thenReturn(Optional.empty());

        mockMvc.perform(get("/employee/lowerAge"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /employee/countLastMonth - Contar empleados del último mes")
    void testCountLastMonth() throws Exception {
        when(employeeService.countEmployeesLastMonth()).thenReturn(5L);

        mockMvc.perform(get("/employee/countLastMonth"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }
}
package com.hiberus.employee_management.services;


import com.hiberus.employee_management.dto.CreateEmployeeDTO;
import com.hiberus.employee_management.entity.Department;
import com.hiberus.employee_management.entity.Employee;
import com.hiberus.employee_management.exceptions.ResourceNotFoundException;
import com.hiberus.employee_management.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private EmployeeService employeeService;

    private Department department;
    private Employee employee1, employee2, employee3, employee4;
    private CreateEmployeeDTO createEmployeeDTO;

    @BeforeEach
    void setUp() {
        department = Department.builder()
                .id(1L)
                .name("Sistemas")
                .status("A")
                .build();

        employee1 = Employee.builder()
                .id(1L).name("Luis").lastName("Pérez").age(22).role("Dev").salary(500)
                .initDate(LocalDate.of(2023, 2, 10)).status("A").department(department)
                .build();
        employee2 = Employee.builder()
                .id(2L).name("Maria").lastName("Gonzalez").age(25).role("QA").salary(450)
                .initDate(LocalDate.of(2023, 3, 11)).status("A").department(department)
                .build();
        employee3 = Employee.builder()
                .id(3L).name("Pedro").lastName("Gómez").age(30).role("Analista").salary(470)
                .initDate(LocalDate.of(2023, 3, 11)).endDate(LocalDate.of(2024, 5, 20)).status("I").department(department)
                .build();
        employee4 = Employee.builder()
                .id(4L).name("José").lastName("López").age(20).role("Intern").salary(300)
                .initDate(LocalDate.now().minusDays(10)).status("A").department(department)
                .build();

        createEmployeeDTO = new CreateEmployeeDTO();
        createEmployeeDTO.setName("Ana");
        createEmployeeDTO.setLastName("Martínez");
        createEmployeeDTO.setAge(28);
        createEmployeeDTO.setRole("Diseñador UX");
        createEmployeeDTO.setSalary(700.50);
        createEmployeeDTO.setInitDate(LocalDate.now());
    }

    @Test
    @DisplayName("Crear un empleado exitosamente con departamento existente")
    void testCreateEmployeeSuccess() {
        when(departmentService.getById(1L)).thenReturn(Optional.of(department));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);

        Employee createdEmployee = employeeService.createEmployee(1L, createEmployeeDTO);

        assertNotNull(createdEmployee);
        assertEquals("Luis", createdEmployee.getName());
        assertEquals("A", createdEmployee.getStatus());
        assertEquals(department, createdEmployee.getDepartment());
        verify(departmentService, times(1)).getById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    @DisplayName("Fallo al crear empleado: departamento no encontrado")
    void testCreateEmployeeDepartmentNotFound() {
        when(departmentService.getById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.createEmployee(99L, createEmployeeDTO);
        });
        assertEquals("Departamento con ID 99 no encontrado", exception.getMessage());
        verify(departmentService, times(1)).getById(99L);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Eliminar lógicamente un empleado existente")
    void testDeleteEmployeeExists() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);

        boolean deleted = employeeService.deleteEmployee(1L);

        assertTrue(deleted);
        assertEquals("I", employee1.getStatus());
        assertNotNull(employee1.getEndDate());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(employee1);
    }

    @Test
    @DisplayName("Intentar eliminar lógicamente un empleado que no existe")
    void testDeleteEmployeeNotExists() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        boolean deleted = employeeService.deleteEmployee(99L);

        assertFalse(deleted);
        verify(employeeRepository, times(1)).findById(99L);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Obtener empleado con mayor salario de empleados activos")
    void testGetHighestSalaryEmployee() {
        List<Employee> activeEmployees = Arrays.asList(employee1, employee2, employee4);
        when(employeeRepository.findAllByStatus("A")).thenReturn(activeEmployees);

        Optional<Employee> highestSalaryEmployee = employeeService.getHighestSalaryEmployee();

        assertTrue(highestSalaryEmployee.isPresent());
        assertEquals(employee1, highestSalaryEmployee.get());
        verify(employeeRepository, times(1)).findAllByStatus("A");
    }

    @Test
    @DisplayName("No hay empleados activos al buscar el de mayor salario")
    void testGetHighestSalaryEmployeeNoActive() {
        when(employeeRepository.findAllByStatus("A")).thenReturn(Collections.emptyList());

        Optional<Employee> highestSalaryEmployee = employeeService.getHighestSalaryEmployee();

        assertFalse(highestSalaryEmployee.isPresent());
        verify(employeeRepository, times(1)).findAllByStatus("A");
    }

    @Test
    @DisplayName("Obtener empleado más joven de empleados activos")
    void testGetYoungestEmployee() {
        List<Employee> activeEmployees = Arrays.asList(employee1, employee2, employee4);
        when(employeeRepository.findAllByStatus("A")).thenReturn(activeEmployees);

        Optional<Employee> youngestEmployee = employeeService.getYoungestEmployee();

        assertTrue(youngestEmployee.isPresent());
        assertEquals(employee4, youngestEmployee.get());
        verify(employeeRepository, times(1)).findAllByStatus("A");
    }

    @Test
    @DisplayName("No hay empleados activos al buscar el más joven")
    void testGetYoungestEmployeeNoActive() {
        when(employeeRepository.findAllByStatus("A")).thenReturn(Collections.emptyList());

        Optional<Employee> youngestEmployee = employeeService.getYoungestEmployee();

        assertFalse(youngestEmployee.isPresent());
        verify(employeeRepository, times(1)).findAllByStatus("A");
    }

    @Test
    @DisplayName("Contar empleados ingresados en el último mes")
    void testCountEmployeesLastMonth() {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        List<Employee> recentEmployees = Arrays.asList(employee4);
        when(employeeRepository.findByInitDateAfter(any(LocalDate.class))).thenReturn(recentEmployees);
        employee4.setStatus("A");


        long count = employeeService.countEmployeesLastMonth();

        assertEquals(1, count);
        verify(employeeRepository, times(1)).findByInitDateAfter(any(LocalDate.class));
    }

    @Test
    @DisplayName("Contar empleados ingresados en el último mes (ninguno)")
    void testCountEmployeesLastMonthNone() {
        when(employeeRepository.findByInitDateAfter(any(LocalDate.class))).thenReturn(Collections.emptyList());

        long count = employeeService.countEmployeesLastMonth();

        assertEquals(0, count);
        verify(employeeRepository, times(1)).findByInitDateAfter(any(LocalDate.class));
    }

    @Test
    @DisplayName("Contar empleados ingresados en el último mes (con inactivos)")
    void testCountEmployeesLastMonthWithInactive() {

        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        Employee activeRecent = Employee.builder().id(5L).name("Test").lastName("Test").age(25).role("Dev")
                .salary(600).initDate(LocalDate.now().minusWeeks(1)).status("A").department(department)
                .build();
        Employee inactiveRecent = Employee.builder().id(6L).name("Test2").lastName("Test2").age(30).role("QA")
                .salary(500).initDate(LocalDate.now().minusWeeks(2)).status("I").department(department)
                .build();

        List<Employee> recentEmployees = Arrays.asList(activeRecent, inactiveRecent);
        when(employeeRepository.findByInitDateAfter(any(LocalDate.class))).thenReturn(recentEmployees);

        long count = employeeService.countEmployeesLastMonth();

        assertEquals(1, count);
        verify(employeeRepository, times(1)).findByInitDateAfter(any(LocalDate.class));
    }
}
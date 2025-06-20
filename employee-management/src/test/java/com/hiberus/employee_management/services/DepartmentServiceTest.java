package com.hiberus.employee_management.services;

import com.hiberus.employee_management.entity.Department;
import com.hiberus.employee_management.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    void setUp() {
        department = Department.builder()
                .id(1L)
                .name("Sistemas")
                .status("A")
                .build();
    }

    @Test
    @DisplayName("Crear un departamento exitosamente")
    void testCreateDepartmentSuccess() {
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        Department createdDepartment = departmentService.createDepartment(department);

        assertNotNull(createdDepartment);
        assertEquals("Sistemas", createdDepartment.getName());
        assertEquals("A", createdDepartment.getStatus());
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    @DisplayName("Eliminar lógicamente un departamento existente")
    void testDeleteDepartmentExists() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        boolean deleted = departmentService.deleteDepartment(1L);

        assertTrue(deleted);
        assertEquals("I", department.getStatus());
        verify(departmentRepository, times(1)).findById(1L);
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    @DisplayName("Intentar eliminar lógicamente un departamento que no existe")
    void testDeleteDepartmentNotExists() {
        when(departmentRepository.findById(2L)).thenReturn(Optional.empty());

        boolean deleted = departmentService.deleteDepartment(2L);

        assertFalse(deleted);
        verify(departmentRepository, times(1)).findById(2L);
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    @DisplayName("Obtener departamento por ID existente")
    void testGetByIdFound() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        Optional<Department> foundDepartment = departmentService.getById(1L);

        assertTrue(foundDepartment.isPresent());
        assertEquals(department, foundDepartment.get());
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Obtener departamento por ID no existente")
    void testGetByIdNotFound() {
        when(departmentRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Department> foundDepartment = departmentService.getById(2L);

        assertFalse(foundDepartment.isPresent());
        verify(departmentRepository, times(1)).findById(2L);
    }
}
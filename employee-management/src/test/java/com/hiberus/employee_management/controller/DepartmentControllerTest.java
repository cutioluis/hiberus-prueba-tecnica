package com.hiberus.employee_management.controller;

import com.hiberus.employee_management.entity.Department;
import com.hiberus.employee_management.services.DepartmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Test
    @DisplayName("POST /department/create - Crear departamento exitosamente")
    void testCreateDepartment() throws Exception {
        Department departmentToCreate = new Department(null, "Marketing", "A");
        Department createdDepartment = new Department(1L, "Marketing", "A");

        when(departmentService.createDepartment(any(Department.class))).thenReturn(createdDepartment);

        mockMvc.perform(post("/department/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Marketing\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Marketing"))
                .andExpect(jsonPath("$.status").value("A"));
    }

    @Test
    @DisplayName("POST /department/delete/{id} - Eliminar departamento lógicamente exitosamente")
    void testDeleteDepartmentSuccess() throws Exception {
        when(departmentService.deleteDepartment(1L)).thenReturn(true);

        mockMvc.perform(post("/department/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Departamento eliminado lógicamente"));
    }

    @Test
    @DisplayName("POST /department/delete/{id} - Eliminar departamento lógicamente (no encontrado)")
    void testDeleteDepartmentNotFound() throws Exception {
        when(departmentService.deleteDepartment(99L)).thenReturn(false);

        mockMvc.perform(post("/department/delete/99"))
                .andExpect(status().isNotFound());
    }
}
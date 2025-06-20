package com.hiberus.employee_management.services;

import com.hiberus.employee_management.entity.Department;
import com.hiberus.employee_management.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public Department createDepartment(Department department) {
        department.setStatus("A");
        return departmentRepository.save(department);
    }

    public boolean deleteDepartment(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            Department d = department.get();
            d.setStatus("I");
            departmentRepository.save(d);
            return true;
        }
        return false;
    }

    public Optional<Department> getById(Long id) {
        return departmentRepository.findById(id);
    }
}

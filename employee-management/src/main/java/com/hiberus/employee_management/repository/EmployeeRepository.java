package com.hiberus.employee_management.repository;

import com.hiberus.employee_management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByStatus(String status);

    List<Employee> findByInitDateAfter(LocalDate date);
}

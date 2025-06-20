package com.hiberus.employee_management.services;

import com.hiberus.employee_management.dto.CreateEmployeeDTO;
import com.hiberus.employee_management.entity.Department;
import com.hiberus.employee_management.entity.Employee;
import com.hiberus.employee_management.exceptions.ResourceNotFoundException;
import com.hiberus.employee_management.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;

    public Employee createEmployee(Long departmentId, CreateEmployeeDTO dto) {
        Department department = departmentService.getById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento con ID " + departmentId + " no encontrado"));

        Employee employee = Employee.builder()
                .name(dto.getName())
                .lastName(dto.getLastName())
                .age(dto.getAge())
                .role(dto.getRole())
                .salary(dto.getSalary())
                .initDate(dto.getInitDate())
                .status("A")
                .department(department)
                .build();

        return employeeRepository.save(employee);
    }

    public boolean deleteEmployee(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Employee e = employee.get();
            e.setStatus("I");
            e.setEndDate(LocalDate.now());
            employeeRepository.save(e);
            return true;
        }
        return false;
    }

    public Optional<Employee> getHighestSalaryEmployee() {
        List<Employee> activeEmployees = employeeRepository.findAllByStatus("A");
        return activeEmployees.stream()
                .max(Comparator.comparingDouble(Employee::getSalary));
    }

    public Optional<Employee> getYoungestEmployee() {
        List<Employee> activeEmployees = employeeRepository.findAllByStatus("A");
        return activeEmployees.stream()
                .min(Comparator.comparingInt(Employee::getAge));
    }

    public long countEmployeesLastMonth() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        return employeeRepository.findByInitDateAfter(lastMonth).stream()
                .filter(e -> "A".equals(e.getStatus()))
                .count();
    }
}

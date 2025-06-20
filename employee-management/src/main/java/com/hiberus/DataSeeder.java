package com.hiberus;

import com.hiberus.employee_management.entity.Department;
import com.hiberus.employee_management.entity.Employee;
import com.hiberus.employee_management.repository.DepartmentRepository;
import com.hiberus.employee_management.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final DepartmentRepository departmentRepo;
    private final EmployeeRepository employeeRepo;

    @Override
    public void run(String... args) {
        var d1 = departmentRepo.save(new Department(null, "Sistemas", "A"));
        var d2 = departmentRepo.save(new Department(null, "Contabilidad", "A"));
        var d3 = departmentRepo.save(new Department(null, "RRHH", "I"));
        var d4 = departmentRepo.save(new Department(null, "People", "A"));

        employeeRepo.save(Employee.builder()
                .name("Luis")
                .lastName("Pérez")
                .age(22)
                .role("Dev")
                .salary(500)
                .initDate(LocalDate.of(2021, 2, 10))
                .status("A")
                .department(d1)
                .build());

        employeeRepo.save(Employee.builder()
                .name("Maria")
                .lastName("Gonzalez")
                .age(25)
                .role("QA")
                .salary(450)
                .initDate(LocalDate.of(2020, 3, 11))
                .status("A")
                .department(d1)
                .build());

        employeeRepo.save(Employee.builder()
                .name("Pedro")
                .lastName("Gómez")
                .age(30)
                .role("Analista")
                .salary(470)
                .initDate(LocalDate.of(2020, 3, 11))
                .endDate(LocalDate.of(2024, 5, 20))
                .status("I")
                .department(d2)
                .build());

        employeeRepo.save(Employee.builder()
                .name("José")
                .lastName("López")
                .age(20)
                .role("Intern")
                .salary(300)
                .initDate(LocalDate.now().minusDays(10))
                .status("A")
                .department(d2)
                .build());
    }
}

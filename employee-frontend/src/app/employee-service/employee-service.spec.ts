// src/app/employee.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Define interfaces for your DTOs to ensure type safety
export interface CreateEmployeeDTO {
  name: string;
  lastName: string;
  age: number;
  role: string;
  salary: number;
  initDate: string; // Use string for date to match backend's LocalDate expected format (e.g., "YYYY-MM-DD")
}

export interface EmployeeResponseDTO {
  fullName: string;
  age: number;
  salary: number;
  role: string;
}

export interface Department {
  id?: number;
  name: string;
  status?: string;
}

@Injectable({
  providedIn: 'root' // This makes the service a singleton available throughout the app
})
export class EmployeeService {
  private baseUrl = 'http://localhost:8080'; // Your Spring Boot backend base URL

  constructor(private http: HttpClient) { }

  // Backend Endpoints:

  // POST /department/create
  createDepartment(department: Department): Observable<Department> {
    return this.http.post<Department>(`${this.baseUrl}/department/create`, department);
  }

  // POST /department/delete/{departmentId}
  deleteDepartment(departmentId: number): Observable<string> {
    return this.http.post(`${this.baseUrl}/department/delete/${departmentId}`, {}, { responseType: 'text' });
  }

  // POST /employee/create/{departmentId}
  createEmployee(departmentId: number, employeeDto: CreateEmployeeDTO): Observable<EmployeeResponseDTO> {
    return this.http.post<EmployeeResponseDTO>(`${this.baseUrl}/employee/create/${departmentId}`, employeeDto);
  }

  // POST /employee/delete/{employeeId}
  deleteEmployee(employeeId: number): Observable<string> {
    return this.http.post(`${this.baseUrl}/employee/delete/${employeeId}`, {}, { responseType: 'text' });
  }

  // GET /employee/highestSalary
  getHighestSalaryEmployee(): Observable<EmployeeResponseDTO> {
    return this.http.get<EmployeeResponseDTO>(`${this.baseUrl}/employee/highestSalary`);
  }

  // GET /employee/lowerAge
  getYoungestEmployee(): Observable<EmployeeResponseDTO> {
    return this.http.get<EmployeeResponseDTO>(`${this.baseUrl}/employee/lowerAge`);
  }

  // GET /employee/countLastMonth
  countEmployeesLastMonth(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/employee/countLastMonth`);
  }
}
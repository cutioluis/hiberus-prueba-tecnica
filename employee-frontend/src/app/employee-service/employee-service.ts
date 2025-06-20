import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Define el DTO aquí
export interface CreateEmployeeDTO {
  name: string;
  lastName: string;
  age: number;
  role: string;
  salary: number;
  initDate: string;
}

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private apiUrl = 'http://localhost:8080'; 

  constructor(private http: HttpClient) {}

createEmployee(departmentId: number, employee: CreateEmployeeDTO): Observable<any> {
  return this.http.post<any>(`${this.apiUrl}/employee/create/${departmentId}`, employee);
  }

  getHighestSalary(): Observable<any> {
  return this.http.get<any>(`${this.apiUrl}/employee/highestSalary`);
}

getLowerAge(): Observable<any> {
  return this.http.get<any>(`${this.apiUrl}/employee/lowerAge`);
}

getCountLastMonth(): Observable<any> {
  return this.http.get<any>(`${this.apiUrl}/employee/countLastMonth`);
}


}
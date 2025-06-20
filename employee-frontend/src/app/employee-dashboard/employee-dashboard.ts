import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeService } from '../employee-service/employee-service';

@Component({
  selector: 'app-employee-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './employee-dashboard.html',
  styleUrl: './employee-dashboard.css'
})
export class EmployeeDashboardComponent {
  result: any = null;
  error: string = '';

  constructor(private employeeService: EmployeeService) {}

  getHighestSalary() {
    this.error = '';
    this.employeeService.getHighestSalary().subscribe({
      next: res => this.result = res,
      error: err => this.error = 'Error al consultar salario más alto'
    });
  }

  getLowerAge() {
    this.error = '';
    this.employeeService.getLowerAge().subscribe({
      next: res => this.result = res,
      error: err => this.error = 'Error al consultar edad más baja'
    });
  }

  getCountLastMonth() {
    this.error = '';
    this.employeeService.getCountLastMonth().subscribe({
      next: res => this.result = res,
      error: err => this.error = 'Error al consultar empleados del último mes'
    });
  }
}
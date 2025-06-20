// src/app/app.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeFormComponent } from './employee-form/employee-form'; // Import your component

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    EmployeeFormComponent // Add your component here
  ],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class App {
  title = 'employee-frontend';
}
// src/app/employee-form/employee-form.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, AbstractControl, ReactiveFormsModule } from '@angular/forms'; // <-- Importa ReactiveFormsModule aquí
import { EmployeeService, CreateEmployeeDTO } from "../employee-service/employee-service"

@Component({
  selector: 'app-employee-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule // <-- ¡Añade ReactiveFormsModule a los imports del componente standalone!
  ],
  templateUrl: './employee-form.html',
  styleUrl: './employee-form.css'
})
export class EmployeeFormComponent implements OnInit {
  employeeForm!: FormGroup;
  departmentId: number = 1; // IMPORTANTE: Hardcoded for now.
  submissionMessage: string = '';
  isSuccess: boolean = false;
  result: any = null;
  error: string = '';
  constructor(
    private fb: FormBuilder,
    private employeeService: EmployeeService
  ) {}


  

  ngOnInit(): void {
    this.employeeForm = this.fb.group({
      name: ['', Validators.required],
      lastName: ['', Validators.required],
      age: ['', [Validators.required, Validators.min(18), Validators.max(65), Validators.pattern('^[0-9]*$')]],
      role: ['', Validators.required],
      salary: ['', [Validators.required, Validators.min(0.01), Validators.pattern('^[0-9]+(\.[0-9]{1,2})?$')]],
      initDate: ['', [Validators.required, this.dateValidator]]
    });
  }

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

  dateValidator(control: AbstractControl): { [key: string]: any } | null {
    if (control.value) {
      const regex = /^\d{4}-\d{2}-\d{2}$/; // YYYY-MM-DD
      if (!regex.test(control.value)) {
        return { 'invalidDateFormat': true };
      }
      const date = new Date(control.value);
      if (isNaN(date.getTime())) {
        return { 'invalidDateFormat': true };
      }
    }
    return null;
  }

  get f(): { [key: string]: AbstractControl } {
    return this.employeeForm.controls;
  }

  onSubmit(): void {
    this.submissionMessage = '';
    if (this.employeeForm.invalid) {
      this.employeeForm.markAllAsTouched();
      this.submissionMessage = 'Por favor, corrige los errores del formulario.';
      this.isSuccess = false;
      return;
    }

    const employeeData: CreateEmployeeDTO = {
      name: this.employeeForm.value.name,
      lastName: this.employeeForm.value.lastName,
      age: Number(this.employeeForm.value.age),
      role: this.employeeForm.value.role,
      salary: Number(this.employeeForm.value.salary),
      initDate: this.employeeForm.value.initDate
    };

    this.employeeService.createEmployee(this.departmentId, employeeData).subscribe({
      next: (response) => {
        this.submissionMessage = `Empleado creado con éxito: ${response.fullName} (Rol: ${response.role})`; // Corregí el mensaje de success
        this.isSuccess = true;
        this.employeeForm.reset();
        this.employeeForm.markAsPristine();
        this.employeeForm.markAsUntouched();
      },
      error: (error) => {
        console.error('Error al crear empleado:', error);
        // Ajusta la forma de acceder al mensaje de error si tu GlobalExceptionHandler lo modifica.
        // Si no usas GlobalExceptionHandler y Spring Boot devuelve un error 500 genérico,
        // error.error puede contener la traza o un objeto diferente.
        this.submissionMessage = `Error al crear empleado: ${error.error?.message || error.message}`;
        this.isSuccess = false;
      }
    });
  }
}
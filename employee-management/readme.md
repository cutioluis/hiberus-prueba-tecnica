# Employee Frontend - Prueba TécnicaAdd commentMore actions

Este proyecto es una aplicación Angular moderna para la gestión de empleados y departamentos, con un diseño inspirado en interfaces como GitHub y Vercel (modo oscuro). Permite crear empleados, consultar estadísticas y gestionar departamentos, conectándose a un backend Spring Boot.

---

## Características

- **Diseño moderno y oscuro** (tipo GitHub/Vercel).
- **Formulario reactivo** para crear empleados con validaciones.
- **Consultas rápidas**:
    - Salario más alto
    - Edad más baja
    - Número de empleados que ingresaron el último mes
- **Integración con backend** (Spring Boot).
- **Código modular y standalone components**.

---

## Instalación

### 1. Clona el repositorio

```bash
git clone <url-del-repo>
cd employee-frontend


### 2. Instala dependencias

```bash
npm install
```

### 3. Corre el servidor de desarrollo

```bash
ng serve
```

Abre [http://localhost:4200](http://localhost:4200) en tu navegador.

---

## Estructura principal

- `src/app/employee-form/employee-form.ts`  
  Componente standalone con el formulario y las consultas.
- `src/app/employee-form/employee-form.html`  
  Vista moderna con formulario y botones de consulta.
- `src/app/employee-form/employee-form.css`  
  Estilos oscuros y modernos.
- `src/app/employee-service/employee-service.ts`  
  Servicio Angular para consumir la API REST.
- `src/app/app.routes.ts`  
  Configuración de rutas.
- `src/app/app.config.ts`  
  Configuración global de providers y router.

---

## Endpoints usados

- `POST /employee/create/{departmentId}`  
  Crear empleado.
- `GET /employee/highestSalary`  
  Consultar salario más alto.
- `GET /employee/lowerAge`  
  Consultar edad más baja.
- `GET /employee/countLastMonth`  
  Contar empleados del último mes.

---

## Backend

Asegúrate de tener corriendo el backend Spring Boot en [http://localhost:8080](http://localhost:8080).

Para correrlo:

```bash
mvn spring-boot:run
```
o
```bash
./mvnw spring-boot:run
```

---

## Vista previa

El diseño se inspira en dashboards modernos, con tarjetas, botones y formularios en modo oscuro, similar a la imagen de referencia de Vercel/GitHub.

---

## Personalización

Puedes modificar los estilos en  
`src/app/employee-form/employee-form.css`  
para ajustar colores, fuentes y espaciados según
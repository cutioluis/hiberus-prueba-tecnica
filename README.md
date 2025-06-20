# Employee Management - Prueba Técnica

![Vista previa](https://github.com/cutioluis/hiberus-prueba-tecnica/blob/master/employee-frontend/public/home.png?raw=true)

---

## Descripción

Esta solución consiste en una aplicación moderna para la gestión de empleados y departamentos, que incluye:

- **Frontend** desarrollado en Angular con diseño moderno y modo oscuro, inspirado en interfaces como GitHub y Vercel.
- **Backend** implementado en Spring Boot, que expone una API REST para la gestión de empleados y departamentos.

El sistema permite crear empleados, consultar estadísticas clave (salario más alto, edad más baja, número de empleados que ingresaron el último mes), y gestionar departamentos de forma sencilla y eficiente.

---

## Características principales

- **Diseño UI/UX** moderno, limpio y responsivo, con modo oscuro.
- **Formulario reactivo** para crear empleados con validaciones robustas.
- Consultas rápidas y eficientes al backend con endpoints REST bien definidos.
- Código modular y escalable, usando standalone components en Angular.
- Backend robusto con Spring Boot y JPA para persistencia en base de datos.
- Fácil instalación y puesta en marcha tanto del frontend como backend.

---

## Tecnologías usadas

| Frontend                      | Backend                  |
|------------------------------|--------------------------|
| Angular 15                   | Spring Boot 3.x          |
| TypeScript                   | Java 17                  |
| Angular Material             | Maven                    |
| SCSS                         | JPA / Hibernate          |
| Standalone Components        | API REST                 |

---

## Instalación y ejecución

### Clonar repositorio

```bash
git clone <url-del-repo>
cd <directorio-del-proyecto>
```
# Backend

- Navega a la carpeta del backend.

- Configura la conexión a base de datos en `src/main/resources/application.properties`.

Ejecuta el backend con:

```bash
mvn spring-boot:run
```
### Frontend
Navega a la carpeta employee-frontend.

Instala las dependencias:

```bash
npm install
```
### Levanta el servidor de desarrollo:

```bash
ng serve
```
Abre en tu navegador:
```bash
http://localhost:4200
```

## Endpoints principales del backend

| Método | Ruta                          | Descripción                 |
|--------|-------------------------------|-----------------------------|
| POST   | `/employee/create/{departmentId}` | Crear un nuevo empleado      |
| GET    | `/employee/highestSalary`     | Obtener el salario más alto |
| GET    | `/employee/lowerAge`          | Obtener la edad más baja    |
| GET    | `/employee/countLastMonth`    | Contar empleados del último mes |

---

## Estructura destacada del frontend

- `src/app/employee-form/`  
  Componente standalone con formulario y consultas.

- `src/app/employee-service/`  
  Servicio Angular para consumir la API REST.

- `src/app/app.routes.ts`  
  Configuración de rutas.

- `src/app/app.config.ts`  
  Configuración global de providers y router.

---

## Personalización

Puedes modificar los estilos y comportamiento en:

- `src/app/employee-form/employee-form.css` para ajustar colores y estilos.

## Contacto:
cutioluis@gmail.com

# Prueba Técnica — Gestión de Empleados y Beneficios

Aplicación full-stack para la gestión de empleados y sus beneficios laborales. Integra geocodificación automática mediante la API pública de Nominatim (OpenStreetMap) para mostrar la ubicación del empleado en un mapa interactivo.

---

## Stack Tecnológico

| Capa       | Tecnología                              |
|------------|-----------------------------------------|
| Backend    | Java 21 · Spring Boot 4.1 · Spring Data JPA · Spring Validation |
| Base de datos | MySQL 8                              |
| Frontend   | Vue 3 (Composition API) · Vite · Bootstrap 5 · Leaflet.js |
| HTTP Client | Axios (frontend) · RestClient (backend) |
| Utilidades | Lombok · SLF4J                         |

---

## Arquitectura General

```
Prueba-Tecnica/
├── backend/          # API REST Spring Boot
└── frontend/         # SPA Vue 3 + Vite
```

### Backend — Capas

```
com.prueba.backend
├── BackendApplication.java      # Punto de entrada Spring Boot
├── config/
│   └── CorsConfig.java          # Configuración CORS global
├── controller/
│   ├── EmployeeController.java  # REST /api/employees
│   └── BenefitController.java   # REST /api/benefits
├── service/
│   ├── EmployeeService.java     # Interfaz de negocio empleados
│   ├── BenefitService.java      # Interfaz de negocio beneficios
│   └── impl/
│       ├── EmployeeServiceImpl.java
│       └── BenefitServiceImpl.java
├── repository/
│   ├── EmployeeRepository.java  # JPA Employee
│   └── BenefitRepository.java   # JPA Benefit
├── entity/
│   ├── Employee.java            # Entidad tabla `employees`
│   ├── Benefit.java             # Entidad tabla `benefits`
│   └── EmployeeStatus.java      # Enum: ACTIVO, INACTIVO, VACACIONES, LICENCIA
├── dto/
│   ├── RequestDTO/
│   │   ├── EmployeeRequestDTO.java
│   │   └── BenefitRequestDTO.java
│   └── ResponseDTO/
│       ├── EmployeeResponseDTO.java
│       └── BenefitResponseDTO.java
├── mapper/
│   ├── EmployeeMapper.java      # Entity <-> DTO conversión
│   └── BenefitMapper.java
├── client/
│   └── NominatimClient.java     # Cliente HTTP → OpenStreetMap Nominatim
└── exception/
    ├── ApiError.java                # DTO de respuesta de error uniforme
    ├── GlobalExceptionHandler.java  # @RestControllerAdvice centralizado
    ├── ResourceNotFoundException.java   # → HTTP 404
    ├── DuplicateResourceException.java  # → HTTP 409
    └── ExternalServiceException.java    # → HTTP 503
```

### Frontend — Estructura

```
src/
├── main.js                     # Bootstrap de Vue + plugins globales
├── App.vue                     # Componente raíz + ToastContainer
├── api/
│   └── axios.js                # Instancia Axios preconfigurada
├── router/
│   └── index.js                # Rutas SPA: / y /empleados/:id
├── services/
│   ├── employeeService.js      # CRUD empleados → /api/employees
│   └── benefitService.js       # CRUD beneficios → /api/benefits
├── composables/
│   └── useToast.js             # Sistema de notificaciones global (singleton)
├── utils/
│   └── extractErrorMessage.js  # Extracción legible de errores Axios
├── views/
│   ├── EmployeeListView.vue    # Listado con búsqueda, paginación y creación
│   └── EmployeeDetailView.vue  # Detalle: mapa, beneficios e info extra
└── components/
    ├── common/
    │   └── ToastContainer.vue  # Contenedor global de toasts (Teleport)
    ├── employees/
    │   ├── EmployeeHeaderCard.vue    # Avatar, nombre, cargo, estado
    │   ├── EmployeeLocationCard.vue  # Mapa Leaflet + coordenadas
    │   ├── EmployeeBenefitsCard.vue  # Tabla CRUD de beneficios
    │   ├── EmployeeInfoFooter.vue    # Fecha, teléfono, departamento, salario
    │   ├── EmployeeFormModal.vue     # Modal crear/editar empleado
    │   ├── EmployeeTable.vue         # Tabla del listado principal
    │   ├── EmployeeSearchBar.vue     # Barra de búsqueda en tiempo real
    │   └── EmployeePagination.vue    # Paginación del listado
    └── benefit/
        └── BenefitFormModal.vue      # Modal crear/editar beneficio
```

---

## Endpoints de la API REST

### Empleados — `/api/employees`

| Método | Ruta              | Descripción                        | Respuesta |
|--------|-------------------|------------------------------------|-----------|
| GET    | `/api/employees`  | Lista todos los empleados          | 200       |
| GET    | `/api/employees/{id}` | Obtiene empleado por ID        | 200 / 404 |
| POST   | `/api/employees`  | Crea un nuevo empleado             | 201 / 400 / 409 |
| PUT    | `/api/employees/{id}` | Actualiza un empleado          | 200 / 404 / 409 |
| DELETE | `/api/employees/{id}` | Elimina empleado y sus beneficios | 204 / 404 |

### Beneficios — `/api/benefits`

| Método | Ruta                              | Descripción                      | Respuesta |
|--------|-----------------------------------|----------------------------------|-----------|
| GET    | `/api/benefits`                   | Lista todos los beneficios       | 200       |
| GET    | `/api/benefits/employee/{id}`     | Beneficios de un empleado        | 200       |
| POST   | `/api/benefits`                   | Crea un nuevo beneficio          | 201 / 404 |
| PUT    | `/api/benefits/{id}`              | Actualiza un beneficio           | 200 / 404 |
| DELETE | `/api/benefits/{id}`              | Elimina un beneficio             | 204 / 404 |

### Formato de error unificado (`ApiError`)

```json
{
  "timestamp": "2026-07-11T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Empleado no encontrado"
}
```

---

## Flujo de Geocodificación

Al crear o actualizar un empleado con una nueva ciudad, el backend consulta automáticamente la API pública de Nominatim:

```
POST /api/employees
       ↓
EmployeeServiceImpl.save()
       ↓
NominatimClient.searchCity(city)
  → GET https://nominatim.openstreetmap.org/search?q={city}&format=json&limit=1
       ↓
employee.latitude / employee.longitude  ← almacenadas en BD
       ↓
Frontend muestra mapa Leaflet con pin en la ubicación
```

> Si la ciudad no se encuentra o Nominatim devuelve coordenadas inválidas,
> los campos `latitude` y `longitude` quedan en `null` y se muestra un mensaje placeholder en el mapa.

---

## Configuración y Ejecución

### Backend

1. **Requisitos previos**: Java 21, Maven, MySQL 8 en ejecución
2. **Base de datos**: se crea automáticamente (`createDatabaseIfNotExist=true`)
3. **Credenciales** en `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/backend_api_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=tu_contraseña
   server.port=8080
   ```
4. **Ejecutar**:
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

### Frontend

1. **Requisitos previos**: Node.js 18+
2. **Instalar dependencias**:
   ```bash
   cd frontend
   npm install
   ```
3. **Ejecutar en modo desarrollo**:
   ```bash
   npm run dev
   ```
   La aplicación estará disponible en `http://localhost:5173`.

> El frontend asume que el backend corre en `http://localhost:8080`. Para cambiar esto, edita `src/api/axios.js`.

---

## Rutas del Frontend

| Ruta               | Vista                | Descripción                          |
|--------------------|----------------------|--------------------------------------|
| `/`                | `EmployeeListView`   | Listado con búsqueda y paginación    |
| `/empleados/:id`   | `EmployeeDetailView` | Detalle del empleado con mapa y beneficios |

---

## Estados del Empleado

| Valor        | Significado                         |
|--------------|-------------------------------------|
| `ACTIVO`     | El empleado trabaja normalmente     |
| `INACTIVO`   | El empleado fue dado de baja        |
| `VACACIONES` | El empleado está de vacaciones      |
| `LICENCIA`   | El empleado tiene licencia          |

# Employee Management System

Sistema para la gestión de empleados y beneficios, desarrollado con **Spring Boot** como backend y **Vue 3 + Vite** como frontend.

---

# Requisitos

Antes de ejecutar el proyecto asegúrese de tener instalado:

- Java 17 o superior
- Maven 3.9 o superior
- Node.js 20 o superior
- npm
- MySQL Server
- Git (opcional)

---

# Backend (Spring Boot)

## 1. Clonar el proyecto

```bash
git clone https://github.com/MayerliAvila/prueba_tecnica.git
```

## 2. Ingresar al proyecto backend

```bash
cd backend
```

## 3. Configurar la conexión a MySQL

Editar el archivo:

```
src/main/resources/application.properties
```

Configurar las credenciales de la base de datos:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nombre_base_datos
spring.datasource.username=root
spring.datasource.password=tu_password
```

## 4. Instalar dependencias

Maven descargará automáticamente las dependencias.

## 5. Ejecutar el proyecto

Con Maven:

```bash
mvn spring-boot:run
```

O desde el IDE ejecutar la clase:

```
Application.java
```

El backend quedará disponible en:

```
http://localhost:8080
```

---

# Frontend (Vue 3 + Vite)

## 1. Ingresar al proyecto

```bash
cd frontend
```

## 2. Instalar dependencias

```bash
npm install
```

## 3. Configurar variables de entorno

Crear un archivo llamado:

```
.env
```

Agregar la URL del backend:

```env
VITE_API_URL=http://localhost:8080/api
```

## 4. Ejecutar el proyecto

```bash
npm run dev
```

El frontend quedará disponible en:

```
http://localhost:5173
```

---

# Tecnologías utilizadas

## Backend

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Maven

## Frontend

- Vue 3
- Vite
- Vue Router
- Axios
- Leaflet
- CSS

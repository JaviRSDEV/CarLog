# CarLog — Plataforma de Gestión Integral de Talleres Mecánicos

![Spring Boot](https://img.shields.io/badge/Backend-Spring_Boot_4.0.1-6db33f?style=for-the-badge&logo=spring)
![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk)
![MySQL](https://img.shields.io/badge/Database-MySQL_8.0-4479a1?style=for-the-badge&logo=mysql)
![Docker](https://img.shields.io/badge/Deploy-Docker-2496ed?style=for-the-badge&logo=docker)
![JWT](https://img.shields.io/badge/Auth-JWT-black?style=for-the-badge&logo=jsonwebtokens)
![WebSocket](https://img.shields.io/badge/RealTime-WebSocket_STOMP-010101?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

---

## Descripción

**CarLog** es una plataforma **Full Stack** para la gestión integral de talleres mecánicos. Proporciona una arquitectura RESTful robusta y escalable que cubre el ciclo de vida completo de un vehículo: desde su entrada al taller y la creación de órdenes de trabajo, hasta la facturación final y la salida.

El sistema está diseñado para dar soporte a múltiples roles de usuario, garantizando una experiencia adaptada a cada actor del ecosistema de reparación automotriz.

---

## Tabla de Contenidos

- [Características Principales](#características-principales)
- [Stack Tecnológico](#stack-tecnológico)
- [Arquitectura del Sistema](#arquitectura-del-sistema)
- [Modelos de Dominio](#modelos-de-dominio)
- [Roles del Sistema](#roles-del-sistema)
- [Seguridad y Autenticación](#seguridad-y-autenticación)
- [API REST — Referencia de Endpoints](#api-rest--referencia-de-endpoints)
- [Notificaciones en Tiempo Real](#notificaciones-en-tiempo-real)
- [Manejo de Errores](#manejo-de-errores)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Inicio Rápido](#inicio-rápido)
- [Configuración](#configuración)
- [Flujo de Trabajo Principal](#flujo-de-trabajo-principal)
- [Licencia](#licencia)

---

## Características Principales

- **Gestión completa de vehículos**: registro, actualización, historial de reparaciones y transferencia de propiedad.
- **Órdenes de trabajo**: ciclo de vida completo (PENDING → IN_PROGRESS → COMPLETED) con cálculo automático de totales, IVA y descuentos.
- **Protocolo de entrada/salida de taller**: handshake de doble confirmación entre propietario y taller.
- **Sistema de empleados**: flujo de invitación → aceptación/rechazo → baja laboral.
- **Notificaciones en tiempo real** vía WebSocket (STOMP) para eventos críticos del taller.
- **Autenticación stateless** con JWT y control de acceso basado en roles (RBAC).
- **Gestión de imágenes**: subida de fotos de vehículos e iconos de taller (Base64 y Multipart).
- **Manejo centralizado de errores** con respuestas JSON estandarizadas.
- **Infraestructura containerizada** con Docker Compose (MySQL + phpMyAdmin).

---

## Stack Tecnológico

| Componente       | Tecnología                        | Descripción                                      |
|------------------|-----------------------------------|--------------------------------------------------|
| **Backend**      | Java 21 / Spring Boot 4.0.1       | Framework principal de la aplicación             |
| **Base de Datos**| MySQL 8.0                         | Almacenamiento relacional persistente            |
| **ORM**          | Spring Data JPA / Hibernate       | Mapeo objeto-relacional y gestión de esquema     |
| **Seguridad**    | Spring Security + JJWT            | Autenticación stateless y RBAC                   |
| **Tiempo Real**  | WebSocket (STOMP)                 | Notificaciones push para eventos del taller      |
| **Despliegue**   | Docker & Docker Compose           | Orquestación de infraestructura                  |
| **Utilidades**   | Lombok                            | Reducción de código boilerplate                  |
| **Admin BD**     | phpMyAdmin                        | Interfaz visual de gestión de base de datos      |

---

## Arquitectura del Sistema

CarLog sigue una arquitectura en capas estándar, con separación clara de responsabilidades:

```
Cliente (Angular / Postman)
        │
        ▼
┌─────────────────────────┐
│   REST Controllers      │  ← Capa de entrada HTTP
│  (VehicleController,    │
│   WorkOrderController,  │
│   UserController, ...)  │
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│    Service Layer        │  ← Lógica de negocio, validaciones,
│  (VehicleService,       │    cálculos financieros, notificaciones
│   WorkOrderService,     │
│   UserService, ...)     │
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│   JPA Repositories      │  ← Acceso a datos (Spring Data JPA)
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│   MySQL 8.0 (Docker)    │  ← Persistencia
└─────────────────────────┘
```

### Infraestructura Docker

```
Docker Engine
├── carlog-mysql   (MySQL 8.0)    → Puerto 3306
└── carlog-admin   (phpMyAdmin)   → Puerto 8082

Spring Boot App                   → Puerto 8081
```

---

## Modelos de Dominio

### Diagrama Entidad-Relación

```
User ──────────────< Vehicle
 │                      │
 │                      │
 └──────> Workshop ─────┘
              │
              └──────< WorkOrder ──────< WorkOrderLine
```

| Entidad          | Clave Natural | Descripción                                                  |
|------------------|---------------|--------------------------------------------------------------|
| `User`           | `dni`         | Actor principal. Implementa `UserDetails` de Spring Security |
| `Workshop`       | `workshopId`  | Entidad de negocio. Gestiona empleados y flota de vehículos  |
| `Vehicle`        | `plate`       | Activo central. Vinculado a propietario y taller actual      |
| `WorkOrder`      | `id`          | Registro de servicio. Contiene líneas de trabajo y totales   |
| `WorkOrderLine`  | `id`          | Ítem individual (pieza o mano de obra) con subtotal propio   |

---

## Roles del Sistema

| Rol         | Descripción                                                        |
|-------------|--------------------------------------------------------------------|
| `MANAGER`   | Gestión completa del taller: empleados, vehículos y órdenes        |
| `MECHANIC`  | Operaciones de reparación y diagnóstico en órdenes asignadas       |
| `CLIENT`    | Seguimiento de sus propios vehículos e historial de reparaciones   |
| `DIY`       | Entusiastas del bricolaje automotriz con acceso limitado           |

---

## Seguridad y Autenticación

El sistema utiliza un modelo de autenticación **stateless** basado en **JWT**. Cada petición debe incluir un token válido en la cabecera `Authorization`.

### Flujo de Autenticación

```
1. POST /api/auth/register   → Registro de usuario (contraseña cifrada con BCrypt)
2. POST /api/auth/authenticate → Login → Devuelve JWT + metadatos (DNI, Rol, WorkshopID)
3. Peticiones protegidas     → Authorization: Bearer <token>
```

### Rutas Públicas (sin autenticación)

| Ruta                  | Descripción                        |
|-----------------------|------------------------------------|
| `/api/auth/**`        | Registro y login                   |
| `/uploads/**`         | Servicio de archivos estáticos     |
| `/ws-carlog/**`       | Handshake WebSocket                |

### Cadena de Filtros

```
Request → JwtAuthenticationFilter → SecurityContextHolder → REST Controller
               │
               ├── Extrae token del header
               ├── Valida firma y expiración (JwtService)
               └── Carga UserDetails desde base de datos
```

---

## API REST — Referencia de Endpoints

### Autenticación (`/api/auth`)

| Método | Endpoint                    | Descripción              |
|--------|-----------------------------|--------------------------|
| `POST` | `/api/auth/register`        | Registrar nuevo usuario  |
| `POST` | `/api/auth/authenticate`    | Login y obtención de JWT |

---

### Vehículos (`/api/vehicles`)

| Método   | Endpoint                                          | Descripción                                  |
|----------|---------------------------------------------------|----------------------------------------------|
| `GET`    | `/api/vehicles`                                   | Listar vehículos (global, por taller o dueño)|
| `GET`    | `/api/vehicles/{plate}`                           | Obtener vehículo por matrícula               |
| `POST`   | `/api/vehicles`                                   | Registrar nuevo vehículo                     |
| `PUT`    | `/api/vehicles/{plate}`                           | Actualizar datos del vehículo                |
| `DELETE` | `/api/vehicles/{plate}`                           | Eliminar vehículo e imágenes                 |
| `GET`    | `/api/vehicles/search`                            | Buscar vehículos (`q`, `workshopId`, `type`) |
| `GET`    | `/api/vehicles/{plate}/history`                   | Historial de órdenes de trabajo              |
| `PUT`    | `/api/vehicles/{plate}/request-entry/{workshopId}`| Solicitar entrada al taller                  |
| `PUT`    | `/api/vehicles/{plate}/approve-entry`             | Aprobar solicitud de entrada                 |
| `PUT`    | `/api/vehicles/{plate}/reject-entry`              | Rechazar solicitud de entrada                |
| `POST`   | `/api/vehicles/{plate}/exit/{workshopId}`         | Registrar salida del taller                  |
| `POST`   | `/api/vehicles/{plate}/transfer`                  | Transferir propiedad del vehículo            |

**Tipos de búsqueda (`type`):**
- `OWNER` — Vehículos del usuario autenticado
- `ASSIGNED` — Vehículos asignados al mecánico autenticado
- `WORKSHOP` — Vehículos en el taller del usuario

---

### Órdenes de Trabajo (`/api/workorders`)

| Método   | Endpoint                                        | Descripción                                  |
|----------|-------------------------------------------------|----------------------------------------------|
| `GET`    | `/api/workorders`                               | Listar órdenes (filtro por `mechanicDni`)    |
| `GET`    | `/api/workorders/{id}`                          | Detalle de una orden                         |
| `GET`    | `/api/workorders/workshop/{id}`                 | Órdenes de un taller                         |
| `GET`    | `/api/workorders/vehicle/{plate}`               | Historial de un vehículo                     |
| `POST`   | `/api/workorders`                               | Crear nueva orden de trabajo                 |
| `PUT`    | `/api/workorders/{workOrderId}`                 | Actualizar estado o notas del mecánico       |
| `DELETE` | `/api/workorders/{id}`                          | Eliminar orden                               |
| `POST`   | `/api/workorders/{id}/lines`                    | Añadir línea de trabajo (pieza/mano de obra) |
| `PUT`    | `/api/workorders/{orderId}/lines/{lineId}`      | Actualizar línea existente                   |
| `DELETE` | `/api/workorders/{orderId}/lines/{lineId}`      | Eliminar línea                               |
| `PATCH`  | `/api/workorders/{orderId}/reassign`            | Reasignar mecánico                           |

**Estados de una orden (`WorkOrderStatus`):**

```
PENDING  ──(primera línea añadida)──►  IN_PROGRESS  ──(cierre manual)──►  COMPLETED
```

---

### Usuarios (`/api/users`)

| Método  | Endpoint                        | Descripción                                  |
|---------|---------------------------------|----------------------------------------------|
| `GET`   | `/api/users/{dni}`              | Obtener perfil de usuario                    |
| `PATCH` | `/api/users/{dni}/invite`       | Invitar usuario al taller (Manager)          |
| `PATCH` | `/api/users/{dni}/accept`       | Aceptar invitación de empleo                 |
| `PATCH` | `/api/users/{dni}/reject`       | Rechazar invitación de empleo                |
| `PATCH` | `/api/users/{dni}/fire`         | Dar de baja a un empleado                    |

---

### Taller (`/api/workshop`)

| Método  | Endpoint                        | Descripción                                  |
|---------|---------------------------------|----------------------------------------------|
| `GET`   | `/api/workshop/{id}`            | Obtener datos del taller                     |
| `PUT`   | `/api/workshop/{id}`            | Actualizar taller (con icono multipart)      |
| `GET`   | `/api/workshop/{id}/employees`  | Listar empleados del taller                  |

---

## Notificaciones en Tiempo Real

CarLog utiliza **STOMP sobre WebSocket** para enviar notificaciones push a usuarios específicos sin necesidad de polling.

### Configuración

| Parámetro          | Valor              |
|--------------------|--------------------|
| Endpoint WS        | `/ws-carlog`       |
| Prefijo broker     | `/topic`           |
| Prefijo app        | `/app`             |
| Orígenes permitidos| `*`                |

### Routing por Usuario

Las notificaciones se enrutan a tópicos específicos por DNI:

```
/topic/notificaciones/{dni}
```

### Tipos de Notificación

| Tipo               | Emisor          | Destinatario    | Evento                                      |
|--------------------|-----------------|-----------------|---------------------------------------------|
| `INVITE`           | UserService     | Empleado (DNI)  | Manager invita a un usuario al taller       |
| `NEW_EMPLOYEE`     | UserService     | Manager (DNI)   | Usuario acepta la invitación                |
| `VEHICLE_REQUEST`  | VehicleService  | Manager (DNI)   | Cliente solicita entrada de vehículo        |
| `NEW_FLEET_VEHICLE`| VehicleService  | Empleados       | Nuevo vehículo registrado en el taller      |

### Estructura del DTO de Notificación

```json
{
  "type": "INVITE",
  "title": "¡Nueva oferta de empleo!",
  "message": "El taller AutoPro te ha invitado como MECHANIC.",
  "extraData": "12345678A"
}
```

---

## Manejo de Errores

Todas las excepciones son interceptadas por `GlobalExceptionHandler` (`@RestControllerAdvice`) y devueltas en formato JSON estandarizado:

```json
{
  "message": "Vehículo con matrícula 1234ABC no encontrado.",
  "status": 404,
  "timestamp": 1713350400000
}
```

### Tabla de Errores

| Código | Excepción                  | Causa                                              |
|--------|----------------------------|----------------------------------------------------|
| `404`  | `VehicleNotFoundException` | Matrícula no existe en la base de datos            |
| `404`  | `UserNotFoundException`    | DNI no encontrado                                  |
| `404`  | `WorkOrderNotFoundException`| ID de orden no existe                             |
| `404`  | `WorkshopNotFoundException`| ID de taller no existe                             |
| `409`  | `VehicleOcuppiedException` | Vehículo ya asignado a otro taller                 |
| `403`  | —                          | Token inválido, no enviado o credenciales erróneas |
| `500`  | `Exception`                | Error inesperado de lógica de negocio              |

---

## Estructura del Proyecto

```
carlogv2/
├── BBDD_CARLOG/
│   └── docker-compose.yml          # Infraestructura MySQL + phpMyAdmin
│
└── backend/
    ├── pom.xml                     # Dependencias Maven (Java 21, Spring Boot 4.0.1)
    └── src/main/java/com/carlog/backend/
        ├── BackendApplication.java # Punto de entrada Spring Boot
        ├── auth/                   # Registro, login y respuestas de autenticación
        ├── config/                 # SecurityConfig, WebSocketConfig, ApplicationConfig
        ├── controller/             # REST Controllers (Vehicle, WorkOrder, User, Workshop)
        ├── dto/                    # Data Transfer Objects (entrada y salida)
        ├── error/                  # Excepciones personalizadas + GlobalExceptionHandler
        ├── model/                  # Entidades JPA (User, Vehicle, Workshop, WorkOrder...)
        ├── repository/             # Interfaces Spring Data JPA
        ├── security/               # JwtService, JwtAuthenticationFilter
        └── service/                # Lógica de negocio (VehicleService, WorkOrderService...)
```

---

## Inicio Rápido

### Prerrequisitos

- **JDK 21**
- **Maven**
- **Docker & Docker Compose**

### 1. Clonar el repositorio

```bash
git clone https://github.com/JaviRSDEV/carlogv2.git
cd carlogv2
```

### 2. Levantar la base de datos con Docker

```bash
cd BBDD_CARLOG
docker-compose up -d
```

Verifica que los contenedores estén corriendo:

```bash
docker ps
# Deberías ver: carlog-mysql y carlog-admin
```

| Servicio     | URL                          |
|--------------|------------------------------|
| MySQL        | `localhost:3306`             |
| phpMyAdmin   | `http://localhost:8082`      |

### 3. Arrancar el backend

```bash
cd ../backend
mvn clean install
mvn spring-boot:run
```

La API estará disponible en: **`http://localhost:8081/api`**

---

## Configuración

El archivo `backend/src/main/resources/application.properties` contiene la configuración principal:

```properties
# Base de datos
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/carlog_db
spring.datasource.username=carlog_user
spring.datasource.password=carlog_password

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

# Servidor
server.port=8081

# JWT
application.security.jwt.secret-key=<TU_CLAVE_SECRETA>
application.security.jwt.expiration=86400000   # 24 horas en ms
```

> **Nota de seguridad**: Cambia `jwt.secret-key` por una clave segura en entornos de producción. Nunca expongas credenciales reales en el repositorio.

---

## Flujo de Trabajo Principal

```mermaid
graph LR
    A[Registro de Vehículo] --> B[Solicitud de Entrada al Taller]
    B --> C[Aprobación por el Manager]
    C --> D[Crear Orden de Trabajo]
    D --> E[Añadir Líneas de Trabajo]
    E --> F[Cerrar Orden - COMPLETED]
    F --> G[Facturación con IVA y Descuentos]
    G --> H[Salida del Taller]
```

---

## Licencia

Este proyecto está bajo licencia **MIT**. Consulta el archivo [LICENSE](LICENSE) para más detalles.

---

*Desarrollado por [JaviRSDEV](https://github.com/JaviRSDEV)*
```

---

Este README cubre:

- **Badges** visuales con el stack tecnológico
- **Tabla de contenidos** navegable
- **Arquitectura** en ASCII art y descripción por capas
- **Modelos de dominio** con diagrama ER textual
- **Referencia completa de todos los endpoints** de la API REST
- **Flujo de notificaciones WebSocket** con tipos y estructura del DTO
- **Manejo de errores** con tabla de códigos HTTP
- **Estructura de directorios** del proyecto
- **Guía de inicio rápido** paso a paso
- **Configuración** con notas de seguridad
- **Diagrama de flujo** del ciclo de vida principal [1](#0-0) [2](#0-1) [3](#0-2)

### Citations

**File:** README.md (L1-98)
```markdown
# CarLog API - README Principal
## Descripción del Proyecto

CarLog es una plataforma **Full Stack** para la gestión integral de talleres mecánicos, diseñada con una arquitectura RESTful robusta y escalable. El sistema facilita el ciclo completo de vida de vehículos, desde su entrada en taller hasta la facturación final.

## Stack Tecnológico

| Componente | Tecnología |
|------------|------------|
| **Backend** | ![Spring Boot](https://img.shields.io/badge/Backend-Spring_Boot-6db33f?style=flat&logo=spring) |
| **Base de Datos** | ![MySQL](https://img.shields.io/badge/DB-MySQL-4479a1?style=flat&logo=mysql) |
| **Seguridad** | JWT (JSON Web Token) |
| **Despliegue** | ![Docker](https://img.shields.io/badge/Deploy-Docker-2496ed?style=flat&logo=docker) |

## Estructura del Proyecto

```
CarLog/
├── backend/                 # Aplicación Spring Boot
│   └── src/main/java/com/carlog/backend/
│       └── BackendApplication.java
├── BBDD
|    └──docker-compose.yml  # Configuración Docker
└── README.md               # Esta documentación
```

## Inicio Rápido

### Prerrequisitos
- Docker y Docker Compose
- Java 17+ (para desarrollo local)

### Ejecución con Docker
```bash
# Clonar el repositorio
git clone https://github.com/JaviRSDEV/CarLog.git
cd CarLog

# Iniciar todos los servicios
docker-compose up -d
```

La aplicación estará disponible en:
- Frontend: `http://localhost:4200`
- Backend API: `http://localhost:8081/api`

## Documentación

- **[API Reference](./README.md)** - Documentación completa de endpoints REST
- **[Getting Started & Deployment](wiki)** - Guía detallada de configuración
- **[System Architecture](wiki)** - Arquitectura y patrones de diseño

## Roles del Sistema

CarLog soporta múltiples roles de usuario [7](#0-6) :
- **MANAGER**: Gestión completa del taller
- **MECHANIC**: Operaciones de reparación y diagnóstico
- **CLIENT**: Seguimiento de sus vehículos
- **DIY**: Entusiastas del bricolaje automotriz

## Flujo de Autenticación

1. **Registro**: `POST /api/auth/register` [8](#0-7) 
2. **Login**: `POST /api/auth/authenticate` [9](#0-8) 
3. **Uso**: Incluir token JWT en cabecera `Authorization: Bearer <token>` [10](#0-9) 

## Flujo de Trabajo Principal

```mermaid
graph LR
    A[Registro Vehículo] --> B[Entrada Taller]
    B --> C[Crear Orden]
    C --> D[Añadir Líneas]
    D --> E[Cerrar Orden]
    E --> F[Facturación]
```

## Licencia

Este proyecto está bajo licencia MIT - ver archivo [LICENSE](LICENSE) para detalles.

---

## Notes

Este README sirve como punto de entrada principal al proyecto CarLog. Para documentación técnica detallada de la API, endpoints específicos y ejemplos de uso, consulta el archivo [README.md](README.md) existente que contiene la referencia completa de la API REST del sistema.

Wiki pages you might want to explore:
- [CarLog Overview (JaviRSDEV/CarLog)](/wiki/JaviRSDEV/CarLog#1)

Tabla de Errores Comunes

| Código | Significado  | Causa probable                                | Solución                                        |
|--------|--------------|-----------------------------------------------|-------------------------------------------------|
| 403    | Forbidden    | Token inválido / No enviado / Login fallido   | Revisa la cabecera Authorization o credenciales |
| 404    | Not Found    | ID o Matrícula no existen                     | Verifica que el recurso existe en BD.           |
| 409    | Conflict     | Vehículo en otro taller / Matrícula duplicada | Realiza Check-out antes de Check-in             |
| 500    | Server Error | Error de lógica de negocio                    |                                                 |
```

**File:** BBDD_CARLOG/docker-compose.yml (L1-33)
```yaml
version: '3.8'

services:
  # Servicio de Base de Datos MySQL
  mysqldb:
    image: mysql:8.0
    container_name: carlog-mysql
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: root        # Contraseña maestra
      MYSQL_DATABASE: carlog_db        # Crea la BBDD automáticamente
      MYSQL_USER: carlog_user          # Usuario específico para la App
      MYSQL_PASSWORD: carlog_password  # Contraseña para la App
    ports:
      - "3306:3306"                    # Expone el puerto al exterior
    volumes:
      - mysql_data:/var/lib/mysql      # Persistencia de datos

  # phpMyAdmin (Gestor visual de la BBDD)
  phpmyadmin:
    image: phpmyadmin/phpmyadmin
    container_name: carlog-admin
    depends_on:
      - mysqldb
    environment:
      PMA_HOST: mysqldb                # Conecta con el servicio de arriba
      PMA_PORT: 3306
      PMA_ARBITRARY: 1                 # Permite introducir cualquier servidor/user
    ports:
      - "8082:80"                      # Acceso en: http://localhost:8081

volumes:
  mysql_data:
```

**File:** backend/src/main/resources/application.properties (L1-18)
```properties
spring.application.name=backend
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/carlog_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=carlog_user
spring.datasource.password=carlog_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

##Configuraci�n JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show.sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

##Configuraci�n del puerto del servidor
server.port=8081

application.security.jwt.secret-key=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
application.security.jwt.expiration=86400000
```

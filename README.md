# CarLog — Plataforma de Gestión Integral de Talleres Mecánicos

![Spring Boot](https://img.shields.io/badge/Backend-Spring_Boot_4.0.1-6db33f?style=for-the-badge&logo=spring)
![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk)
![MySQL](https://img.shields.io/badge/Database-MySQL_8.0-4479a1?style=for-the-badge&logo=mysql)
![Docker](https://img.shields.io/badge/Deploy-Docker-2496ed?style=for-the-badge&logo=docker)
![JWT](https://img.shields.io/badge/Auth-JWT-black?style=for-the-badge&logo=jsonwebtokens)
![WebSocket](https://img.shields.io/badge/RealTime-WebSocket_STOMP-010101?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

## Estado del Proyecto

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=JaviRSDEV_CarLog&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=JaviRSDEV_CarLog)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=JaviRSDEV_CarLog&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=JaviRSDEV_CarLog)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=JaviRSDEV_CarLog&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=JaviRSDEV_CarLog)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=JaviRSDEV_CarLog&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=JaviRSDEV_CarLog)

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
- [Flujo de Trabajo Principal](#flujo-de-trabajo-principal)
- [Notificaciones en Tiempo Real](#notificaciones-en-tiempo-real)
- [Manejo de Errores](#manejo-de-errores)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Inicio Rápido](#inicio-rápido)
- [Configuración](#configuración)
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

| Tecnología | Versión | Uso |
|---|---|---|
| Spring Boot | 4.0.1 | Framework principal |
| Java | 21 | Lenguaje |
| Spring Security | (boot managed) | Autenticación y autorización |
| Spring Data JPA | (boot managed) | Acceso a datos |
| MySQL | 8.0 | Base de datos |
| Flyway | (boot managed) | Migraciones de esquema |
| JJWT | 0.12.6 | Generación y validación de JWT |
| Bucket4j | 8.10.1 | Rate limiting |
| Cloudinary | 2.0.0 | Almacenamiento de imágenes |
| Spring WebSocket (STOMP) | 4.0.1 | Notificaciones en tiempo real |
| SpringDoc OpenAPI | 3.0.0 | Documentación Swagger |
| Lombok | (boot managed) | Reducción de boilerplate |
| spring-dotenv | 4.0.0 | Variables de entorno desde `.env` |
| Apache Tika | 2.9.1 | Detección de tipo MIME |

---

## Arquitectura del Sistema

```
Cliente (Angular / Postman)
        │
        ▼
┌─────────────────────────────────────┐
│         REST Controllers            │  ← Capa HTTP
│  AuthenticationController           │
│  VehicleController                  │
│  WorkOrderController                │
│  WorkshopController                 |
│  UserController                     |
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│           Service Layer             │  ← Lógica de negocio
│  UserService / VehicleService       │
│  WorkOrderService / WorkshopService │
│  RateLimitingService                │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│         JPA Repositories            │  ← Acceso a datos
│  UserJpaRepository                  │
│  VehicleJpaRepository               │
│  WorkOrderJpaRepository             │
│  WorkshopJpaRepository              |
│  WorkOrderLineJpaRepository         |
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│         MySQL 8.0 (Docker)          │  ← Persistencia
└─────────────────────────────────────┘
```

El esquema de base de datos es gestionado por **Flyway** (`V1__Initial_schema.sql`).
La configuración JPA usa `ddl-auto=validate` — Flyway es la única fuente de verdad del esquema.

---

## Modelos de Dominio

### `User` — tabla `users`

| Campo | Tipo | Descripción |
|---|---|---|
| `dni` | `String` (PK) | Identificador único del usuario |
| `name` | `String` | Nombre completo |
| `email` | `String` | Email (usado como username en Spring Security) |
| `phone` | `String` | Teléfono (opcional) |
| `password` | `String` | Hash BCrypt (nunca serializado en JSON) |
| `role` | `Role` | Rol activo del usuario |
| `workshop` | `Workshop` | Taller al que pertenece (nullable) |
| `vehicles` | `List<Vehicle>` | Vehículos en propiedad |
| `pendingWorkshop` | `Workshop` | Taller con invitación pendiente |
| `pendingRole` | `Role` | Rol propuesto en la invitación |

### `Vehicle` — tabla `vehicles`

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | `Long` (PK, auto) | Identificador interno |
| `plate` | `String` (unique) | Matrícula del vehículo |
| `brand` | `String` | Marca |
| `model` | `String` | Modelo |
| `kilometers` | `Long` | Kilometraje |
| `engine` | `String` | Motor |
| `horsePower` | `int` | Potencia (CV) |
| `torque` | `int` | Par motor (Nm) |
| `tires` | `String` | Neumáticos |
| `images` | `List<String>` | URLs de Cloudinary (tabla `vehicle_images`) |
| `lastMaintenance` | `LocalDate` | Fecha último mantenimiento |
| `workshop` | `Workshop` | Taller donde está ingresado (nullable) |
| `owner` | `User` | Propietario |
| `pendingWorkshop` | `Workshop` | Taller con solicitud de ingreso pendiente |

### `WorkOrder` — tabla `work_order`

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | `Long` (PK, auto) | Identificador |
| `description` | `TEXT` | Descripción del trabajo |
| `mechanicNotes` | `TEXT` | Notas del mecánico |
| `status` | `WorkOrderStatus` | Estado: `PENDING`, `IN_PROGRESS`, `COMPLETED` |
| `createdAt` | `LocalDateTime` | Timestamp de creación (automático) |
| `closedAt` | `LocalDate` | Fecha de cierre (se asigna al pasar a `COMPLETED`) |
| `vehicle` | `Vehicle` | Vehículo asociado |
| `mechanic` | `User` | Mecánico asignado |
| `workshop` | `Workshop` | Taller donde se realiza |
| `totalAmount` | `Double` | Importe total calculado |
| `lines` | `List<WorkOrderLine>` | Líneas de detalle |

**Lógica de cálculo de importe** (en `WorkOrder.addWorkOrderLine`):
```
subTotal = (cantidad × precio_unitario) × (1 + IVA%) × (1 - descuento%)
```

### `WorkOrderLine` — tabla `work_order_line`

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | `Long` (PK, auto) | Identificador |
| `concept` | `String` | Descripción del concepto |
| `quantity` | `Double` | Cantidad |
| `pricePerUnit` | `Double` | Precio unitario |
| `IVA` | `Double` | IVA en % (default 0.0) |
| `discount` | `Double` | Descuento en % (default 0.0) |
| `subTotal` | `Double` | Calculado automáticamente |

### `Workshop` — tabla `workshop`

| Campo | Tipo | Descripción |
|---|---|---|
| `workshopId` | `Long` (PK, auto) | Identificador |
| `workshopName` | `String` | Nombre del taller |
| `address` | `String` | Dirección |
| `workshopPhone` | `String` | Teléfono |
| `workshopEmail` | `String` | Email (opcional) |
| `icon` | `String` | URL del icono en Cloudinary |
| `employees` | `List<User>` | Empleados |
| `vehicles` | `List<Vehicle>` | Vehículos ingresados |

---

## Roles del Sistema

El enum `Role` define cinco roles:

| Rol | Descripción |
|---|---|
| `MANAGER` | Administrador de taller. Puede crear/eliminar talleres, contratar/despedir empleados, reasignar órdenes |
| `CO_MANAGER` | Co-administrador. Mismos permisos que MANAGER excepto crear/eliminar talleres |
| `MECHANIC` | Mecánico. Puede gestionar órdenes y vehículos de su taller |
| `CLIENT` | Cliente. Gestiona sus propios vehículos y consulta su historial |

**Regla de registro**: Al registrarse, si se solicita `CO_MANAGER` o `MECHANIC`, el sistema asigna `CLIENT` automáticamente. Solo un `MANAGER` puede promover a esos roles mediante invitación.

---

## Seguridad y Autenticación

### JWT

- El token se almacena en una **cookie HttpOnly** llamada `auth_token`.
- El filtro `JwtAuthenticationFilter` extrae el token de la cookie en cada petición.
- Sin `remember_me`: la cookie es de sesión (`maxAge = -1`).
- Con `remember_me`: la cookie dura **7 días**.
- La cookie usa `SameSite=Lax` y `Secure` configurable por variable de entorno `isSecure`.
- Expiración del token: **86400000 ms (24 horas)** por defecto.

### CSRF

- Protección CSRF habilitada con `CookieCsrfTokenRepository`.
- El cliente debe enviar el token CSRF en la cabecera `X-XSRF-TOKEN`.
- Rutas exentas de CSRF: `/api/auth/**` y `/ws-carlog/**`.

### CORS

- Origen permitido: configurable mediante variable de entorno `URL_CORS`.
- Métodos permitidos: `GET`, `POST`, `PUT`, `PATCH`, `DELETE`, `OPTIONS`.
- Cabeceras permitidas: `Authorization`, `Content-Type`, `X-XSRF-TOKEN`.
- `allowCredentials = true` (necesario para cookies).

### Rate Limiting

Implementado con **Bucket4j** en `RateLimitingService`:
- **5 peticiones por minuto** por IP.
- Aplica únicamente a: `POST /api/auth/authenticate` y `POST /api/auth/register`.
- La caché de IPs se limpia automáticamente cada hora.
- Responde con `HTTP 429` al superar el límite.

### Contraseñas

Cifradas con **BCrypt** (`BCryptPasswordEncoder`).

---

## API REST — Referencia de Endpoints

### Autenticación — `/api/auth`

> Estos endpoints son públicos (no requieren token).

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/api/auth/register` | Registro de nuevo usuario |
| `POST` | `/api/auth/authenticate` | Login |
| `POST` | `/api/auth/logout` | Logout (limpia la cookie) |

**Body de registro** (`RegisterRequest`):
```json
{
  "dni": "12345678A",
  "name": "Juan García",
  "email": "juan@example.com",
  "phone": "600000000",
  "password": "miPassword123",
  "role": "CLIENT",
  "rememberMe": false
}
```

**Body de login** (`AuthenticationRequest`):
```json
{
  "email": "juan@example.com",
  "password": "miPassword123",
  "rememberMe": true
}
```

La respuesta incluye la cookie `auth_token` en la cabecera `Set-Cookie` y el objeto `User` en el body.

---

### Vehículos — `/api/vehicles`

> Todos los endpoints requieren autenticación.

| Método | Ruta | Roles | Descripción |
|---|---|---|---|
| `GET` | `/api/vehicles` | Todos | Lista paginada. Filtra por `workshopId`, `ownerId` o vehículos propios |
| `GET` | `/api/vehicles/{plate}` | Todos | Detalle por matrícula |
| `POST` | `/api/vehicles` | Todos | Crear vehículo (imágenes en Base64, se suben a Cloudinary) |
| `PUT` | `/api/vehicles/{plate}` | Propietario | Editar vehículo |
| `DELETE` | `/api/vehicles/{plate}` | Propietario | Eliminar vehículo (borra imágenes de Cloudinary) |
| `POST` | `/api/vehicles/{plate}/exit/{workshopId}` | MANAGER, CO_MANAGER, MECHANIC | Registrar salida del taller |
| `PUT` | `/api/vehicles/{plate}/request-entry/{workshopId}` | MANAGER, CO_MANAGER, MECHANIC | Solicitar ingreso de vehículo (notifica al propietario por WebSocket) |
| `PUT` | `/api/vehicles/{plate}/approve-entry` | Propietario | Aprobar solicitud de ingreso (notifica al MANAGER por WebSocket) |
| `PUT` | `/api/vehicles/{plate}/reject-entry` | Propietario | Rechazar solicitud de ingreso |
| `POST` | `/api/vehicles/{plate}/transfer` | Propietario | Transferir propiedad a otro usuario (`?newOwnerId=DNI`) |
| `GET` | `/api/vehicles/{plate}/history` | Propietario / Personal taller | Historial de órdenes del vehículo (paginado) |
| `GET` | `/api/vehicles/search` | Todos | Búsqueda por texto (`?q=`, `?workshopId=`, `?type=OWNER\|ASSIGNED\|WORKSHOP`) |

**Parámetros de paginación**: `?page=0&size=10`

---

### Órdenes de Trabajo — `/api/workorders`

> Todos los endpoints requieren rol `MANAGER`, `CO_MANAGER` o `MECHANIC`.

| Método | Ruta | Roles | Descripción |
|---|---|---|---|
| `GET` | `/api/workorders/workshop/{id}` | MANAGER, CO_MANAGER, MECHANIC | Todas las órdenes del taller |
| `GET` | `/api/workorders/vehicle/{plate}` | MANAGER, CO_MANAGER, MECHANIC | Órdenes de un vehículo (paginado) |
| `GET` | `/api/workorders/mechanic/{dni}` | MANAGER, CO_MANAGER, MECHANIC | Órdenes asignadas a un mecánico |
| `GET` | `/api/workorders/{id}` | MANAGER, CO_MANAGER, MECHANIC | Detalle de una orden |
| `POST` | `/api/workorders` | MANAGER, CO_MANAGER, MECHANIC | Crear orden (estado inicial: `PENDING`) |
| `PUT` | `/api/workorders/{workOrderId}` | MANAGER, CO_MANAGER, MECHANIC | Actualizar notas/estado. Al pasar a `COMPLETED` se registra `closedAt` |
| `POST` | `/api/workorders/{id}/lines` | MANAGER, CO_MANAGER, MECHANIC | Añadir línea (cambia estado a `IN_PROGRESS` si estaba `PENDING`) |
| `PUT` | `/api/workorders/{orderId}/lines/{lineId}` | MANAGER, CO_MANAGER, MECHANIC | Editar línea |
| `DELETE` | `/api/workorders/{orderId}/lines/{lineId}` | MANAGER, CO_MANAGER, MECHANIC | Eliminar línea |
| `DELETE` | `/api/workorders/{id}` | MANAGER, CO_MANAGER, MECHANIC | Eliminar orden |
| `PATCH` | `/api/workorders/{orderId}/reassign` | MANAGER, CO_MANAGER | Reasignar mecánico (`?newMechanicId=DNI`) |

**Reglas de negocio**:
- No se pueden añadir, editar ni eliminar líneas en órdenes con estado `COMPLETED`.
- Solo se pueden modificar órdenes de vehículos que siguen ingresados en el taller (modo lectura si el vehículo salió).
- Un mecánico solo puede ver/modificar órdenes de su propio taller.

**Body de creación** (`NewWorkOrderDTO`):
```json
{
  "description": "Revisión de frenos",
  "vehiclePlate": "1234ABC"
}
```

**Body de línea** (`NewWorkOrderLineDTO`):
```json
{
  "concept": "Pastillas de freno",
  "quantity": 4,
  "pricePerUnit": 25.00,
  "IVA": 21.0,
  "discount": 0.0
}
```

---

### Talleres — `/api/workshop`

| Método | Ruta | Roles | Descripción |
|---|---|---|---|
| `GET` | `/api/workshop/details/{id}` | MANAGER, CO_MANAGER, MECHANIC | Detalle del taller |
| `GET` | `/api/workshop/{id}/employees` | MANAGER, CO_MANAGER, MECHANIC | Lista de empleados |
| `POST` | `/api/workshop` | MANAGER | Crear taller (el creador se convierte en MANAGER del taller) |
| `PUT` | `/api/workshop/details/{id}` | MANAGER, CO_MANAGER | Editar taller (multipart/form-data, icono opcional) |
| `DELETE` | `/api/workshop/details/{id}` | MANAGER | Eliminar taller |

**Regla**: Un usuario solo puede crear un taller si no pertenece ya a otro.

---

### Gestión de Empleados

Las siguientes operaciones se gestionan a través de `WorkshopController` y `UserService`:

| Operación | Descripción |
|---|---|
| `inviteToWorkshop` | MANAGER/CO_MANAGER invita a un usuario por DNI con un rol propuesto. Envía notificación WebSocket. |
| `acceptInvitation` | El usuario acepta la invitación, se le asigna el taller y el rol. Notifica al MANAGER. |
| `rejectInvitation` | El usuario rechaza la invitación. |
| `fireEmployee` | MANAGER despide a un empleado (rol vuelve a `CLIENT`, taller a `null`). Notifica al empleado. |

---

## Flujo de Trabajo Principal

```
1. MANAGER crea el taller
        │
        ▼
2. MANAGER invita a mecánicos (WebSocket → notificación al mecánico)
        │
        ▼
3. Mecánico acepta la invitación (WebSocket → notificación al MANAGER)
        │
        ▼
4. MANAGER/MECHANIC solicita ingreso de vehículo (WebSocket → notificación al propietario)
        │
        ▼
5. Propietario aprueba el ingreso (WebSocket → notificación al MANAGER)
        │
        ▼
6. MECHANIC crea orden de trabajo (estado: PENDING)
        │
        ▼
7. MECHANIC añade líneas → estado cambia a IN_PROGRESS
        │
        ▼
8. MANAGER/MECHANIC cierra la orden → estado COMPLETED, se registra closedAt
        │
        ▼
9. MANAGER/MECHANIC registra la salida del vehículo
```

---

## Notificaciones en Tiempo Real

**Endpoint STOMP**: `ws://host/ws-carlog`

**Prefijo de aplicación**: `/app`  
**Broker de mensajes**: `/topic`

### Canal de suscripción

```
/topic/notificaciones/{dni}
```

Cada usuario se suscribe a su propio canal usando su DNI.

### Tipos de notificación (`NotificationDTO`)

| `type` | `title` | Cuándo se envía |
|---|---|---|
| `INVITE` | "¡Nueva oferta de empleo!" | Un taller invita al usuario |
| `NEW_EMPLOYEE` | "¡Nuevo empleado en el taller!" | Un empleado acepta la invitación |
| `FIRE` | "Permisos revocados" | El usuario es despedido |
| `VEHICLE_REQUEST` | "Solicitud de Ingreso" | Un taller solicita ingresar un vehículo |
| `NEW_FLEET_VEHICLE` | "¡Nuevo vehiculo ingresado!" | El propietario aprueba el ingreso |

**Estructura del mensaje**:
```json
{
  "type": "INVITE",
  "title": "¡Nueva oferta de empleo!",
  "message": "El taller AutoTaller SL quiere contratarte",
  "extraData": null
}
```

---

## Integración con Cloudinary

Las imágenes se almacenan en Cloudinary en dos carpetas:

| Recurso | Carpeta en Cloudinary |
|---|---|
| Imágenes de vehículos | `carlog/vehicles/` |
| Iconos de talleres | `carlog/workshops/` |

**Formatos de entrada aceptados**:
- `data:image/...;base64,...` — se sube directamente.
- `https://...` — se conserva la URL existente (no se re-sube).

Al eliminar un vehículo o cambiar el icono de un taller, las imágenes antiguas se eliminan automáticamente de Cloudinary.

---

## Manejo de Errores

Todas las excepciones son capturadas por `GlobalExceptionHandler` (`@RestControllerAdvice`).

**Formato de respuesta de error**:
```json
{
  "message": "Descripción del error",
  "status": 404,
  "timestamp": 1713350400000
}
```

Basado en el análisis del `GlobalExceptionHandler`, aquí está la tabla completa de excepciones del sistema:

| Código HTTP | Excepción | Causa |
|---|---|---|
| `404` | `VehicleNotFoundException` | Matrícula no encontrada |
| `404` | `UserNotFoundException` | DNI o email no encontrado |
| `404` | `WorkOrderNotFoundException` | ID de orden no existe |
| `404` | `WorkshopNotFoundException` | ID de taller no existe |
| `404` | `WorkOrderLineNotFoundException` | Línea de orden no encontrada |
| `400` | `WorkshopNotAssignedException` | El mecánico/manager no tiene taller asignado |
| `400` | `VehicleNotInWorkshopException` | El vehículo no está en el taller especificado |
| `400` | `ClosedWorkOrderException` | Intento de modificar orden cerrada |
| `400` | `WorkOrderLineMismatchException` | La línea no pertenece a la orden |
| `400` | `MechanicNotInWorkshopException` | El mecánico no pertenece al taller |
| `400` | `NoPendingRequestException` | No hay solicitud pendiente de vehículo |
| `400` | `InvalidSearchTypeException` | Tipo de búsqueda no válido |
| `400` | `InvalidRegistrationException` | Registro inválido (auto-contratación) |
| `400` | `NoPendingInvitationException` | No hay invitación pendiente |
| `403` | `UnauthorizedActionException` | Acceso denegado por permisos |
| `409` | `VehicleOcuppiedException` | Vehículo ya asignado a otro taller |
| `409` | `VehicleAlreadyExistsException` | Ya existe vehículo con esa matrícula |
| `409` | `UserAlreadyExistsException` | Ya existe usuario con ese DNI |
| `409` | `UserAlreadyInWorkshopException` | Usuario ya pertenece a un taller |
| `409` | `WorkshopAlreadyExistsException` | Ya existe un taller con esos datos |
| `409` | `UserAlreadyHasWorkshopException` | Usuario ya tiene taller asignado |
| `401` | `BadCredentialsException` | Credenciales incorrectas |
| `429` | `RateLimitExceededException` | Demasiados intentos de login/registro |
| `500` | `Exception` | Error interno no controlado | [1](#1-0) 

## Notas

Las excepciones están agrupadas por código HTTP en el `GlobalExceptionHandler` [2](#1-1) . Las excepciones de negocio (400) cubren validaciones de reglas del dominio, mientras que las de conflicto (409) manejan duplicados y estados inconsistentes. El rate limiting se aplica específicamente a endpoints de autenticación [3](#1-2).

## Documentación de la API (Swagger)

Disponible en:
- UI: `http://localhost:8081/swagger-ui.html`
- JSON: `http://localhost:8081/v3/api-docs`

---

## Estructura del Proyecto

```
backend/
├── src/main/java/com/carlog/backend/
│   ├── auth/                    # Registro, login, logout
│   │   ├── AuthenticationController.java
│   │   ├── AuthenticationService.java
│   │   ├── AuthenticationRequest.java
│   │   ├── AuthenticationResponse.java
│   │   └── RegisterRequest.java
│   ├── config/                  # Configuración de Spring
│   │   ├── SecurityConfig.java
│   │   ├── ApplicationConfig.java
│   │   ├── JwtAuthenticationFilter.java
│   │   ├── CsrfCookieFilter.java
│   │   ├── RateLimitingInterceptor.java
│   │   ├── WebMvcConfig.java
│   │   ├── WebSocketConfig.java
│   │   ├── CloudinaryConfig.java
│   │   └── WebConfig.java
│   ├── controller/              # Endpoints REST
│   │   ├── VehicleController.java
│   │   ├── WorkOrderController.java
│   │   └── WorkshopController.java
|   |   └── UserController.java
|   |   └── RoleConverter.java
│   ├── dto/                     # Records de entrada/salida
│   ├── error/                   # Excepciones y GlobalExceptionHandler
│   ├── model/                   # Entidades JPA
│   ├── repository/              # Interfaces Spring Data JPA
│   ├── security/                # JwtService
│   └── service/                 # Lógica de negocio
└── src/main/resources/
    ├── application.properties
    └── db/migration/
        └── V1__Initial_schema.sql
BBDD_CARLOG/
└── docker-compose.yml
```

---

## Inicio Rápido

```bash
# 1. Levantar MySQL
cd BBDD_CARLOG && docker-compose up -d

# 2. Crear el archivo .env en backend/
cp .env.example backend/.env
# Editar con tus valores

# 3. Compilar y arrancar
cd backend
./mvnw spring-boot:run
```

El servidor arranca en el puerto **8081**.

---

## Configuración

### Variables de Entorno

El proyecto usa `spring-dotenv` para cargar un archivo `.env` en desarrollo.

| Variable | Descripción | Obligatoria |
|---|---|---|
| `DB_HOST` | Host de MySQL (default: `127.0.0.1`) | No |
| `DB_USER` | Usuario de MySQL | Sí |
| `DB_PASSWORD` | Contraseña de MySQL | Sí |
| `JWT_SECRET_KEY` | Clave secreta JWT en Base64 (mínimo 256 bits) | Sí |
| `URL_CORS` | Origen permitido para CORS y WebSocket | Sí |
| `isSecure` | `true` en producción (cookie Secure) | Sí |
| `API_NAME` | Cloudinary cloud name | Sí |
| `CLOUDINARY_API_KEY` | Cloudinary API key | Sí |
| `API_SECRET` | Cloudinary API secret | Sí |
| `DB_ROOT_PASSWORD` | Contraseña root de MySQL (solo Docker) | Sí (Docker) |

**Ejemplo de `.env` para desarrollo**:
```env
DB_HOST=127.0.0.1
DB_USER=carlog_user
DB_PASSWORD=carlog_pass
DB_ROOT_PASSWORD=root_pass
JWT_SECRET_KEY=<base64_de_al_menos_32_bytes>
URL_CORS=http://localhost:4200
isSecure=false
API_NAME=tu_cloud_name
CLOUDINARY_API_KEY=tu_api_key
API_SECRET=tu_api_secret
```

### Base de Datos con Docker

El directorio `BBDD_CARLOG/` contiene el `docker-compose.yml` para levantar MySQL:

```bash
cd BBDD_CARLOG
docker-compose up -d
```

Esto crea:
- Contenedor `carlog-mysql` con MySQL 8.0
- Base de datos `carlog_db`
- Puerto expuesto solo en `127.0.0.1:3306` (no accesible desde el exterior)
- Vol

---

## Licencia

Este proyecto está bajo licencia **MIT**. Consulta el archivo [LICENSE](LICENSE) para más detalles.

---

*Desarrollado por [JaviRSDEV](https://github.com/JaviRSDEV)*

## Documentación
- **[API Reference](./README.md)** - Documentación completa de endpoints REST
- **[Documentación Técnica (ESP)](./docs/CARLOG_DOCUMENTATION_ESP.pdf)** - Documentación técnica en español
- **[Technical Documentation (ENG)](./docs/CARLOG_DOCUMENTATION_ENG.pdf)** - Technical Documentation in english
```

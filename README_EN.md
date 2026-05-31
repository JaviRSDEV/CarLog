# CarLog — Comprehensive Management Platform for Mechanical Workshops

![Spring Boot](https://img.shields.io/badge/Backend-Spring_Boot_4.0.1-6db33f?style=for-the-badge&logo=spring)
![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk)
![MySQL](https://img.shields.io/badge/Database-MySQL_8.0-4479a1?style=for-the-badge&logo=mysql)
![Docker](https://img.shields.io/badge/Deploy-Docker-2496ed?style=for-the-badge&logo=docker)
![JWT](https://img.shields.io/badge/Auth-JWT-black?style=for-the-badge&logo=jsonwebtokens)
![WebSocket](https://img.shields.io/badge/RealTime-WebSocket_STOMP-010101?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)
[![Spanish README](https://img.shields.io/badge/Language-Spanish-red?style=for-the-badge)](./README.md)

## Project Status

[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=JaviRSDEV_CarLog&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=JaviRSDEV_CarLog)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=JaviRSDEV_CarLog&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=JaviRSDEV_CarLog)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=JaviRSDEV_CarLog&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=JaviRSDEV_CarLog)

---

## Description

**CarLog** is a **Full Stack** platform for the comprehensive management of mechanical workshops. It provides a robust and scalable RESTful architecture covering a vehicle's entire lifecycle: from check-in and work order creation to final billing and check-out.

The system is designed to support multiple user roles, ensuring an experience tailored to each actor in the automotive repair ecosystem.

---

## Project Structure

- **Backend (Spring Boot 4.0.1)**: This repository contains the REST API, WebSockets, security, business logic, and validations.
- **Frontend (Angular 21+)**: Repository with the SPA, state management with Signals, and UI: https://github.com/JaviRSDEV/FrontCarLog

---

## Table of Contents

- [Main Features](#main-features)
- [Tech Stack](#tech-stack)
- [System Architecture](#system-architecture)
- [Domain Models](#domain-models)
- [System Roles](#system-roles)
- [Security and Authentication](#security-and-authentication)
- [REST API — Endpoint Reference](#rest-api--endpoint-reference)
- [Main Workflow](#main-workflow)
- [Real-Time Notifications](#real-time-notifications)
- [Billing System](#billing-system)
- [Email Notifications](#email-notifications)
- [Automated Alert System](#automated-alert-system)
- [Vehicle Transfer System with Handshake](#vehicle-transfer-system-with-handshake)
- [Password Recovery System](#password-recovery-system)
- [Authenticated Password Change](#authenticated-password-change)
- [Enhanced HTML Email System](#enhanced-html-email-system)
- [Vehicle Catalog](#vehicle-catalog)
- [Cloudinary Integration](#cloudinary-integration)
- [Error Handling](#error-handling)
- [API Documentation (Swagger)](#api-documentation-swagger)
- [Project Directory Structure](#project-directory-structure)
- [Quick Start](#quick-start)
- [Configuration](#configuration)
- [License](#license)

---

## Main Features

- **Comprehensive Vehicle Management**: registration, updates, repair history, and ownership transfers.
- **Work Orders**: full lifecycle (PENDING → IN_PROGRESS → COMPLETED) with automatic calculation of totals, VAT (IVA), and discounts.
- **Workshop Check-in/Check-out Protocol**: double-confirmation handshake between the owner and the workshop.
- **Employee Management**: flow of invitation → acceptance/rejection → termination of employment.
- **Real-Time Notifications**: via WebSocket (STOMP) for critical workshop events.
- **Stateless Authentication**: with JWT and Role-Based Access Control (RBAC).
- **Image Management**: upload of vehicle photos and workshop icons (Base64 and Multipart).
- **Centralized Error Handling**: with standardized JSON responses.
- **Containerized Infrastructure**: with Docker Compose (MySQL + phpMyAdmin).
- **Complete European Car Catalog**: +100 brands, +500 models, +3000 versions with technical specifications (engine, power, torque, production years).
- **Administration Panel**: Dashboard with global statistics for the ADMIN role.
- **PDF Invoice Generation**: Billing system with HTML templates and PDF conversion.
- **Email Notification System**: Automatic emails for key events (completed orders, vehicle check-ins, hirings).
- **External API Sync**: Integration with NHTSA for the American vehicle catalog.
- **Payment Status Management**: New `PaymentStatus` enum for tracking payments.

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Spring Boot | 4.0.1 | Main Framework |
| Java | 21 | Programming Language |
| Spring Security | (boot managed) | Authentication and Authorization |
| Spring Data JPA | (boot managed) | Data Access |
| MySQL | 8.0 | Database |
| Flyway | 10.10.0 | Schema Migrations |
| JJWT | 0.12.6 | JWT Generation and Validation |
| Bucket4j | 8.10.1 | Rate limiting |
| Cloudinary | 2.0.0 | Image Storage |
| Spring WebSocket (STOMP) | 4.0.1 | Real-time notifications |
| SpringDoc OpenAPI | 3.0.0 | Swagger Documentation |
| Lombok | (boot managed) | Boilerplate reduction |
| spring-dotenv | 4.0.0 | Environment variables from `.env` |
| Apache Tika | 2.9.1 | MIME type detection |
| OpenHTMLToPDF | 1.0.10 | PDF Generation |
| Thymeleaf | (boot managed) | HTML templates for emails and invoices |
| Spring Mail | (boot managed) | Email sending |
| Spring Async | (boot managed) | Asynchronous processing |

---

## System Architecture

```
Client (Angular / Postman)
        │
        ▼
┌─────────────────────────────────────┐
│         REST Controllers            │  ← HTTP Layer
│  AuthenticationController           │
│  VehicleController                  │
│  WorkOrderController                │
│  WorkshopController                 │
│  UserController                     │
│  AdminController                    │
│  CarCatalogController               │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│           Service Layer             │  ← Business Logic
│  UserService / VehicleService       │
│  WorkOrderService / WorkshopService │
│  AdminService                       │
│  InvoiceService                     │
│  MailService                        │
│  CarCatalogService                  │
│  BrandSyncService                   │
│  RateLimitingService                │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│         JPA Repositories            │  ← Data Access
│  UserJpaRepository                  │
│  VehicleJpaRepository               │
│  WorkOrderJpaRepository             │
│  WorkshopJpaRepository              │
│  WorkOrderLineJpaRepository         │
│  CarBrandJpaRepository              │
│  CarModelJpaRepository              │
│  CarVersionJpaRepository            │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│         MySQL 8.0 (Docker)          │  ← Persistence
└─────────────────────────────────────┘
```

The database schema is managed by **Flyway** (`V1__Initial_schema.sql` to `V5__Clean_DB.sql`).
The JPA configuration uses `ddl-auto=validate` — Flyway is the single source of truth for the database schema.

---

## Domain Models

### `User` — table `users`

| Field | Type | Description |
|---|---|---|
| `dni` | `String` (PK) | Unique user identifier |
| `name` | `String` | Full name |
| `email` | `String` | Email (used as username in Spring Security) |
| `phone` | `String` | Phone number (optional) |
| `password` | `String` | BCrypt hash (never serialized in JSON) |
| `role` | `Role` | Active user role |
| `workshop` | `Workshop` | Associated workshop (nullable) |
| `vehicles` | `List<Vehicle>` | Owned vehicles |
| `pendingWorkshop` | `Workshop` | Workshop with pending invitation |
| `pendingRole` | `Role` | Proposed role in the invitation |

### `Vehicle` — table `vehicles`

| Field | Type | Description |
|---|---|---|
| `id` | `Long` (PK, auto) | Internal identifier |
| `plate` | `String` (unique) | Vehicle license plate |
| `brand` | `String` | Brand |
| `model` | `String` | Model |
| `kilometers` | `Long` | Mileage |
| `engine` | `String` | Engine code/details |
| `horsePower` | `int` | Power (HP/CV) |
| `torque` | `int` | Engine torque (Nm) |
| `tires` | `String` | Tire specifications |
| `images` | `List<String>` | Cloudinary URLs (table `vehicle_images`) |
| `lastMaintenance` | `LocalDate` | Last maintenance date |
| `workshop` | `Workshop` | Workshop where checked in (nullable) |
| `owner` | `User` | Owner details |
| `pendingWorkshop` | `Workshop` | Workshop with pending entry request |

### `WorkOrder` — table `work_order`

| Field | Type | Description |
|---|---|---|
| `id` | `Long` (PK, auto) | Work order ID |
| `description` | `TEXT` | Job description |
| `mechanicNotes` | `TEXT` | Notes from the mechanic |
| `status` | `WorkOrderStatus` | Status: `PENDING`, `IN_PROGRESS`, `COMPLETED` |
| `createdAt` | `LocalDateTime` | Timestamp of creation (automatic) |
| `closedAt` | `LocalDate` | Closure date (set when passing to `COMPLETED`) |
| `vehicle` | `Vehicle` | Associated vehicle |
| `mechanic` | `User` | Assigned mechanic |
| `workshop` | `Workshop` | Workshop where repair is conducted |
| `totalAmount` | `Double` | Calculated total amount |
| `lines` | `List<WorkOrderLine>` | Detailed invoice lines |

**Amount calculation logic** (in `WorkOrder.addWorkOrderLine`):
```
subTotal = (quantity × price_per_unit) × (1 + VAT%) × (1 - discount%)
```

### `WorkOrderLine` — table `work_order_line`

| Field | Type | Description |
|---|---|---|
| `id` | `Long` (PK, auto) | Identifier |
| `concept` | `String` | Concept description |
| `quantity` | `Double` | Quantity |
| `pricePerUnit` | `Double` | Unit price |
| `IVA` | `Double` | VAT in % (default 0.0) |
| `discount` | `Double` | Discount in % (default 0.0) |
| `subTotal` | `Double` | Automatically calculated |

### `Workshop` — table `workshop`

| Field | Type | Description |
|---|---|---|
| `workshopId` | `Long` (PK, auto) | Identifier |
| `workshopName` | `String` | Name of the workshop |
| `address` | `String` | Address |
| `workshopPhone` | `String` | Telephone |
| `workshopEmail` | `String` | Email (optional) |
| `icon` | `String` | Cloudinary URL of the logo/icon |
| `employees` | `List<User>` | Employees |
| `vehicles` | `List<Vehicle>` | Checked-in vehicles |

### `CarBrand` — table `car_brands`

| Field | Type | Description |
|---|---|---|
| `id` | `Long` (PK, auto) | Identifier |
| `name` | `String` (unique) | Brand name |

### `CarModel` — table `car_models`

| Field | Type | Description |
|---|---|---|
| `id` | `Long` (PK, auto) | Identifier |
| `name` | `String` | Model name |
| `brand` | `CarBrand` | Associated brand |

### `CarVersion` — table `car_version`

| Field | Type | Description |
|---|---|---|
| `id` | `Long` (PK, auto) | Identifier |
| `carModel` | `CarModel` | Associated model |
| `versionName` | `String` | Version name |
| `engineCode` | `String` | Engine code |
| `engineType` | `String` | Engine type |
| `fuelType` | `String` | Fuel type |
| `powerCv` | `Integer` | Power in HP (CV) |
| `torque` | `Integer` | Engine torque in Nm |
| `yearStart` | `Integer` | Production start year |
| `yearEnd` | `Integer` | Production end year |

### `PaymentStatus` — Enum

| Value | Description |
|---|---|
| `PENDING` | Payment pending |
| `PAID` | Paid |
| `CANCELLED` | Cancelled |

---

## System Roles

The `Role` enum defines five roles:

| Role | Description |
|---|---|
| `ADMIN` | Global Administrator. Access to global statistics and complete system management |
| `MANAGER` | Workshop Manager. Can create/delete workshops, hire/fire employees, and reassign orders |
| `CO_MANAGER` | Workshop Co-manager. Same permissions as MANAGER except creating/deleting workshops |
| `MECHANIC` | Mechanic. Can manage orders and vehicles within their workshop |
| `CLIENT` | Client. Manages their own vehicles and views their repair history |

**Registration Rule**: During registration, if a user requests the `CO_MANAGER` or `MECHANIC` role, the system automatically assigns `CLIENT`. Only a `MANAGER` can promote them to those roles via invitation.

---

## Security and Authentication

### JWT

- The token is stored in a secure **HttpOnly cookie** named `auth_token`.
- The `JwtAuthenticationFilter` extracts the token from the cookie on each request.
- Without `remember_me`: the cookie is session-scoped (`maxAge = -1`).
- With `remember_me`: the cookie lasts for **7 days**.
- The cookie uses `SameSite=Lax` and `Secure` configurable via the `isSecure` environment variable.
- Token expiration: **86400000 ms (24 hours)** by default.

### CSRF

- CSRF protection enabled with `CookieCsrfTokenRepository`.
- The client must send the CSRF token in the `X-XSRF-TOKEN` header.
- Paths exempted from CSRF: `/api/auth/**` and `/ws-carlog/**`.

### CORS

- Allowed origin: configurable via the `URL_CORS` environment variable.
- Allowed methods: `GET`, `POST`, `PUT`, `PATCH`, `DELETE`, `OPTIONS`.
- Allowed headers: `Authorization`, `Content-Type`, `X-XSRF-TOKEN`.
- `allowCredentials = true` (necessary for cookie inclusion).

### Rate Limiting

Implemented using **Bucket4j** inside `RateLimitingService`:
- **5 requests per minute** per IP.
- Applies only to: `POST /api/auth/authenticate` and `POST /api/auth/register`.
- The IP cache is automatically cleared every hour.
- Responds with `HTTP 429` when the limit is exceeded.

### Passwords

Encrypted using **BCrypt** (`BCryptPasswordEncoder`).

---

## REST API — Endpoint Reference

### Authentication — `/api/auth`

> These endpoints are public (do not require a token).

| Method | Path | Description |
|---|---|---|
| `POST` | `/api/auth/register` | Registers a new user |
| `POST` | `/api/auth/authenticate` | Log in |
| `POST` | `/api/auth/logout` | Log out (clears cookie) |

**Registration Body** (`RegisterRequest`):
```json
{
  "dni": "12345678A",
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "600000000",
  "password": "myPassword123",
  "role": "CLIENT",
  "rememberMe": false
}
```

**Login Body** (`AuthenticationRequest`):
```json
{
  "email": "john@example.com",
  "password": "myPassword123",
  "rememberMe": true
}
```

The response includes the `auth_token` cookie in the `Set-Cookie` header and the `User` object in the body.

---

### Vehicles — `/api/vehicles`

> All endpoints require authentication.

| Method | Path | Roles | Description |
|---|---|---|---|
| `GET` | `/api/vehicles` | All | Paginated list. Filter by `workshopId`, `ownerId`, or owned vehicles |
| `GET` | `/api/vehicles/{plate}` | All | Detailed vehicle specifications by plate |
| `POST` | `/api/vehicles` | All | Create a vehicle (Base64 images, uploaded to Cloudinary) |
| `PUT` | `/api/vehicles/{plate}` | Owner | Edit vehicle details |
| `DELETE` | `/api/vehicles/{plate}` | Owner | Delete vehicle (removes images from Cloudinary) |
| `POST` | `/api/vehicles/{plate}/exit/{workshopId}` | MANAGER, CO_MANAGER, MECHANIC | Register check-out from workshop |
| `PUT` | `/api/vehicles/{plate}/request-entry/{workshopId}` | MANAGER, CO_MANAGER, MECHANIC | Request workshop entry (notifies owner via WebSocket) |
| `PUT` | `/api/vehicles/{plate}/approve-entry` | Owner | Approve entry request (notifies MANAGER via WebSocket) |
| `PUT` | `/api/vehicles/{plate}/reject-entry` | Owner | Reject entry request |
| `POST` | `/api/vehicles/{plate}/transfer` | Owner | Request ownership transfer (`?newOwnerId=DNI`) |
| `GET` | `/api/vehicles/{plate}/history` | Owner / Workshop Staff | Paginated maintenance history of the vehicle |
| `GET` | `/api/vehicles/search` | All | Text-based search (`?q=`, `?workshopId=`, `?type=OWNER\|ASSIGNED\|WORKSHOP`) |

**Pagination parameters**: `?page=0&size=10`

---

### Work Orders — `/api/workorders`

> All endpoints require the `MANAGER`, `CO_MANAGER`, or `MECHANIC` role.

| Method | Path | Roles | Description |
|---|---|---|---|
| `GET` | `/api/workorders/workshop/{id}` | MANAGER, CO_MANAGER, MECHANIC | All work orders of the workshop |
| `GET` | `/api/workorders/vehicle/{plate}` | MANAGER, CO_MANAGER, MECHANIC | Paginated work orders of a vehicle |
| `GET` | `/api/workorders/mechanic/{dni}` | MANAGER, CO_MANAGER, MECHANIC | Work orders assigned to a mechanic |
| `GET` | `/api/workorders/{id}` | MANAGER, CO_MANAGER, MECHANIC | Details of a work order |
| `POST` | `/api/workorders` | MANAGER, CO_MANAGER, MECHANIC | Create work order (initial status: `PENDING`) |
| `PUT` | `/api/workorders/{workOrderId}` | MANAGER, CO_MANAGER, MECHANIC | Update notes/status. Sets `closedAt` when moving to `COMPLETED` |
| `POST` | `/api/workorders/{id}/lines` | MANAGER, CO_MANAGER, MECHANIC | Add invoice line (moves status to `IN_PROGRESS` if it was `PENDING`) |
| `PUT` | `/api/workorders/{orderId}/lines/{lineId}` | MANAGER, CO_MANAGER, MECHANIC | Edit invoice line |
| `DELETE` | `/api/workorders/{orderId}/lines/{lineId}` | MANAGER, CO_MANAGER, MECHANIC | Delete invoice line |
| `DELETE` | `/api/workorders/{id}` | MANAGER, CO_MANAGER, MECHANIC | Delete work order |
| `PATCH` | `/api/workorders/{orderId}/reassign` | MANAGER, CO_MANAGER | Reassign mechanic (`?newMechanicId=DNI`) |

**Business Rules**:
- Invoice lines cannot be added, edited, or deleted in work orders with status `COMPLETED`.
- Work orders can only be modified if the vehicle is still checked into the workshop (read-only mode after exit).
- A mechanic can only view/modify work orders belonging to their own workshop.

**Creation Body** (`NewWorkOrderDTO`):
```json
{
  "description": "Brake system check",
  "vehiclePlate": "1234ABC"
}
```

**Line Body** (`NewWorkOrderLineDTO`):
```json
{
  "concept": "Brake pads",
  "quantity": 4,
  "pricePerUnit": 25.00,
  "IVA": 21.0,
  "discount": 0.0
}
```

---

### Workshops — `/api/workshop`

| Method | Path | Roles | Description |
|---|---|---|---|
| `GET` | `/api/workshop/details/{id}` | MANAGER, CO_MANAGER, MECHANIC | Workshop details |
| `GET` | `/api/workshop/{id}/employees` | MANAGER, CO_MANAGER, MECHANIC | Employee list |
| `POST` | `/api/workshop` | MANAGER | Create workshop (creator becomes the workshop's MANAGER) |
| `PUT` | `/api/workshop/details/{id}` | MANAGER, CO_MANAGER | Edit workshop (multipart/form-data, optional icon) |
| `DELETE` | `/api/workshop/details/{id}` | MANAGER | Delete workshop |

**Rule**: A user can only create a workshop if they do not already belong to another one.

---

### Employee Management

The following operations are managed through the `WorkshopController` and `UserService`:

| Operation | Description |
|---|---|
| `inviteToWorkshop` | MANAGER/CO_MANAGER invites a user by DNI with a proposed role. Sends WebSocket notification. |
| `acceptInvitation` | User accepts the invitation; the workshop and role are assigned. Notifies the MANAGER. |
| `rejectInvitation` | User rejects the invitation. |
| `fireEmployee` | MANAGER fires an employee (role reverts to `CLIENT`, workshop becomes `null`). Notifies employee. |

---

### Administration — `/api/admin`

> All endpoints require the `ADMIN` role.

| Method | Path | Description |
|---|---|---|
| `GET` | `/api/admin/stats` | Global system statistics |
| `GET` | `/api/admin/users` | Paginated list of all users |
| `GET` | `/api/admin/workshops` | Paginated list of all workshops |
| `GET` | `/api/admin/vehicles` | Paginated list of all vehicles |
| `GET` | `/api/admin/workorders` | Paginated list of all work orders |
| `GET` | `/api/admin/catalog/brands` | Paginated list of all catalog brands |
| `GET` | `/api/admin/catalog/models` | Paginated list of all catalog models |
| `GET` | `/api/admin/catalog/versions` | Paginated list of all catalog versions |

---

### Vehicle Catalog — `/api/catalog`

> All endpoints are public.

| Method | Path | Description |
|---|---|---|
| `GET` | `/api/catalog/brands` | List of all brands |
| `GET` | `/api/catalog/brands/{brandId}/models` | Models of a specific brand |
| `GET` | `/api/catalog/models/{modelId}/versions` | Versions of a specific model |
| `GET` | `/api/catalog/versions/{versionId}` | Details of a specific version |
| `POST` | `/api/catalog/sync` | Sync catalog with external API (NHTSA) |

---

## Main Workflow

```
1. MANAGER creates a workshop
        │
        ▼
2. MANAGER invites mechanics (WebSocket → notification sent to mechanic)
        │
        ▼
3. Mechanic accepts invitation (WebSocket → notification sent to MANAGER)
        │
        ▼
4. MANAGER/MECHANIC requests vehicle entry (WebSocket → notification sent to owner)
        │
        ▼
5. Owner approves vehicle entry (WebSocket → notification sent to MANAGER)
        │
        ▼
6. MECHANIC creates work order (status: PENDING)
        │
        ▼
7. MECHANIC adds lines → status transitions to IN_PROGRESS
        │
        ▼
8. MANAGER/MECHANIC completes order → status COMPLETED, closedAt timestamp is registered
        │
        ▼
9. MANAGER/MECHANIC registers vehicle exit
```

---

## Real-Time Notifications

**STOMP Endpoint**: `ws://host/ws-carlog`

**Application Prefix**: `/app`  
**Message Broker**: `/topic`

### Subscription Channel

```
/topic/notificaciones/{dni}
```

Each user subscribes to their own personal channel using their DNI.

### Notification Types (`NotificationDTO`)

| `type` | `title` | Sent When |
|---|---|---|
| `INVITE` | "New job offer!" | A workshop invites the user as an employee |
| `NEW_EMPLOYEE` | "New employee in the workshop!" | An employee accepts the invitation |
| `FIRE` | "Permissions revoked" | An employee is fired |
| `VEHICLE_REQUEST` | "Check-in Request" | A workshop requests a vehicle entry |
| `NEW_FLEET_VEHICLE` | "New vehicle checked in!" | The owner approves the check-in request |

**Message Structure**:
```json
{
  "type": "INVITE",
  "title": "New job offer!",
  "message": "AutoTaller SL wants to hire you",
  "extraData": null
}
```

---

## Billing System

The system includes PDF invoice generation via `InvoiceService`:

### Features

- **Rendering Engine**: OpenHTMLToPDF with PDFBox.
- **Templates**: Thymeleaf for HTML invoice designs.
- **Automatic Calculations**: Tax base, VAT, discounts, and total amount.
- **Historical Data**: Preserves vehicle details even if the vehicle is deleted from the active system.

### Generation Process

1. Retrieves the work order by ID.
2. Calculates totals (tax base, VAT, discounts).
3. Prepares context variables with details of the vehicle, client, and workshop.
4. Renders the HTML template using Thymeleaf.
5. Converts the rendered HTML to PDF using OpenHTMLToPDF.
6. Returns the PDF as a byte array.

### Endpoint

| Method | Path | Description |
|---|---|---|
| `GET` | `/api/workorders/{id}/invoice` | Generates a PDF invoice for a work order |

---

## Email Notifications

The system sends automatic emails via `MailService` using JavaMailSender and Thymeleaf:

### Email Types

| Type | Recipient | Subject | Sent When |
|---|---|---|---|
| Completed Order | Client | "Good news! Your vehicle is ready - CarLog" | Upon closing a work order |
| Vehicle Entry | Client | "Check-in request sent - CarLog" | Upon requesting a vehicle entry |
| Job Offer | User | "Job Offer - CarLog" | Upon inviting an employee |

---

## Automated Alert System

A comprehensive system for custom alerts with automatic email notifications for crucial vehicle events.

### Features

- **Custom Alerts**: Users can create alerts specifying a title, description, and deadline associated with their vehicles.
- **Automatic Notifications**: Scheduled service executing daily at 8:00 AM.
- **Dual Reminder**:
  - Notification 1 week before the deadline.
  - Notification on the day of the deadline.
- **Automatic Maintenance**: Upon completing a work order, a "Yearly Maintenance" alert is automatically scheduled for 1 year later.

### Endpoints

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/api/alerts` | Get all alerts of the authenticated user |
| `POST` | `/api/alerts` | Create a new alert |
| `PUT` | `/api/alerts/{id}` | Update an existing alert |
| `DELETE` | `/api/alerts/{id}` | Delete an alert |

### Entities

- **Alert**: Entity containing title, description, deadline, vehicle, user, and notification flags (`notifiedOneWeek`, `notifiedToday`).
- **AlertSchedulerService**: Scheduled task wrapper with `@Scheduled(cron = "0 0 8 * * ?")` for alert scans and notification dispatches.

---

## Vehicle Transfer System with Handshake

Vehicle ownership transfer protocol requiring double confirmation between parties to guarantee transactional security.

### Transfer Flow

1. **Request**: The current owner requests to transfer the vehicle to another user by specifying their DNI.
2. **Approval**: The recipient accepts the transfer to finalize the ownership shift.
3. **Rejection**: Either party can cancel the pending transfer request.

### Features

- **Double-Confirmation Handshake**: Requires consent from both parties.
- **Real-Time Notifications**: WebSocket notifications alert users of pending changes.
- **Validations**:
  - Cannot transfer a vehicle to oneself.
  - Only the current owner or the pending recipient can approve/reject the transaction.
- **Data Preservation**: On transfer success, pending fields are cleared, and vehicle owner links are updated.

### Endpoints

| Method | Path | Description |
|--------|------|-------------|
| `POST` | `/api/vehicles/{plate}/transfer/request` | Request ownership transfer |
| `PUT` | `/api/vehicles/{plate}/transfer/approve` | Approve a pending transfer |
| `PUT` | `/api/vehicles/{plate}/transfer/reject` | Reject/cancel a pending transfer |

### Database Schema Updates

- **Updated DTOs**: `NewVehicleDTO` includes `pendingOwnerName` and `pendingOwnerId`.

---

## Password Recovery System

A robust password recovery flow utilizing secure unique tokens and controlled expiration times.

### Features

- **Unique UUID Tokens**: Generates randomly generated single-use tokens.
- **15-Minute Expiration**: Tokens expire automatically for enhanced security.
- **Token Cleanup**: Prior recovery tokens are automatically purged upon issuing a new one.
- **Silent Behavior**: Does not disclose if an email exists in the database (security best practice).
- **Professional HTML Email**: Email styling features action buttons and expiration warnings.

### Endpoints

| Method | Path | Description |
|--------|------|-------------|
| `POST` | `/api/auth/forgot-password` | Request password recovery email |
| `POST` | `/api/auth/reset-password` | Complete password reset using token |

### Entities

- **PasswordResetToken**: Contains the UUID token, a `OneToOne` relationship with `User`, and the expiration date.
- **PasswordRecoveryService**: Orchestrates token generation, validation, and usage.

### Recovery URL Structure

```
https://tallercarlog.com/reset-password?token={uuid}
```

---

## Authenticated Password Change

Allows active, authenticated users to modify their credentials with prior identity verification.

### Features

- **Identity Verification**: Requires current password validation before modification.
- **Complexity Guard**: Minimum of 6 characters required for new passwords.
- **Strict Ownership**: A user is only authorized to modify their own credentials.
- **Logging**: Change logs kept for security audits.

### Endpoints

| Method | Path | Description |
|--------|------|-------------|
| `POST` | `/api/users/{dni}/change-password` | Change password while authenticated |

### DTOs

- **UpdatePasswordDTO**: Includes validated fields for `currentPassword` and `newPassword`.

---

## Enhanced HTML Email System

Generic HTML email dispatching module styled with Thymeleaf for professional correspondence.

### Features

- **Asynchronous Execution**: Leverages `@Async` to prevent main thread blocking during SMTP handshakes.
- **HTML Support**: Professional design layouts styled with custom CSS rules.
- **Thymeleaf Layouts**: Centralized and reusable layout structures.
- **Layout Templates**:
  - `password-recovery.html`: Credential recovery layout.
  - `alert-notification.html`: Vehicles alert notifications.
  - Dynamic invoice notifications and event emails.

### Service Layout

- **MailService.sendHtmlEmail()**: Unified method executing HTML parsing and sending.

---

## Vehicle Catalog

Comprehensive European vehicle catalog containing detailed technical specifications:

### Scoped Data

- **+100 Brands**: Volkswagen, BMW, Mercedes, Toyota, Ford, etc.
- **+500 Models**: Structured under respective manufacturers.
- **+3000 Versions**: Listing in-depth engine details.

### Version-Specific Metrics

- Engine code
- Engine type
- Fuel type
- Power output (CV/HP)
- Engine torque (Nm)
- Production timeline (start/end years)

### Synchronizing External Data

Integrated American vehicle catalog parsing capabilities from NHTSA APIs:

- **Endpoint**: `POST /api/catalog/sync`
- **Source**: NHTSA Vehicle Products API.
- **Process**: Fully asynchronous, automatically generating missing brands and models.

---

## Cloudinary Integration

Static assets and images are stored inside Cloudinary under two subdirectories:

| Resource | Folder Path in Cloudinary |
|---|---|
| Vehicle Images | `carlog/vehicles/` |
| Workshop Logos | `carlog/workshops/` |

**Supported Input Formats**:
- `data:image/...;base64,...` — Base64 strings are uploaded directly.
- `https://...` — Existing URL strings are preserved (skips re-uploading).

When deleting a vehicle or replacing a workshop logo, old files are automatically purged from Cloudinary.

---

## Error Handling

All system exceptions are captured centrally by `GlobalExceptionHandler` (`@RestControllerAdvice`).

**Standard Error Response Format**:
```json
{
  "message": "Error description message",
  "status": 404,
  "timestamp": 1713350400000
}
```

### Managed Exceptions Matrix

| HTTP Status | Exception Class | Primary Cause |
|---|---|---|
| `404` | `VehicleNotFoundException` | Specified plate not found in database |
| `404` | `UserNotFoundException` | DNI or email matches no user |
| `404` | `WorkOrderNotFoundException` | Specified work order ID does not exist |
| `404` | `WorkshopNotFoundException` | Specified workshop ID does not exist |
| `404` | `WorkOrderLineNotFoundException` | Invoice detail line ID not found |
| `400` | `WorkshopNotAssignedException` | Mechanic/Manager has no assigned workshop |
| `400` | `VehicleNotInWorkshopException` | Vehicle is not currently checked into workshop |
| `400` | `ClosedWorkOrderException` | Cannot modify a completed work order |
| `400` | `WorkOrderLineMismatchException` | Detail line does not belong to specified order |
| `400` | `MechanicNotInWorkshopException` | Mechanic is not hired at the specified workshop |
| `400` | `NoPendingRequestException` | No pending entry request exists for vehicle |
| `400` | `InvalidSearchTypeException` | Invalid search scope specified |
| `400` | `InvalidRegistrationException` | Registration rule violation (e.g. self-hiring) |
| `400` | `NoPendingInvitationException` | No pending workshop invitation exists |
| `403` | `UnauthorizedActionException` | User lacks security privileges for this action |
| `409` | `VehicleOcuppiedException` | Vehicle is already checked into another workshop |
| `409` | `VehicleAlreadyExistsException` | Vehicle license plate already registered |
| `409` | `UserAlreadyExistsException` | User DNI already registered in system |
| `409` | `UserAlreadyInWorkshopException` | Employee is already hired in a workshop |
| `409` | `WorkshopAlreadyExistsException` | Specified workshop fields conflict with existing record |
| `409` | `UserAlreadyHasWorkshopException` | Manager is already assigned to a workshop |
| `401` | `BadCredentialsException` | Incorrect password or username credentials |
| `429` | `RateLimitExceededException` | Rate limits exceeded on auth endpoints |
| `500` | `Exception` | Generic unhandled server error |

---

## API Documentation (Swagger)

Centralized API description endpoints are hosted at:
- Swagger UI: `http://localhost:8081/swagger-ui.html`
- JSON Docs: `http://localhost:8081/v3/api-docs`

---

## Project Directory Structure

```
backend/
├── src/main/java/com/carlog/backend/
│   ├── auth/                    # Registration, login, logout flows
│   │   ├── AuthenticationController.java
│   │   ├── AuthenticationService.java
│   │   ├── AuthenticationRequest.java
│   │   ├── AuthenticationResponse.java
│   │   └── RegisterRequest.java
│   ├── config/                  # Framework and Security configurations
│   │   ├── SecurityConfig.java
│   │   ├── ApplicationConfig.java
│   │   ├── JwtAuthenticationFilter.java
│   │   ├── CsrfCookieFilter.java
│   │   ├── RateLimitingInterceptor.java
│   │   ├── WebMvcConfig.java
│   │   ├── WebSocketConfig.java
│   │   ├── CloudinaryConfig.java
│   │   └── WebConfig.java
│   ├── controller/              # HTTP Controllers
│   │   ├── VehicleController.java
│   │   ├── WorkOrderController.java
│   │   ├── WorkshopController.java
│   │   ├── UserController.java
│   │   ├── AdminController.java
│   │   ├── CarCatalogController.java
│   │   └── RoleConverter.java
│   ├── dto/                     # Transfer objects / Records
│   ├── error/                   # Exception handlers and custom exceptions
│   ├── listener/                # Application event listeners
│   ├── model/                   # JPA Entity definitions
│   │   ├── User.java
│   │   ├── Vehicle.java
│   │   ├── WorkOrder.java
│   │   ├── WorkOrderLine.java
│   │   ├── Workshop.java
│   │   ├── Role.java
│   │   ├── WorkOrderStatus.java
│   │   ├── CarBrand.java
│   │   ├── CarModel.java
│   │   ├── CarVersion.java
│   │   └── PaymentStatus.java
│   ├── repository/              # Spring Data JPA Repository interfaces
│   ├── security/                # Jwt utilities
│   └── service/                 # Core Business Logic services
│       ├── UserService.java
│       ├── VehicleService.java
│       ├── WorkOrderService.java
│       ├── WorkshopService.java
│       ├── AdminService.java
│       ├── InvoiceService.java
│       ├── MailService.java
│       ├── CarCatalogService.java
│       ├── BrandSyncService.java
│       └── RateLimitingService.java
└── src/main/resources/
    ├── application.properties
    ├── templates/
    │   ├── invoice-template.html
    │   └── emails/
    │       ├── work-order-completed.html
    │       ├── vehicle-admission.html
    │       └── hiring-message.html
    └── db/migration/
        ├── V1__Initial_schema.sql
        ├── V2__European_brands.sql
        ├── V3__European_Models.sql
        ├── V4__Car_Versions.sql
        └── V5__Clean_DB.sql
BBDD_CARLOG/
└── docker-compose.yml
```

---

## Quick Start

```bash
# 1. Start MySQL database instance
cd BBDD_CARLOG && docker-compose up -d

# 2. Create the .env file in backend/
cp .env.example backend/.env
# Edit configurations with your credentials

# 3. Compile and launch the application
cd backend
./mvnw spring-boot:run
```

The application server listens on port **8081** by default.

---

## Configuration

### Environment Variables

The project utilizes `spring-dotenv` to dynamically parse environment variables from a `.env` file during local execution.

| Variable Name | Description | Mandatory |
|---|---|---|
| `DB_HOST` | MySQL database host (default: `127.0.0.1`) | No |
| `DB_USER` | MySQL login username | Yes |
| `DB_PASSWORD` | MySQL login password | Yes |
| `JWT_SECRET_KEY` | Base64-encoded secret key (minimum of 256 bits) | Yes |
| `URL_CORS` | Authorized origin URL for CORS and WebSocket connections | Yes |
| `isSecure` | `true` in production environments (marks JWT cookie as Secure) | Yes |
| `API_NAME` | Cloudinary cloud service name | Yes |
| `CLOUDINARY_API_KEY` | Cloudinary access API key | Yes |
| `API_SECRET` | Cloudinary access API secret key | Yes |
| `DB_ROOT_PASSWORD` | MySQL database root password (only required in Docker containers) | Yes (Docker) |
| `MAIL_HOST` | SMTP server host for sending emails | Yes (for emails) |
| `MAIL_PORT` | SMTP port number | Yes (for emails) |
| `MAIL_USERNAME` | SMTP authentication username | Yes (for emails) |
| `MAIL_PASSWORD` | SMTP authentication password | Yes (for emails) |

**Sample Local `.env` File**:
```env
DB_HOST=127.0.0.1
DB_USER=carlog_user
DB_PASSWORD=carlog_pass
DB_ROOT_PASSWORD=root_pass
JWT_SECRET_KEY=<base64_secret_key_at_least_32_bytes>
URL_CORS=http://localhost:4200
isSecure=false
API_NAME=your_cloudinary_cloud_name
CLOUDINARY_API_KEY=your_cloudinary_api_key
API_SECRET=your_cloudinary_api_secret
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password
```

### Docker-managed Database

The `BBDD_CARLOG/` subdirectory hosts the configuration parameters for initializing local database resources:

```bash
cd BBDD_CARLOG
docker-compose up -d
```

This command generates:
- A `carlog-mysql` container instance running MySQL 8.0.
- A database catalog named `carlog_db`.
- Database ports mapped directly to local loops `127.0.0.1:3306` (denying external routing).
- Persistent volume hooks for data safety.

---

## License

This project is licensed under the terms of the **MIT License**. Check the [LICENSE](LICENSE) file for more information.

---

*Developed by [JaviRSDEV](https://github.com/JaviRSDEV)*

## Documentation
- **[API Reference](./README.md)** - Complete documentation of REST endpoints
- **[Documentación Técnica (ESP)](./docs/CARLOG_DOCUMENTATION_ESP.pdf)** - Technical documentation in Spanish
- **[Technical Documentation (ENG)](./docs/CARLOG_DOCUMENTATION_ENG.pdf)** - Technical documentation in English

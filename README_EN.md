# CarLog — Comprehensive Management Platform for Mechanical Workshops

![Spring Boot](https://img.shields.io/badge/Backend-Spring_Boot_4.0.1-6db33f?style=for-the-badge&logo=spring)
![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk)
![MySQL](https://img.shields.io/badge/Database-MySQL_8.0-4479a1?style=for-the-badge&logo=mysql)
![Docker](https://img.shields.io/badge/Deploy-Docker-2496ed?style=for-the-badge&logo=docker)
![JWT](https://img.shields.io/badge/Auth-JWT-black?style=for-the-badge&logo=jsonwebtokens)
![WebSocket](https://img.shields.io/badge/RealTime-WebSocket_STOMP-010101?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

---

## Description

**CarLog** is a **Full Stack** platform designed for the comprehensive management of mechanical workshops. It provides a robust, scalable RESTful API that covers the entire lifecycle of a vehicle: from its check-in at the workshop and the creation of work orders to final billing, payment status updates, and vehicle check-out.

The system is designed to support multiple user roles, ensuring a tailored experience for all participants in the automotive repair ecosystem.

---

## Project Structure

- **Backend (Spring Boot)**: This repository contains the REST API, WebSockets, security, business logic, and validation layer.
- **Frontend (Angular)**: The Single Page Application (SPA) repository utilizing Signals state management: [https://github.com/JaviRSDEV/FrontCarLog](https://github.com/JaviRSDEV/FrontCarLog)

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
- [Billing System (Invoices)](#billing-system-invoices)
- [Email Notifications](#email-notifications)
- [Automated Alert System](#automated-alert-system)
- [Vehicle Transfer Handshake](#vehicle-transfer-handshake)
- [Password Recovery System](#password-recovery-system)
- [Vehicle Catalog](#vehicle-catalog)
- [Cloudinary Integration](#cloudinary-integration)
- [Error Handling](#error-handling)
- [Getting Started](#getting-started)
- [License](#license)

---

## Main Features

- **Comprehensive Vehicle Management**: Registration, updates, service history tracking, and ownership transfers.
- **Work Orders**: Full lifecycle management (PENDING → IN_PROGRESS → COMPLETED) with automated calculations of totals, VAT, and discounts.
- **Check-In/Check-Out Protocol**: A secure double-confirmation handshake between the vehicle owner and the workshop.
- **Employee System**: Full recruitment workflow (Invite → Accept/Reject → Terminate).
- **Real-Time Notifications** via STOMP WebSockets for critical workshop events.
- **Stateless Authentication** using JWT and Role-Based Access Control (RBAC).
- **Image Management**: Image uploads for vehicles and workshop logos via Cloudinary (both Base64 and Multipart formats).
- **Centralized Error Handling** returning standardized JSON error objects.
- **Containerized Infrastructure** via Docker Compose (including MySQL and phpMyAdmin).
- **European Car Catalog**: Integrated catalog featuring over 100 makes, 500 models, and 3,000 versions with complete technical specifications (engine codes, horsepower, torque, production years).
- **Administration Panel**: Dashboard displaying global platform statistics for the `ADMIN` role.
- **PDF Invoice Generation**: Billing system utilizing Thymeleaf HTML templates converted directly to PDF.
- **Email Notification System**: Asynchronous email delivery for critical events (completed work orders, vehicle check-ins, employee invitations).
- **External API Sync**: Integration with the NHTSA database for vehicle catalog synchronization.

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Spring Boot | 4.0.1 | Main Framework |
| Java | 21 | Development Language |
| Spring Security | (boot managed) | Authentication and Authorization |
| Spring Data JPA | (boot managed) | Data Access Layer |
| MySQL | 8.0 | Relational Database |
| Flyway | 10.10.0 | Schema Migration Tool |
| JJWT | 0.12.6 | JWT Token Generation and Verification |
| Bucket4j | 8.10.1 | Rate Limiting (API request control) |
| Cloudinary | 2.0.0 | Media Storage |
| Spring WebSocket | 4.0.1 | Real-Time Messaging (STOMP) |
| SpringDoc OpenAPI | 3.0.0 | Swagger API Documentation |
| Lombok | (boot managed) | Boilerplate Reduction |
| spring-dotenv | 4.0.0 | Environment Variables via `.env` file |
| Apache Tika | 2.9.1 | File MIME Type Detection |
| OpenHTMLToPDF | 1.0.10 | PDF Document Generation |
| Thymeleaf | (boot managed) | HTML Templating for Invoices & Emails |
| Spring Mail | (boot managed) | SMTP Mail Sending |
| Spring Async | (boot managed) | Asynchronous Task Processing |

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
│         MySQL 8.0 (Docker)          │  ← Database Persistence
└─────────────────────────────────────┘
```

The database schema is entirely version-controlled using **Flyway** migration files (`V1__Initial_schema.sql` through `V7`). JPA schema auto-generation is set to `validate` - Flyway is the single source of truth for the database layout.

---

## System Roles

The `Role` enum defines five distinct roles:

| Role | Purpose |
|---|---|
| `ADMIN` | Global administrator. Full access to system metrics, users, workshops, and database sync options. |
| `MANAGER` | Workshop Owner. Can register/delete workshops, recruit/fire workers, assign work orders, and manage files. |
| `CO_MANAGER` | Assistant Manager. Possesses the same authority as the Manager except for creating or deleting workshops. |
| `MECHANIC` | Mechanic. Can accept work orders, add lines, write repair notes, and view workshop vehicles. |
| `CLIENT` | Client. Can register vehicles, request entries, and inspect historical invoices. |

**Role Promotion Rule**: At registration, all users start with the `CLIENT` role. A user can only be promoted to `MECHANIC` or `CO_MANAGER` via a formal workshop invitation sent by a `MANAGER`/`CO_MANAGER` that they must accept. If a `CLIENT` registers a new workshop, they are automatically upgraded to `MANAGER`.

---

## Security and Authentication

### JWT

- Stored in a secure `HttpOnly` cookie called `auth_token`.
- Extracted and validated on every request by `JwtAuthenticationFilter`.
- If "Remember Me" is disabled: session cookie (`maxAge = -1`).
- If "Remember Me" is enabled: persistent cookie for **7 days**.
- Cookies use `SameSite=Lax` and `Secure` depending on the `isSecure` environment variable.
- Token expiration: **24 hours** by default.

### CSRF

- CSRF protection enabled using `CookieCsrfTokenRepository`.
- The frontend client sends the CSRF token in the `X-XSRF-TOKEN` header.
- Permitted exceptions: `/api/auth/**` and `/ws-carlog/**`.

### CORS

- Allowed origin: Configurable via the `URL_CORS` environment variable.
- Allowed methods: `GET`, `POST`, `PUT`, `PATCH`, `DELETE`, `OPTIONS`.
- Allowed headers: `Authorization`, `Content-Type`, `X-XSRF-TOKEN`.
- `allowCredentials` set to `true` to allow HttpOnly cookies.

### Rate Limiting

Implemented using **Bucket4j** inside `RateLimitingService`:
- Rate limit: **5 requests per minute** per IP address.
- Applies strictly to: `POST /api/auth/authenticate` (Login) and `POST /api/auth/register`.
- Returns an `HTTP 429 Too Many Requests` status if the limit is exceeded.

---

## Billing System (Invoices)

The platform generates complete invoice PDFs using `InvoiceService`:
- **Template engine**: Thymeleaf to design CSS-styled HTML templates.
- **PDF Renderer**: OpenHTMLToPDF utilizing Apache PDFBox.
- **Automated Calculations**: Base amount, tax (VAT), discount rates, and final amount.
- **Endpoint**: `GET /api/workorders/{id}/invoice` returns the PDF binary stream.

---

## Email Notifications

The application dispatches automated HTML emails asynchronously (`@Async`) using Thymeleaf templates and Spring's JavaMailSender:
- **Completed Work Order**: Sent to the client when their repair is completed.
- **Vehicle Check-In Request**: Sent to the owner when a workshop requests entry.
- **Job Opportunity**: Sent to a user when invited to join a workshop.

---

## Automated Alert System

Features custom and automatic user notifications sent to their email:
- Users can create custom reminders (e.g., ITV, insurance) by setting a title, description, and deadline.
- A background cron task (`@Scheduled(cron = "0 0 8 * * ?")`) runs daily at 8:00 AM to check upcoming deadlines.
- Dispatches reminders **1 week before** and **on the day of** the deadline.
- Upon completion of a work order, the system automatically schedules a "Mantenimiento Anual" (Annual Maintenance) alert for 1 year in the future.

---

## Vehicle Transfer Handshake

A secure vehicle transfer protocol:
1. **Request**: The owner requests to transfer the vehicle to another user by entering their DNI.
2. **Approval**: The target recipient accepts the transfer to finalize the swap.
3. **Rejection**: Either party can cancel the pending transfer request.
4. **Real-time Sync**: WebSockets notify the active parties of status updates instantly.

---

## Password Recovery System

A secure password recovery system:
- Generates a secure, temporary UUID token (`PasswordResetToken`).
- Tokens expire automatically after **15 minutes**.
- Older tokens for the user are immediately invalidated upon creating a new request.
- Anti-enumeration: returns a generic success response even if the email does not exist.

---

## License

This project is licensed under the MIT License.

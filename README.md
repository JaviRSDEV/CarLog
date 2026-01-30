# CarLog
> **Plataforma Full Stack para la gestión integral de talleres mecánicos.** > Arquitectura RESTful robusta y escalable.

![Angular](https://img.shields.io/badge/Frontend-Angular-dd0031?style=flat&logo=angular)
![Spring Boot](https://img.shields.io/badge/Backend-Spring_Boot-6db33f?style=flat&logo=spring)
![MySQL](https://img.shields.io/badge/DB-MySQL-4479a1?style=flat&logo=mysql)
![Docker](https://img.shields.io/badge/Deploy-Docker-2496ed?style=flat&logo=docker)
## CarLog API - Guía de Referencia y Uso

Esta documentación describe los endpoints, formatos de datos y flujos de seguridad de la API REST de CarLog (Sistema de Gestión de Talleres).

## Configuración General

| Configuración | Valor |
| :--- | :--- |
| **URL Base (Local)** | `http://localhost:8081/api` |
| **Formato** | JSON |
| **Seguridad** | JWT (JSON Web Token) |

**Cabeceras Requeridas:**
Todas las peticiones (excepto Auth) requieren:
```http
Content-Type: application/json
Authorization: Bearer <tu_token_aqui>
```
---

## Flujo de Trabajo Típico (Workflow)

Registrarse en **/auth/register** {POST} (Obtienes Token).

Login en **/auth/authenticate** {POST} (Si necesitas renovar el Token).

Registrar Vehículo en **/vehicles** {POST} (Opcional si ya existe).

Entrada a Taller en **/vehicles/{plate}/entry** {POST} (El mecánico registra la entrada del coche al taller).

Crear Orden en **/workorders** {POST} (Creas la ficha de reparación).

Añadir Líneas en **/workorders/{id}/lines** {POST} (Añades piezas y mano de obra).

Cerrar Orden en **/workorders/{id}** {POST} (Cambias estado a COMPLETED y se cierra la ficha de reparación).

## 1. Autenticación (Auth)

Accesible públicamente.

Registrar Usuario

Crea una nueva cuenta (Mecánico, Manager, Cliente, DIY).

**Método**: POST

**URL**: /auth/register

**Body**:

```json
{
  "dni": "12345678Z",
  "name": "Pepe",
  "lastname": "Mecánico",
  "email": "pepe@carlog.com",
  "password": "securePass123",
  "phone": "600123456",
  "role": "MECHANIC" 
}
```

(Roles disponibles: MANAGER, MECHANIC, CLIENT, DIY)
---
### Iniciar Sesión

Obtiene el token de acceso.

**Método**: POST

**URL**: /auth/authenticate

**Body**:

```json
{
  "email": "pepe@carlog.com",
  "password": "securePass123"
}
```

**Respuesta Exitosa:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOi..."
}
```


## 2. Gestión de Órdenes (WorkOrders)

Requiere Auth Token.

Crear Nueva Orden

Abre una orden de trabajo para un vehículo específico. El sistema asigna automáticamente el mecánico logueado y su taller.

**Método** POST

**URL**: /workorders

**Headers Específicos**:

**Vehicle-plate**: 1234-BBB (Matrícula del coche a reparar)

**Body**:

```json
{
  "description": "El cliente reporta ruido en los frenos y solicita cambio de aceite."
}
```
---
Listar Órdenes

**Método**: GET

**URL**: /workorders

**Opcional**: /workorders?mechanicDni=12345678Z (Filtrar por mecánico)
---
Cerrar Orden / Editar Notas

Actualiza el estado o las notas. Si se pasa a COMPLETED, se genera fecha de cierre y se bloquea la edición de líneas.

**Método**: PUT

**URL**: /workorders/{id}

**Body**:

```json
{
  "status": "COMPLETED",
  "mechanicNotes": "Reparación finalizada con éxito. Vehículo probado."
}
```

(Estados: PENDING, IN_PROGRESS, COMPLETED, CANCELLED)

---
### 3. Facturación (Líneas de Orden)

El sistema calcula automáticamente: (Cantidad * Precio) + IVA - Descuento.

Añadir Línea (Pieza o Mano de Obra)

**Método**: POST

**URL**: /workorders/{orderId}/lines

**Body**:

```json
{
  "concept": "Juego de Pastillas de Freno",
  "quantity": 1.0,
  "pricePerUnit": 100.0,
  "IVA": 21.0,       // Opcional (Default: 0.0)
  "discount": 10.0   // Opcional (Default: 0.0)
}
```

Respuesta: Devuelve la orden completa con el totalAmount recalculado.

---
Borrar Línea

**Método**: DELETE

**URL**: /workorders/{orderId}/lines/{lineId}

### 4. Vehículos (Vehicles)

Registrar Vehículo

**Método**: POST

**URL**: /vehicles

**Body**:

```json
{
  "plate": "9999-ZZZ",
  "brand": "Toyota",
  "model": "Corolla",
  "kilometers": 15000,
  "engine": "Híbrido",
  "ownerId": "87654321X" // Opcional. Si se omite, se asigna al usuario logueado.
}
```
---
Registrar Entrada en Taller (Check-in)

Mueve el coche al taller especificado. Falla si el coche ya está en otro taller.

**Método**: POST

**URL**: /vehicles/{plate}/entry?workshopId=1

---
Registrar Salida (Check-out)

Libera el coche del taller.

**Método**: POST

**URL**: /vehicles/{plate}/exit?workshopId=1

---
Transferir Propiedad (Venta)

Cambia el dueño del vehículo. Requiere validación del dueño actual.

**Método**: POST

**URL**: /vehicles/{plate}/transfer

Params: ?currentOwnerId=1234A&newOwnerId=5678B

Tabla de Errores Comunes

| Código | Significado  | Causa probable                                | Solución                                        |
|--------|--------------|-----------------------------------------------|-------------------------------------------------|
| 403    | Forbidden    | Token inválido / No enviado / Login fallido   | Revisa la cabecera Authorization o credenciales |
| 404    | Not Found    | ID o Matrícula no existen                     | Verifica que el recurso existe en BD.           |
| 409    | Conflict     | Vehículo en otro taller / Matrícula duplicada | Realiza Check-out antes de Check-in             |
| 500    | Server Error | Error de lógica de negocio                    |                                                 |

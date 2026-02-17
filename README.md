Employee Management System (EMS)

The Employee Management System (EMS) is a Spring Boot–based microservice responsible for managing employee data within a distributed microservices architecture.

EMS is secured using a dedicated Authentication Service (Auth Service) implementing:

🔐 JWT-based Authentication

🛡 Role-Based Access Control (RBAC)

🚫 Protected API Endpoints

This service follows clean architecture, SOLID principles, and production-grade backend practices.

----
🏗 System Architecture

EMS is part of a multi-service ecosystem:

```

                        ┌─────────────────────┐
                        │     Auth Service    │
                        │  JWT + RBAC + Login │
                        └──────────┬──────────┘
                                   │
                                   ▼
         ┌─────────────────────────────────────────┐
         │        Employee Management Service      │
         └──────────┬──────────────────────────────┘
                    │
       ┌────────────┴────────────┐
       ▼                         ▼
Departments Service	Designation Service
```

----
🔗 Related Microservices

The Employee Management System (EMS) is part of a distributed microservices architecture.
Below are the related services:

🔗 [Auth Service Repository](https://github.com/Asiya338/Auth_Service-EMS)

🔗 [Department Service Repository](https://github.com/Asiya338/Departments_Service-EMS)

🔗 [Designation Service Repository](https://github.com/Asiya338/Designation_Service-EMS)


----
🔐 Authentication & Authorization

🧩 Auth Service

The Auth Service handles:

User registration

Login

JWT token generation

Role assignment


-----
🔑 JWT Integration in EMS

EMS:

>Validates JWT token on every request

>Extracts user role from token

>Applies role-based restrictions

Example Role Permissions
```
Role	Access Level
ADMIN	Full CRUD access : Create & Update employees
USER	 : Read-only
```


-----
🛠 Tech Stack

Java 17+

Spring Boot

Spring Security

JWT

Spring Data JPA

MySQL

WebClient

ModelMapper (STRICT mode)

Redis (caching)

Swagger / OpenAPI

MDC Logging (traceId)


-----
📂 Project Structure

```
employee-management-system/
│
├── controller/
├── service/
│   └── impl/
├── repo/
├── entity/
├── dto/
├── security/          ← JWT filters 
├── http/              ← WebClient clients
├── exception/
├── enums/
├── constants/
├── config/
└── resources/
```

🗄 Database Schema

📄 employees table


🚀 Core Features
✅ Employee CRUD Operations

Create

Update

Delete

Get by ID

Get all

✅ Pagination & Sorting
GET /api/v1/employees?page=0&size=10&sortBy=name&order=asc

✅ Filtering

By department

By designation

By role

By status

✅ Search API

Priority-based search:

GET /api/v1/employees/search?name=asiya
GET /api/v1/employees/search?email=test@example.com
GET /api/v1/employees/search?employeeCode=EMP0001


🔁 Inter-Service Communication

Before employee creation/update:

Validate department via Department Service

Validate designation via Designation Service

Implemented using Spring WebClient.

No cross-database foreign keys used — microservice best practice.


----
❗ Standard Error Response

All errors follow a consistent structure:

```
{
  "errorCode": "10001",
  "errorMessage": "Invalid department ID",
  "path": "/api/v1/employees",
  "traceId": "6931767f5254c3db70483548ae53419f",
  "timeStamp": "2025-12-04T17:24:39.9370513",
  "httpMethod": "POST"
}
```

----
Design Decisions

Loose coupling between services

JWT-secured endpoints

Role-based permission enforcement

Strict DTO ↔ Entity mapping

Centralized error codes

Global exception handler

MDC-based traceId logging

Idempotent PUT operations

-----
🧪 Sample Flow (Secure API Call)
Step 1: Login via Auth Service
POST /auth/login


Receive JWT token.

Step 2: Call EMS API
Authorization: Bearer <JWT_TOKEN>

Step 3: Role Validation Applied

If role unauthorized → 403 Forbidden.


----
Running EMS

1️⃣ Create MySQL database:

employee_management


2️⃣ Update properties:

spring.datasource.url=jdbc:mysql://localhost:3306/employee_management
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD


3️⃣ Run:

mvn spring-boot:run

```
Service runs on:

http://localhost:8087
```
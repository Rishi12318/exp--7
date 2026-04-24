# Experiment 7 - Spring Boot Role-Based Authorization (RBAC)

This project implements authentication and role-based authorization in Spring Boot using Spring Security.

## Features Implemented

- User authentication using Spring Security (`/api/auth/login` + HTTP Basic for secured endpoint testing)
- Roles: `ROLE_USER`, `ROLE_ADMIN`
- Protected APIs based on roles
- Correct HTTP status behavior:
	- `401 Unauthorized` when authentication is missing/invalid
	- `403 Forbidden` when authenticated user lacks required role
- H2 database with startup data seeding

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- H2 Database
- Maven

## Project Structure

```
src/
├── main/
│   ├── java/com/example/experiment7/
│   │   ├── config/
│   │   │   ├── DataInitializer.java
│   │   │   ├── RestAccessDeniedHandler.java
│   │   │   ├── RestAuthenticationEntryPoint.java
│   │   │   └── SecurityConfig.java
│   │   ├── controller/
│   │   │   ├── AdminController.java
│   │   │   ├── AuthController.java
│   │   │   ├── PublicController.java
│   │   │   └── UserController.java
│   │   ├── dto/
│   │   │   ├── LoginRequest.java
│   │   │   └── LoginResponse.java
│   │   ├── entity/
│   │   │   ├── Role.java
│   │   │   ├── RoleName.java
│   │   │   └── User.java
│   │   ├── repository/
│   │   │   ├── RoleRepository.java
│   │   │   └── UserRepository.java
│   │   ├── service/
│   │   │   ├── AuthService.java
│   │   │   └── CustomUserDetailsService.java
│   │   └── Experiment7Application.java
│   └── resources/
│       └── application.properties
└── test/
		└── java/com/example/experiment7/Experiment7ApplicationTests.java

screenshots/
└── README.md
```

## Demo Credentials

Seeded at startup by `DataInitializer`:

- `user1` / `user123` -> `ROLE_USER`
- `admin1` / `admin123` -> `ROLE_ADMIN`

## API Endpoints

### 1. Public Endpoint

- `GET /api/public/hello`
- Access: Public

Example response:

```json
{
	"message": "This is a public endpoint"
}
```

### 2. Login Endpoint

- `POST /api/auth/login`
- Access: Public

Request body:

```json
{
	"username": "user1",
	"password": "user123"
}
```

Example response:

```json
{
	"message": "Login successful",
	"username": "user1",
	"roles": [
		"ROLE_USER"
	]
}
```

### 3. User Endpoint

- `GET /api/user/profile`
- Access: `ROLE_USER`, `ROLE_ADMIN`

Response:

```json
{
	"message": "Welcome, authenticated user"
}
```

### 4. Admin Endpoint

- `GET /api/admin/dashboard`
- Access: `ROLE_ADMIN` only

Response:

```json
{
	"message": "Welcome, admin"
}
```

## Security Rules

Configured in `SecurityConfig`:

- `/api/public/**` -> permit all
- `/api/auth/login` -> permit all
- `/api/user/**` -> `hasAnyRole("USER", "ADMIN")`
- `/api/admin/**` -> `hasRole("ADMIN")`
- all other endpoints -> authenticated

Custom handlers return JSON for:

- `401` via `RestAuthenticationEntryPoint`
- `403` via `RestAccessDeniedHandler`

## How to Run

1. Open terminal in project root.
2. Run:

```bash
mvn clean spring-boot:run
```

3. Application starts at: `http://localhost:8080`
4. Optional H2 console: `http://localhost:8080/h2-console`

## Postman Testing Guide

### Case A: Public Endpoint (No Auth)

- `GET http://localhost:8080/api/public/hello`
- Expected: `200 OK`

### Case B: Login with Valid Credentials

- `POST http://localhost:8080/api/auth/login`
- Body (JSON):

```json
{
	"username": "user1",
	"password": "user123"
}
```

- Expected: `200 OK`

### Case C: USER Accessing User Endpoint

- `GET http://localhost:8080/api/user/profile`
- Authorization -> Basic Auth:
	- Username: `user1`
	- Password: `user123`
- Expected: `200 OK`

### Case D: USER Accessing Admin Endpoint

- `GET http://localhost:8080/api/admin/dashboard`
- Authorization -> Basic Auth (`user1` / `user123`)
- Expected: `403 Forbidden`

### Case E: ADMIN Accessing Admin Endpoint

- `GET http://localhost:8080/api/admin/dashboard`
- Authorization -> Basic Auth:
	- Username: `admin1`
	- Password: `admin123`
- Expected: `200 OK`

### Case F: No Authentication for Secured Endpoint

- `GET http://localhost:8080/api/user/profile`
- No Authorization header
- Expected: `401 Unauthorized`

## Required Screenshots

Add at least these screenshots in `screenshots/`:

1. Login request with valid credentials
2. Successful response after login or secured endpoint access
3. USER role accessing `/api/user/profile` successfully
4. USER denied on admin endpoint (`403`) or ADMIN accessing admin endpoint successfully

Recommended additional screenshots:

1. Invalid login attempt
2. Request without token/auth (`401`)
3. Access denied (`403`) payload

## Git Commands

```bash
git add .
git commit -m "Implement Spring Boot RBAC with USER/ADMIN roles and secured endpoints"
git push origin main
```


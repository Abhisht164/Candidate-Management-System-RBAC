# Candidate Management System – JWT Authentication & Role-Based Access Control

A Spring Boot REST API demonstrating JWT Authentication, Role-Based Access Control (RBAC), and Spring Security using a Candidate Management System.

The application separates responsibilities across Admin, Recruiter, and Candidate roles. Each role has access only to the APIs required for its responsibilities, with authorization enforced using Spring Security.

---

## Tech Stack

* Java 21
* Spring Boot
* Spring Security
* JWT Authentication
* Spring Data JPA
* Hibernate
* H2 Database
* Maven
* Lombok
* OpenAPI / Swagger

---

# What This Project Demonstrates

This project focuses on implementing secure REST APIs using Spring Security.

Implemented features include:

* JWT-based authentication
* Stateless authentication using Spring Security
* Role-Based Access Control (RBAC)
* User and Role management
* Candidate management
* Custom authentication filter
* Custom `UserDetailsService`
* Global exception handling
* API documentation with Swagger

---

# Security Flow

Every secured request follows the flow below:

```
Client
   │
   ▼
Login (/api/auth/login)
   │
   ▼
JWT Token
   │
   ▼
Client sends Authorization Header
   │
   ▼
JwtAuthenticationFilter
   │
   ▼
Spring Security
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
Response
```

---

# Authentication

## Login

```
POST /api/auth/login
```

Authenticates a user using username and password.

On successful authentication, a JWT token is generated and returned to the client.

The token is then included in the `Authorization` header for every protected request.

Example:

```
Authorization: Bearer <jwt-token>
```

---

# User Roles

The application supports three roles.

### Admin

Responsible for user management.

Capabilities:

* Create users
* Update user details
* View user information

---

### Recruiter

Responsible for candidate management.

Capabilities:

* Add candidates
* Update candidate details
* View candidate information

---

### Candidate

Responsible for managing their own profile.

Capabilities:

* View profile
* Update profile

---

# API Endpoints

## Authentication

### Login

```
POST /api/auth/login
```

Authenticates a user and returns a JWT access token.

---

## Admin APIs

Base URL

```
/api/admin/users
```

### Create User

```
POST /api/admin/users
```

Creates a new system user with the required role.

---

### Update User

```
PUT /api/admin/users/{id}
```

Updates user details.

---

### Get User

```
GET /api/admin/users/{id}
```

Returns a specific user's information.

---

### Get All Users

```
GET /api/admin/users
```

Returns all registered users.

---

## Recruiter APIs

Base URL

```
/api/recruiter/candidates
```

### Add Candidate

```
POST /api/recruiter/candidates
```

Creates a new candidate profile.

---

### Update Candidate

```
PUT /api/recruiter/candidates/{id}
```

Updates candidate information.

---

### Get Candidate

```
GET /api/recruiter/candidates/{id}
```

Returns candidate details.

---

### Get All Candidates

```
GET /api/recruiter/candidates
```

Returns all candidate records.

---

## Candidate APIs

Base URL

```
/api/candidate/profile
```

### View Profile

```
GET /api/candidate/profile
```

Returns the logged-in candidate's profile.

---

### Update Profile

```
PUT /api/candidate/profile
```

Allows candidates to update their own profile.

---

# Security Components

## Security Configuration

Configures:

* Stateless session management
* JWT authentication
* Public and protected endpoints
* Role-based authorization rules
* Password encoding

---

## JwtAuthenticationFilter

Processes every incoming request.

Responsibilities:

* Reads the JWT token from the request
* Validates the token
* Extracts user information
* Populates the Spring Security context

Only authenticated requests continue to the controller.

---

## CustomUserDetailsService

Loads user details from the database.

Spring Security uses this service during authentication to verify user credentials and retrieve assigned roles.

---

## JwtUtil

Responsible for:

* Generating JWT tokens
* Validating tokens
* Extracting username and claims
* Checking token expiration

---

# Project Structure

```
src/main/java
│
├── controller
│   ├── AuthController
│   ├── AdminUserController
│   ├── RecruiterCandidateController
│   └── CandidateProfileController
│
├── security
│   ├── JwtAuthenticationFilter
│   ├── JwtUtil
│   ├── SecurityConfig
│   └── CustomUserDetailsService
│
├── service
├── repository
├── entity
├── dto
├── mapper
├── config
├── exception
└── validation
```

---

# Running the Application

Clone the repository

```
git clone <repository-url>
```

Run the application

```
./mvnw spring-boot:run
```

The application starts on:

```
http://localhost:8080
```

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

---

# What I Learned

This project helped me gain hands-on experience with:

* Configuring Spring Security for stateless applications
* Implementing JWT-based authentication
* Designing Role-Based Access Control (RBAC)
* Creating custom authentication filters
* Integrating Spring Security with JPA and Hibernate
* Managing users, roles, and permissions
* Building secure REST APIs
* Documenting APIs using OpenAPI / Swagger
* Structuring a layered Spring Boot application using Controller, Service, Repository, and DTO patterns


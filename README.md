# 📑 Kanban Task Manager API

A robust RESTful API for managing Kanban-style tasks. Built with **Spring Boot 21**, secured with **JWT**, and deployed on **Render**.  
It features a complete **Role-Based Access Control (RBAC)** system and live API documentation.

---

# 🚀 Live Documentation (Swagger)

You can interact with the API and test all endpoints directly from your browser:

👉 [Explore API on Swagger UI](https://backend-de-la-api-del-gestor-de-tareas.onrender.com/swagger-ui/index.html)

> ⚠️ Since this project is hosted on Render's free tier, the first request may take between **30–50 seconds** while the server wakes up.

---
Companion Frontend Client Repository:
> 👉 [Frontend Client (React + TypeScript)](https://github.com/Thabatadm/kanban-task-manager-frontend)

---

# 🛠️ Tech Stack

- Java 21
- Spring Boot 3.3.4
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL (Supabase)
- Lombok
- Maven
- Docker

---

# ⚖️ RBAC System

The application implements a strict Role-Based Access Control system to protect project integrity and collaboration workflows.

| Action | Owner | Developer |
|---|---|---|
| Create project | ✅ | ❌ |
| Manage members | ✅ | ❌ |
| Create cards | ✅ | ❌ |
| Edit / Move cards | ✅ | ✅ |
| Delete cards | ✅ | ❌ |
| Delete project | ✅ | ❌ |
| Leave project | ❌ (Protected) | ✅ |

---

# 🛡️ Security Architecture

## 🔹 URL-Level Security

Implemented using Spring Security:

- Automatic JWT validation
- Public endpoints under `/auth/**`
- Protected private routes

---

## 🔹 Service-Level Security

Additional business logic validations:

- Ownership verification
- Critical action restrictions
- Role validation before sensitive operations

---

# 🧪 How to Test the API

## 1️⃣ Register Users

### Create Owner Account

```http
POST /auth/register
```

```json
{
  "name": "string",
  "lastName": "string",
  "email": "string@example.com",
  "password": "string"
}
```

---

### Create Developer Account

```http
POST /auth/register
```

```json
{
  "name": "string",
  "lastName": "string",
  "email": "string@example.com",
  "password": "string"
}
```

> Use different emails to simulate team collaboration.

---

# 2️⃣ Authentication

## Login

```http
POST /auth/login
```

```json
{
  "email": "string@example.com",
  "password": "string"
}
```

Copy the JWT token from the response.

Then:

1. Click the **Authorize** 🔒 button in Swagger
2. Enter:

```txt
Bearer YOUR_TOKEN
```

---

# 3️⃣ Project Workflow

## Create Project

> The user who creates the project automatically becomes the **Owner**.

```http
POST /api/projects/create
```

```json
{
  "name": "Task Management System",
  "description": "Spring Boot backend project"
}
```

---

## Add Member to Project

```http
POST /api/projects/{projectId}/members
```

```json
{
  "userId": number,
  "role": "DEVELOPER"
}
```

---

## Create Card

```http
POST /api/projects/{projectId}/cards/create
```

```json
{
  "title": "New Task",
  "description": "Task description",
  "status": "TO_DO",
  "priority": "MEDIUM"
}
```

---

# ✅ RBAC Verification

Switch authorization to the **Developer** token and try to:

- Delete a project
- Delete cards
- Manage project members

### Expected Response

```http
403 Forbidden
```

---

# 🖥️ Run Locally

## Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/YOUR_REPOSITORY.git](https://github.com/Thabatadm/Backend-de-la-API-del-gestor-de-tareas-Kanban
cd YOUR_REPOSITORY
```

---

## Configure the database

Create an `application.properties` file inside:

```txt
src/main/resources/
```

Add your Supabase PostgreSQL credentials:

```properties
spring.datasource.url=YOUR_SUPABASE_URL
spring.datasource.username=YOUR_SUPABASE_USERNAME
spring.datasource.password=YOUR_SUPABASE_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=YOUR_SECRET_KEY
```

---

## Build Docker image

```bash
docker build -t kanban-api .
```

---

## Run Docker container

```bash
docker run -p 8080:8080 kanban-api
```

The API will be available at:

```txt
http://localhost:8080
```

---

## Open Swagger UI

```txt
http://localhost:8080/swagger-ui/index.html
```

---

# 📂 Project Structure

```bash
src/main/java/com/example/demo/
│
├── controller/     # REST endpoints
├── service/        # Business logic and RBAC
├── repository/     # JPA data access layer
├── model/          # Entities (User, Project, Card)
├── dto/            # Data Transfer Objects
├── security/       # JWT and security configuration
├── exception/      # Global exception handling
│
└── DemoApplication.java
```

---

# 📄 API Testing File

The repository includes a ready-to-use HTTP testing file for the VS Code REST Client extension.

Example:

```txt
api-test.http
```

Included test flows:

- User registration
- Authentication
- Project creation
- Member management
- Card CRUD operations
- RBAC validation

---

# 🔌 Recommended VS Code Extension

Install:

- REST Client (by Huachao Mao)

This allows you to execute HTTP requests directly from VS Code.

---

# © Copyright

Created by **Thabata Denise Monteiro**

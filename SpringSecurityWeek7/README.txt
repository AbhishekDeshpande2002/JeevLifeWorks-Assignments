Spring Security Employee Management System
==========================================

Project Overview:
-----------------
This is a backend REST API built with Spring Boot and Spring Security using JWT for authentication.
It manages employee data with role-based access control. Users can register and login to receive
JWT tokens to authenticate their requests. Employees can be created, read, updated, and deleted
based on user roles.

Features:
---------
- User registration and login with JWT authentication
- Role-based access control (ROLE_USER and ROLE_ADMIN)
- CRUD operations on Employee data
- Global exception handling for common errors
- Password hashing with BCrypt
- Logging of important events

Technologies Used:
------------------
- Java 21
- Spring Boot 3.5.3
- Spring Security with JWT
- Hibernate / JPA
- MySQL
- Lombok
- Maven
- SLF4J / Logback for logging

Getting Started:
----------------
1. Clone the repository
2. Create a MySQL database (e.g., spring_security_db)
3. Configure database connection and JWT secret in application.properties:
   spring.datasource.url=jdbc:mysql://localhost:3306/spring_security_db?allowPublicKeyRetrieval=true&useSSL=false
   spring.datasource.username=root
   spring.datasource.password=your_password

   app.jwt.secret=your_jwt_secret_key
   app.jwt.expiration=3600000  (1 hour in milliseconds)

4. Build and run the application:
   mvn clean install
   mvn spring-boot:run

API Endpoints:
--------------
POST   /api/auth/register    - Register a new user (public)
POST   /api/auth/login       - Login user and get JWT token (public)
GET    /api/profile          - Get logged-in user's profile (USER, ADMIN)
GET    /api/employees        - List all employees (USER, ADMIN)
POST   /api/employees        - Create employee (ADMIN only)
PUT    /api/employees/{id}   - Update employee by ID (ADMIN only)
DELETE /api/employees/{id}   - Delete employee by ID (ADMIN only)

Role-Based Access Control:
--------------------------
- ROLE_USER: View profile and list employees
- ROLE_ADMIN: Full employee CRUD access

Exception Handling:
-------------------
- Handles user not found, bad credentials, access denied, resource not found and other exceptions
- Returns meaningful HTTP status codes and error messages

Logging:
--------
- Logs key events such as authentication, employee creation/update/deletion
- Uses SLF4J Logger for structured logs

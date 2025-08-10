Smart Task Manager Backend
This document provides an overview of the Smart Task Manager backend application. It covers the project's architecture, technologies used, and instructions for running the application.

Overview
The Smart Task Manager backend is a robust Spring Boot application that provides RESTful APIs for managing tasks, users, and their roles. It is designed to work in conjunction with a frontend application to create a comprehensive task management system for teams. The application implements a secure, token-based authentication system using JWT and Spring Security, ensuring that all API interactions are authorized.

Key Features
User Management: APIs for user registration, login, and profile management.

Role-Based Access Control (RBAC): Users are assigned roles (ADMIN, MANAGER, EMPLOYEE) to control access to different functionalities.

Task Management: APIs to create, read, update, and delete tasks.

Secure Authentication: JSON Web Token (JWT) is used for stateless authentication.

Data Transfer Objects (DTOs): Uses DTOs to cleanly separate the data transfer layer from the persistence layer.

Database Integration: Utilizes JPA and Hibernate for object-relational mapping with a relational database.

Technologies Used
Spring Boot: The core framework for building the application.

Spring Security: For authentication and authorization.

JWT (JSON Web Tokens): For secure, token-based authentication.

Spring Data JPA: For database interactions and repository management.

Hibernate: The JPA implementation for ORM.

Lombok: A library to reduce boilerplate code (e.g., getters, setters).

Jakarta Persistence API: Standard for ORM in Java.

MySQL/PostgreSQL/H2: The application can connect to any relational database. H2 is a good choice for development and testing.

Getting Started
Prerequisites
Before you begin, ensure you have the following installed:

Java Development Kit (JDK) (version 17 or higher)

Maven

An IDE like IntelliJ IDEA or VS Code with a Java extension.

1. Database Configuration
By default, the application is configured to connect to a relational database. You will need to update the src/main/resources/application.properties file with your database credentials.

Example for a MySQL database:

spring.datasource.url=jdbc:mysql://localhost:3306/smart_task_manager
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

For development, you can use an in-memory H2 database by setting:

spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:smart_task_manager
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

2. Running the Application
You can run the application directly from your IDE or using Maven from the command line:

# From the project's root directory
mvn spring-boot:run

Once the application is running, it will be available at http://localhost:8080.

3. API Endpoints
The application exposes a set of RESTful endpoints. Here are some key examples:

POST /api/auth/register: Register a new user.

POST /api/auth/login: Log in and receive a JWT.

GET /api/tasks/assigned: Get tasks assigned to the authenticated user.

PATCH /api/tasks/{taskId}/status: Update a task's status.

Refer to the source code for a complete list of endpoints and their functionalities.
--Student Management System - Spring Boot

A robust and RESTful Spring Boot application that manages student records with full CRUD operations. This project demonstrates layered architecture with clean code practices, DTO usage, validation, exception handling, and unit testing.

---

--Features

- Create, Retrieve, Update, Delete (CRUD) operations for Student entities
- Field validation using Jakarta Bean Validation
- Exception handling with custom exceptions and global error responses
- RESTful API design
- DTO (Data Transfer Object) pattern to decouple entities from the controller
- Unit tests for service and controller layers
- Uses Lombok for cleaner and concise code
- MySQL database

---

--Technologies Used

- Java 21
- Spring Boot 3.5.3
- Spring Data JPA
- Hibernate Validation
- Lombok
- MySQL
- JUnit 5
- Mockito
- SLF4J (Logging)
- Maven

---

--Project Structure

- src/main/java
  - com.jeevlifeworks.Student_Management_System_SpringBoot
    - StudentManagementSystemSpringBootApplication.java
  - com.jeevlifeworks.Student_Management_System_SpringBoot.controller
    - StudentController.java
  - com.jeevlifeworks.Student_Management_System_SpringBoot.dto
    - StudentRequestDTO.java
    - StudentResponseDTO.java
  - com.jeevlifeworks.Student_Management_System_SpringBoot.exception
    - GlobalExceptionHandler.java
    - StudentNotFoundException.java
  - com.jeevlifeworks.Student_Management_System_SpringBoot.model
    - Student.java
  - com.jeevlifeworks.Student_Management_System_SpringBoot.repository
    - StudentRepository.java
  - com.jeevlifeworks.Student_Management_System_SpringBoot.service
    - StudentService.java
    - StudentServiceImpl.java

- src/main/resourses
  - application.properties

- src/test/java
  - com.jeevlifeworks.Student_Management_System_SpringBoot
    - StudentManagementSystemSpringBootApplicationTests.java
  - com.jeevlifeworks.Student_Management_System_SpringBoot.controller
    - StudentControllerTest.java
  - com.jeevlifeworks.Student_Management_System_SpringBoot.service
    - StudentServiceImplTest.java

- pom.xml

---

--MySQL DB Setup Instructions - 

- Install MySQL Server: If you don't have MySQL installed, download and install it from the official MySQL website.
- Create Database: Open your MySQL client and execute the following SQL commands to create the database and table:
   CREATE DATABASE student_database;
- Configure application.properties - Update the src/main/resources/application.properties file with your MySQL database credentials.
 
  spring.datasource.url=jdbc:mysql://localhost:3306/student_database
  spring.datasource.username=your_mysql_username
  spring.datasource.password=your_mysql_password

---

-- How to Run the Application - 

- Clone / Download the Project: Get the project files.
- Open Eclipse/IntelliJ/VS Code IDE import the project.
- IDE will automatically download the required dependencies, if not execute the command in your IDE terminal - mvn clean install
- Run the Application: Execute the main application.
  
---

-- How to Run Tests -

- Execute command in IDE terminal - mvn test  

---

-- Postman 

-- url: http://localhost:8080/{endpoints} 

-- API Endpoints

| Method | Endpoint             | Description            |
|--------|----------------------|------------------------|
| POST   | `/api/students`      | Add a new student      |
| GET    | `/api/students`      | Get all students       |
| GET    | `/api/students/{id}` | Get a student by ID    |
| PUT    | `/api/students/{id}` | Update student by ID   |
| DELETE | `/api/students/{id}` | Delete student by ID   |

---

-- Validation Rules

- name - Must not be blank
- age - Must be greater than 0
- grade - Must match pattern (A+, B, C-, etc.)



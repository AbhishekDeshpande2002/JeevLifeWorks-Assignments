Student Management System

Objective -
A comprehensive, scalable Java application using Java 8 features and JDBC to manage a list of students. The application interfaces with a MySQL database to perform robust and efficient CRUD operations with validation, logging, and configuration management.

Technologies Used -
Java 8: Streams, Lambdas, Optionals, Functional Interfaces
JDBC: For database connectivity
MySQL: Relational database
Maven: Project build automation and dependency management
JUnit 5: For unit testing

Architecture - 
The application follows a layered architecture:
1. Entity Layer: Student class representing the data model.
2. DAO Layer (Data Access Object): StudentDAO interface and StudentDAOImpl provide an abstract interface to the database. Responsible for CRUD operations directly with the database.
3. Service Layer: StudentService interface and StudentServiceImpl encapsulate business logic, validation, and transaction management. It interacts with the DAO layer.
4. Utility Layer: Contains helper classes for database connection.
5. Exception Layer: Contains Custom Exception for handling exceptions.
6. Main Application (Console UI): MainApplication class provides a simple console-based user interface for interacting with the service layer.

MySQL DB Setup Instructions - 
1. Install MySQL Server: If you don't have MySQL installed, download and install it from the official MySQL website.
2. Create Database: Open your MySQL client and execute the following SQL commands to create the database and table:
   CREATE DATABASE student_management_db;

   CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    grade VARCHAR(100) NOT NULL,
    address VARCHAR(100) NOT NULL);
3. Configure configuration.properties: Update the src/main/resources/configuration/db.properties file with your MySQL database credentials.
   db.url=jdbc:mysql://localhost:3306/student_db
   db.username=your_mysql_username
   db.password=your_mysql_password
   
How to Run the Application - 
1. Clone / Download the Project: Get the project files.
2. Open Eclipse/IntelliJ/VS Code IDE import the project.
3. IDE will automatically download the required dependencies, if not execute the command in your IDE terminal - mvn clean install
4. Run the Application: Execute the main application.

How to Run Tests -
1. Open src/test/java/com/student_management/StudentServiceTest.java and run as JUnit tests
2. Another method is execute command in IDE terminal - mvn test

Deliverables -
1. Sample Exported CSV: A students_export.csv file will be generated in the project root when you choose the export option in the console UI.
2. Log File: A .log file will be generated in the logs folder
3. Screenshots: Please refer to the UI provided in MainApplication.java for how the application runs in the console.
# Employee Management System

This is a modular Employee Management System developed using Spring Boot, Java 8, JDBC, and Java Collections. It follows a microservices architecture, providing user authentication via JWT and CRUD operations for employees.

## Project Structure

The project consists of two independent Spring Boot microservices:

1.  **User Service**: Handles user registration, login, and JWT-based authentication.
2.  **Employee Service**: Manages employee CRUD operations, secured by JWT.


## Technologies Used

* **Java 21**
* **Spring Boot 3.5.3**
* **Spring Security**
* **JWT (JSON Web Tokens)** for authentication and authorization
* **Spring JDBC Template** for database interaction in Employee Service
* **Spring Data JPA** for User Service (for simplicity of user management)
* **MySQL**
* **Lombok** (optional, for reducing boilerplate code)
* **Maven** for build automation


## Setup and Running the Services

### Prerequisites

* Java 8 or higher (Java 21 recommended for Spring Boot 3+)
* Maven 3.x
* MySQL Server

### 1. Database Setup (MySQL for Employee Service)

1.  **Create Database**:
    Log in to your MySQL server and create a database named `employeedb`.

    ```sql
    CREATE DATABASE employeedb;
    ```

2.  **Create Table**:

    ```sql
    USE employeedb;
    CREATE TABLE IF NOT EXISTS employees (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        department VARCHAR(255) NOT NULL,
        position VARCHAR(255) NOT NULL,
        salary DOUBLE NOT NULL
    );
    ```

3.  **Update `application.properties` for Employee Service and User Service**:
    Open `/src/main/resources/application.properties` and update the MySQL connection details:
    ```properties
    spring.datasource.username=your_mysql_username
    spring.datasource.password=your_mysql_password
    ```


### 2. Build and Run Each Service

Navigate to the root directory `EmployeeManagementSystemWeek_6` in your terminal.

#### User Service

1.  **Navigate to User Service directory**:
    ```bash
    cd user_service
    ```
2.  **Build the project**:
    ```bash
    mvn clean install
    ```
3.  **Run the application**:
    ```bash
    mvn spring-boot:run
    ```
    The User Service will start on `http://localhost:8080`.

#### Employee Service

1.  **Navigate back to the root and then to Employee Service directory**:
    ```bash
    cd ..
    cd employee_service
    ```
2.  **Build the project**:
    ```bash
    mvn clean install
    ```
3.  **Run the application**:
    ```bash
    mvn spring-boot:run
    ```
    The Employee Service will start on `http://localhost:8081`.


## Testing Endpoints with Postman/Swagger

Once both services are running, you can test the API endpoints.

### User Service Endpoints (`http://localhost:8080`)

#### 1. Register User (POST)

* **URL**: `/users/register`
* **Method**: `POST`
* **Headers**: `Content-Type: application/json`
* **Body (raw, JSON)**:
    ```json
    {
        "username": "testuser",
        "password": "password123",
        "email": "test@example.com"
    }
    ```
* **Expected Response (201 Created)**:
    ```json
    {
        "id": 1,
        "username": "testuser",
        "email": "test@example.com",
        "token": "eyJhbGciOiJIUzI1NiJ9..." // JWT token
    }
    ```

#### 2. Login User (POST)

* **URL**: `/users/login`
* **Method**: `POST`
* **Headers**: `Content-Type: application/json`
* **Body (raw, JSON)**:
    ```json
    {
        "username": "testuser",
        "password": "password123"
    }
    ```
* **Expected Response (200 OK)**:
    ```json
    {
        "id": 1,
        "username": "testuser",
        "email": "test@example.com",
        "token": "eyJhbGciOiJIUzI1NiJ9..." // New JWT token
    }
    ```
    **_Important:_** Save the `token` from this response. You will need it to access secured Employee Service endpoints.

#### 3. Get User Profile (GET)

* **URL**: `/users/profile`
* **Method**: `GET`
* **Headers**:
    * `Content-Type: application/json`
    * `Authorization: Bearer <YOUR_JWT_TOKEN>` (Replace `<YOUR_JWT_TOKEN>` with the token obtained from login)
* **Expected Response (200 OK)**:
    ```json
    {
        "id": 1,
        "username": "testuser",
        "email": "test@example.com",
        "token": null
    }
    ```

### Employee Service Endpoints (`http://localhost:8081`)

**_All Employee Service endpoints require a valid JWT in the `Authorization: Bearer <token>` header._**

#### 1. List All Employees (GET)

* **URL**: `/employees`
* **Method**: `GET`
* **Headers**:
    * `Authorization: Bearer <YOUR_JWT_TOKEN>`
* **Expected Response (200 OK)**:
    ```json
    [
        {
            "id": 1,
            "name": "John Doe",
            "department": "Engineering",
            "position": "Software Engineer",
            "salary": 75000.0
        }
    ]
    ```

#### 2. Get Employee by ID (GET)

* **URL**: `/employees/{id}` (e.g., `/employees/1`)
* **Method**: `GET`
* **Headers**:
    * `Authorization: Bearer <YOUR_JWT_TOKEN>`
* **Expected Response (200 OK)**:
    ```json
    {
        "id": 1,
        "name": "John Doe",
        "department": "Engineering",
        "position": "Software Engineer",
        "salary": 75000.0
    }
    ```
* **Error Response (404 Not Found)** if ID does not exist.

#### 3. Create Employee (POST)

* **URL**: `/employees`
* **Method**: `POST`
* **Headers**:
    * `Content-Type: application/json`
    * `Authorization: Bearer <YOUR_JWT_TOKEN>`
* **Body (raw, JSON)**:
    ```json
    {
        "name": "Jane Smith",
        "department": "HR",
        "position": "HR Manager",
        "salary": 60000.0
    }
    ```
* **Expected Response (201 Created)**:
    ```json
    {
        "id": 2,
        "name": "Jane Smith",
        "department": "HR",
        "position": "HR Manager",
        "salary": 60000.0
    }
    ```

#### 4. Update Employee (PUT)

* **URL**: `/employees/{id}` (e.g., `/employees/1`)
* **Method**: `PUT`
* **Headers**:
    * `Content-Type: application/json`
    * `Authorization: Bearer <YOUR_JWT_TOKEN>`
* **Body (raw, JSON)**:
    ```json
    {
        "id": 1,
        "name": "John Doe Updated",
        "department": "Engineering",
        "position": "Senior Software Engineer",
        "salary": 85000.0
    }
    ```
* **Expected Response (200 OK)**:
    ```json
    {
        "id": 1,
        "name": "John Doe Updated",
        "department": "Engineering",
        "position": "Senior Software Engineer",
        "salary": 85000.0
    }
    ```
* **Error Response (404 Not Found)** if ID does not exist.

#### 5. Delete Employee (DELETE)

* **URL**: `/employees/{id}` (e.g., `/employees/2`)
* **Method**: `DELETE`
* **Headers**:
    * `Authorization: Bearer <YOUR_JWT_TOKEN>`

* **Expected Response (204 No Content)**

* **Error Response (404 Not Found)** if ID does not exist.

#### 6. Group Employees by Department (GET)

* **URL**: `/employees/group-by-department`
* **Method**: `GET`
* **Headers**:
    * `Authorization: Bearer <YOUR_JWT_TOKEN>`
* **Expected Response (200 OK)**:
    ```json
    {
        "Engineering": [
            { "id": 1, "name": "John Doe", "department": "Engineering", "position": "Software Engineer", "salary": 75000.0 }
        ],
        "HR": [
            { "id": 2, "name": "Jane Smith", "department": "HR", "position": "HR Manager", "salary": 60000.0 }
        ]
    }
    ```

#### 7. Filter Employees by Salary (GET)

* **URL**: `/employees/filter-by-salary?minSalary=70000`
* **Method**: `GET`
* **Headers**:
    * `Authorization: Bearer <YOUR_JWT_TOKEN>`
* **Expected Response (200 OK)**:
    ```json
    [
        {
            "id": 1,
            "name": "John Doe",
            "department": "Engineering",
            "position": "Software Engineer",
            "salary": 75000.0
        }
    ]
    ```

#### 8. Sort Employees by Salary (GET)

* **URL**: `/employees/sort-by-salary`
* **Method**: `GET`
* **Headers**:
    * `Authorization: Bearer <YOUR_JWT_TOKEN>`
* **Expected Response (200 OK)** (employees sorted by salary ascending):
    ```json
    [
        {
            "id": 2,
            "name": "Jane Smith",
            "department": "HR",
            "position": "HR Manager",
            "salary": 60000.0
        },
        {
            "id": 1,
            "name": "John Doe",
            "department": "Engineering",
            "position": "Software Engineer",
            "salary": 75000.0
        }
    ]
    ```

---

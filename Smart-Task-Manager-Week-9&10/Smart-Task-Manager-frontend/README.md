Smart Task Manager Frontend
This document provides an overview of the Smart Task Manager frontend application, including its setup, dependencies, and how to run it.

Overview
The Smart Task Manager is a multi-user application built with Angular, designed to help teams manage tasks efficiently. It features distinct dashboards for different user roles (Admin, Manager, Employee), secure authentication, and a robust routing system. The application uses a standalone component architecture, making it modular and easy to maintain.

Features
User Authentication: Secure login and registration with role-based access control.

Role-Based Dashboards: Separate views for administrators, managers, and employees.

Secure Routing: Utilizes an AuthGuard to protect routes and ensure users have the correct permissions.

HTTP Interceptors: Automatically attaches a JSON Web Token (JWT) to every outgoing API request for authentication.

Environment Configuration: Easy-to-manage environment files for development and production settings.

Toast Notifications: Provides user feedback with clear, non-intrusive toast messages for actions like login or registration.

Technologies Used
Angular: The core framework for the application.

TypeScript: The primary language for development.

ngx-toastr: A library for displaying toast notifications.

Angular Router: For handling navigation and routing within the application.

Angular HttpClient: For making API calls to the backend.

Getting Started
Prerequisites
Before you begin, ensure you have the following installed:

Node.js (version 16 or higher)

npm (comes with Node.js)

Angular CLI (npm install -g @angular/cli)

1. Installation
Navigate to the project root directory and install all the necessary npm packages:

npm install

2. Configuration
The application is configured to connect to a backend API. You may need to update the API URL in the src/environments/environment.ts file if your backend is not running on http://localhost:8080/api.

// src/environments/environment.ts
export const environment = {
  production: false,
  apiBaseUrl: 'http://localhost:8080/api' // Update this URL if needed
};

3. Running the Application
To start the development server, run the following command:

ng serve

The application will be accessible at http://localhost:4200. It will automatically reload if you change any of the source files.

4. Running a Production Build
To create a production-ready build of the application, use the following command:

ng build --configuration production

The build artifacts will be stored in the dist/ directory.

This README should help you and other developers quickly understand and set up the Smart Task Manager frontend project.
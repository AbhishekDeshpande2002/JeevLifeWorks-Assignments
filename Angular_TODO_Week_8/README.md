# Angular Todo App with Authentication

This is a basic Angular web application built as part of an internship assignment. It features user authentication (signup & login) and a todo list interface. The app is designed using Reactive Forms, modular architecture, and standalone components.

## Features

-  User Registration (Signup)
-  User Login (with simple local validation)
-  To-Do List (Add/Delete tasks)
-  Form Validation using Reactive Forms
-  LocalStorage-based persistence
-  Modular architecture using standalone components

## Project Structure

src/
├── app/
│ ├── auth/
│ │ ├── login/
│ │ ├── signup/
│ │ ├── auth.guard.ts
│ │ └── auth.service.ts
│ ├── todo/
│ ├── app-routing.module.ts
│ ├── app.component.ts / .html / .css
├── assets/
├── styles.css
├── index.html
├── main.ts
...



##  Features

- User Signup: Register a new user using reactive forms
- User Login: Log in with saved credentials from localStorage
- Auth Guard: Restricts access to the todo page if not authenticated
- Todo List:
  - Add tasks
  - Delete tasks
  - Persist tasks in local storage

##  Getting Started

## Prerequisites

- Node.js and npm
- Angular CLI

Install Angular CLI if not already installed:
```bash
npm install -g @angular/cli

# Clone the repository
git clone <your-repo-url>
cd angular-todo-app

# Install dependencies
npm install

## Running App 
ng serve
Visit http://localhost:4200/ in your browser

| Module                  | Purpose                               |
| ----------------------- | ------------------------------------- |
| `auth/`                 | Handles login, signup, authentication |
| `todo/`                 | Manages todo functionality            |
| `app-routing.module.ts` | Centralized routing config            |

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-todo',
  templateUrl: './todo.component.html',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule]
})
export class TodoComponent implements OnInit {
  // Reactive form to add a new task
  form!: FormGroup;
  // Array to store all tasks
  tasks: any[] = [];

  constructor(
    // FormBuilder service for building the form
    private fb: FormBuilder,
    // Custom AuthService for logout functionality
    private auth: AuthService,
    // Router to navigate on logout
    private router: Router
  ) {}

  ngOnInit(): void {
    // Initialize the form with title (required) and optional description
    this.form = this.fb.group({
      title: ['', Validators.required],
      description: ['']
    });

    // Load existing tasks from localStorage, or initialize with empty array
    const savedTasks = localStorage.getItem('tasks');
    this.tasks = savedTasks ? JSON.parse(savedTasks) : [];
  }

  // Getter for pending tasks
  get pendingTasks() {
    return this.tasks.filter(task => !task.completed);
  }

  // Getter for completed tasks
  get completedTasks() {
    return this.tasks.filter(task => task.completed);
  }

  // Add a new task to the list
  addTask(): void {
    if (this.form.valid) {
      // Add default completed:false
      const newTask = { ...this.form.value, completed: false };
      // Push to task list
      this.tasks.push(newTask);
      // Save updated list
      localStorage.setItem('tasks', JSON.stringify(this.tasks));
      // Reset form fields
      this.form.reset();
    }
  }

  // Toggle a task's completion status
  toggleComplete(task: any): void {
    task.completed = !task.completed;
    // Update localStorage
    localStorage.setItem('tasks', JSON.stringify(this.tasks));
  }

  // Delete a task from the list by index
  deleteTask(index: number): void {
    this.tasks.splice(index, 1);
    // Update localStorage
    localStorage.setItem('tasks', JSON.stringify(this.tasks));
  }

  // Logout user and navigate to login page
  logout(): void {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}

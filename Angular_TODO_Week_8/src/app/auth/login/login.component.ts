import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';  // Required if the template uses routerLink or routerOutlet
import { AuthService } from '../auth.service';

@Component({
  // Selector for the login component
  selector: 'app-login', 
  // HTML template file for the component
  templateUrl: './login.component.html', 
  // This makes the component a standalone component (no need to declare it in a module)
  standalone: true, 
  // Required modules for forms and routing
  imports: [CommonModule, ReactiveFormsModule, RouterModule],  
})
export class LoginComponent {
  // Holds error message for invalid login
  error = '';
  // Form group instance for login form
  form: FormGroup;

  constructor(
    // Used to build the reactive form
    private fb: FormBuilder,
    // Auth service to perform login logic
    private auth: AuthService,
    // Router to navigate after successful login
    private router: Router
  ) {
    // Initialize the form with two controls: email and password
    this.form = this.fb.group({
      // Email field with required and email validators
      email: ['', [Validators.required, Validators.email]],
      // Password field with required validator
      password: ['', Validators.required]
    });
  }

  // Function triggered on form submit
  login() {
    // Destructure form values
    const { email, password } = this.form.value; 
    // Call auth service to validate credentials
    if (this.auth.login(email!, password!)) {
      // If login successful, navigate to "todo" page
      this.router.navigate(['/todo']);
    } else {
      // Show error message if login fails
      this.error = 'Invalid email or password';
    }
  }
}

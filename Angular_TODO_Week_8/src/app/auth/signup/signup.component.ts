import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, AbstractControl, ValidationErrors } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  standalone: true,
  // Import necessary Angular modules for template rendering and form handling
  imports: [CommonModule, ReactiveFormsModule, RouterModule], 
})

export class SignupComponent {
  // Reactive form group instance to handle form controls and validations
  form: FormGroup;

  constructor(
    private fb: FormBuilder, // FormBuilder helps construct the form group
    private auth: AuthService, // AuthService for signup logic (storing user)
    private router: Router // Router for navigation after signup
  ) {
    // Define form controls and their validators
    this.form = this.fb.group({
      fullName: ['', Validators.required], // Full name is required
      email: ['', [Validators.required, Validators.email]], // Email is required and must be in valid format
      password: ['', [Validators.required, Validators.minLength(6)]], // Password required with minimum length
      confirmPassword: ['', Validators.required] // Confirm password field required
    }, { validators: this.passwordMatchValidator }); // Custom validator to match password and confirmPassword
  }

  /**
   * Custom validator to ensure password and confirmPassword match
   * Accepts parameter control - FormGroup control
   * returns ValidationErrors if mismatch, otherwise null
   */
  passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const password = control.get('password')?.value;
    const confirmPassword = control.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { mismatch: true };
  }

  /**
   * Called on form submission
   * - If form is valid, user details are saved to localStorage using AuthService
   * - Then navigate to the login page
   */
  signup() {
    if (this.form.valid) {
      this.auth.signup(this.form.value); // Store user credentials
      this.router.navigate(['/login']); // Redirect to login after signup
    }
  }
}

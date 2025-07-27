// Import Angular core functionality
import { Component } from '@angular/core';
// Import CommonModule to use common Angular directives like *ngIf, *ngFor
import { CommonModule } from '@angular/common';
// Import RouterModule to enable router-outlet for displaying routed components
import { RouterModule } from '@angular/router';

@Component({
  // The CSS selector used to insert this component into the DOM
  selector: 'app-root',
  // Path to the component's HTML template
  templateUrl: './app.component.html',
  // Declare this as a standalone component (not declared in any NgModule)
  standalone: true,
  // Import necessary modules used in this component's template
  imports: [CommonModule, RouterModule]   // // Allows use of *ngIf, *ngFor, router-outlet, etc.
})
export class AppComponent {
  // This root component simply serves as a container for routed components via <router-outlet>
}

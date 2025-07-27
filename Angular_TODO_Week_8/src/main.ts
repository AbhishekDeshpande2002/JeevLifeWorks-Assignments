// Import Angular's zone.js which is required for change detection and async tracking.
import 'zone.js';

// Import bootstrapApplication for bootstrapping a standalone component
import { bootstrapApplication } from '@angular/platform-browser';
// Import the router provider to define application routes
import { provideRouter } from '@angular/router';

// Import root and route components
import { AppComponent } from './app/app.component';
import { LoginComponent } from './app/auth/login/login.component';
import { SignupComponent } from './app/auth/signup/signup.component';
import { TodoComponent } from './app/todo/todo.component';

//  Bootstrap the Angular application using standalone `AppComponent`
//  and provide the routing configuration
bootstrapApplication(AppComponent, {
  providers: [
    // Define application routes
    provideRouter([
      { path: '', redirectTo: 'login', pathMatch: 'full' }, // default route
      { path: 'login', component: LoginComponent }, // login page
      { path: 'signup', component: SignupComponent }, // signup page
      { path: 'todo', component: TodoComponent } // to-do list page
    ])
  ]
}).catch(err => console.error(err)); // Log bootstrap errors

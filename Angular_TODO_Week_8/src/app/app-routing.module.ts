import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Importing components used in routing
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { TodoComponent } from './todo/todo.component';

// AuthGuard ensures only logged-in users can access protected routes like /todo
import { AuthGuard } from './auth/auth.guard';

//  Route definitions
const routes: Routes = [
  // Default route redirects to login page
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  // Login route
  { path: 'login', component: LoginComponent },
  // Signup route
  { path: 'signup', component: SignupComponent },
  // Todo route protected by AuthGuard
  { path: 'todo', component: TodoComponent, canActivate: [AuthGuard] }
];

@NgModule({
  // Register routes in Angular's root module
  imports: [RouterModule.forRoot(routes)],
  // Export RouterModule so it can be used in other parts of the app
  exports: [RouterModule]
})
export class AppRoutingModule {}
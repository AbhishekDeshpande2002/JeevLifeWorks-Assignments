import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from './auth.service';

// This decorator makes the guard available application-wide
@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {

  // Inject AuthService to check login status
  // Inject Router to navigate to login if user is not authenticated
  constructor(private authService: AuthService, private router: Router) {}


  /**
   * Determines whether a route can be activated
   * Returns true if the user is logged in
   * Otherwise, redirects to the login page and returns false
   */
  canActivate(): boolean {
    if (this.authService.isLoggedIn()) return true;

    // User not logged in → redirect to login
    this.router.navigate(['/login']);
    return false;
  }
}
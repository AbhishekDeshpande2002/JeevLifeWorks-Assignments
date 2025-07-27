import { Injectable } from '@angular/core';

// This decorator registers the AuthService at the root level,
// making it a singleton service available throughout the app.
@Injectable({ providedIn: 'root' })
export class AuthService {
  // Tracks the user's authentication status
  isAuthenticated = false;

  /**
   * Registers a new user by storing their data in localStorage.
   * Accepts parameter user - The user object containing fullName, email, password, etc.
   */
  signup(user: any) {
    localStorage.setItem(user.email, JSON.stringify(user));
  }

  /**
   * Logs in a user by validating email and password against stored data.
   * If credentials match, sets isAuthenticated to true.
   * Accepts parameter email - The user's email.
   * Accepts parameter password - The user's password.
   * returns true if login is successful, false otherwise.
   */
  login(email: string, password: string): boolean {
    const user = JSON.parse(localStorage.getItem(email) || '{}');
    if (user && user.password === password) {
      this.isAuthenticated = true;
      return true;
    }
    return false;
  }

  /**
   * Logs out the current user by resetting authentication status.
   */
  logout() {
    this.isAuthenticated = false;
  }

  /**
   * Checks if a user is currently logged in.
   * returns true if the user is authenticated, false otherwise.
   */
  isLoggedIn(): boolean {
    return this.isAuthenticated;
  }
}

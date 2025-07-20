package com.jeevlifeworks.SpringSecurity.service;

import com.jeevlifeworks.SpringSecurity.dto.AuthRequest;
import com.jeevlifeworks.SpringSecurity.entities.User;

/**
 * AuthService defines the contract for authentication and registration operations.
 * It is implemented by a service class AuthServiceImpl to handle user-related
 * authentication logic such as registering new users and generating JWT tokens.
 */
public interface AuthService {
	
	/**
     * Registers a new user in the system.
     *
     * Accepts parameter user, The user entity containing registration details like username, password and role.
     * return A confirmation message or JWT token after successful registration.
     */
	String register(User user);
	
	/**
     * Authenticates a user and generates a JWT token upon successful login.
     *
     * Accepts parameter request, The authentication request containing username and password.
     * return A JWT token if authentication is successful.
     */
    String authenticate(AuthRequest request);
}

package com.jeevlifeworks.SpringSecurity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jeevlifeworks.SpringSecurity.dto.AuthRequest;
import com.jeevlifeworks.SpringSecurity.entities.User;
import com.jeevlifeworks.SpringSecurity.repository.UserRepository;
import com.jeevlifeworks.SpringSecurity.security.JwtService;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of the AuthService interface.
 * Handles user registration and authentication using Spring Security and JWT.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	private final UserRepository userRepository; // Repository for CRUD operations on User entity
    private final PasswordEncoder passwordEncoder; // Bean for securely hashing passwords
    private final AuthenticationManager authManager; // Spring Security's manager to authenticate credentials
    private final JwtService jwtService; // Service to generate and validate JWT tokens

    
    /**
     * Registers a new user by encoding their password and saving the user to the database.
     *
     * Accepts parameter user, The user object containing username, password, and role
     * return A confirmation message after successful registration
     */
    public String register(User user) {
    	logger.info("Registering new user with username: "+ user.getUsername());
    	// Encode the user's raw password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Save user to database
        userRepository.save(user);
        logger.info("User "+ user.getUsername() +" registered successfully");
        return "User registered successfully";
    }


    /**
     * Authenticates a user and generates a JWT token if credentials are valid.
     *
     * Accepts parameter request, Object containing the username and password
     * return JWT token string if authentication is successful
     */
    public String authenticate(AuthRequest request) {
    	logger.info("Authenticating user with username: "+ request.getUsername());
    	// Create an authentication token and authenticate it using AuthenticationManager
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        
        logger.info("User "+ request.getUsername() +" authenticated successfully");
     // Generate JWT token for the authenticated user
        return jwtService.generateToken((org.springframework.security.core.userdetails.UserDetails) auth.getPrincipal());
    }
}

package com.jeevlifeworks.SpringSecurity.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeevlifeworks.SpringSecurity.dto.AuthRequest;
import com.jeevlifeworks.SpringSecurity.entities.User;
import com.jeevlifeworks.SpringSecurity.service.AuthService;


import lombok.RequiredArgsConstructor;

/**
 * REST controller for authentication-related endpoints such as registration and login.
 */

@RestController
@RequestMapping("/api/auth") // Base URL for all auth endpoints
@RequiredArgsConstructor
public class AuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	private final AuthService authService;

	/**
     * Handles user registration requests.
     *
     * Accepts parameter user User entity sent in the request body containing username, password and role.
     * return ResponseEntity containing a success message after registration
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
    	logger.info("Received registration request for username: "+ user.getUsername());
    	String message = authService.register(user);
        logger.info("User "+ user.getUsername() +" registered successfully");
        return ResponseEntity.ok(message);
    }

    /**
     * Handles user login requests.
     *
     * Accepts parameter request AuthRequest DTO containing username and password
     * return ResponseEntity containing a JSON map with the JWT token if authentication succeeds
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest request) {
    	// Returns the JWT token in the response body under the key "token"
    	logger.info("Login attempt for username: "+ request.getUsername());
    	String token = authService.authenticate(request);
        logger.info("JWT token generated for user: "+request.getUsername());
        return ResponseEntity.ok(Map.of("token", token));
    }
}

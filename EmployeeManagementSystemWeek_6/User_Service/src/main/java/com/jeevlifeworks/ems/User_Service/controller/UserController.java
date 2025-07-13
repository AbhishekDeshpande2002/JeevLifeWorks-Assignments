package com.jeevlifeworks.ems.User_Service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeevlifeworks.ems.User_Service.dto.LoginRequest;
import com.jeevlifeworks.ems.User_Service.dto.RegisterRequest;
import com.jeevlifeworks.ems.User_Service.dto.UserResponse;
import com.jeevlifeworks.ems.User_Service.service.UserService;

import jakarta.validation.Valid;

/**
 * UserController handles all HTTP requests related to user operations.
 */

@RestController
@RequestMapping("/users")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    /**
     * Constructs a new UserController with dependency injection.
     * Accepts parameter userService The user service to handle business logic.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * POST API for user registration.
     * Accepts parameter request The registration request containing username, password, and email.
     * returns a ResponseEntity containing the registered user's details and JWT token, or an error.
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        logger.info("Attempting to register user: {}", request.getUsername());
        UserResponse response = userService.registerUser(request);
        logger.info("User registered successfully: {}", request.getUsername());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * POST API for user login.
     * Accepts parameter request The login request containing username and password.
     * returns a ResponseEntity containing the logged-in user's details and JWT token, or an error.
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@Valid @RequestBody LoginRequest request) {
        logger.info("Attempting to log in user: {}", request.getUsername());
        UserResponse response = userService.loginUser(request);
        logger.info("User logged in successfully: {}", request.getUsername());
        return ResponseEntity.ok(response);
    }

    /**
     * GET API to retrieve the profile of the currently logged-in user.
     * Requires JWT authentication.
     * returns A ResponseEntity containing the logged-in user's details.
     */
    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
            logger.info("Attempting to retrieve profile for user: {}", username);
            
            UserResponse userProfile = userService.getUserProfile(username);
            logger.info("Profile retrieved successfully for user: {}", username);
            return ResponseEntity.ok(userProfile);
        }
        
        logger.warn("No authenticated user found for profile request.");
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}

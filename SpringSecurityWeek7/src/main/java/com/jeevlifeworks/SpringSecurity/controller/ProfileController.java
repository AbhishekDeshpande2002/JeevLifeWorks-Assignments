package com.jeevlifeworks.SpringSecurity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for user profile related endpoints.
 * Provides access to authenticated user's profile information.
 */
@RestController
@RequestMapping("/api/profile") // Base path for profile-related requests
public class ProfileController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	/**
     * Endpoint to get the current authenticated user's profile information.
     * 
     * Accepts parameter auth, Injected Authentication object representing the current user
     * return ResponseEntity with a greeting message including the username
     */
	 @GetMapping
	    public ResponseEntity<String> getProfile(Authentication auth) {
		 logger.info("Profile requested by user: "+ auth.getName());
		// Returns a simple greeting message with the authenticated user's username
	        return ResponseEntity.ok("Hello, " + auth.getName());
	    }
}

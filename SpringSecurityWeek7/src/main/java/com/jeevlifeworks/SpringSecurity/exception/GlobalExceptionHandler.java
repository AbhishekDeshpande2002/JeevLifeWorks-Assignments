package com.jeevlifeworks.SpringSecurity.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Global exception handler for the application.
 * Catches and handles exceptions across all controllers,
 * providing meaningful responses to the client.
 */

@RestControllerAdvice // Applies exception handling globally to all @RestController classes
@Slf4j // Lombok annotation to enable logging via SLF4J
public class GlobalExceptionHandler {
	
	/**
     * Handles exceptions when a username is not found during authentication.
     * accepts parameter ex, the UsernameNotFoundException
     * return a NOT_FOUND (404) response with the error message
     */
	@ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFound(UsernameNotFoundException ex) {
		log.warn("Username not found: "+ ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

	/**
     * Handles incorrect login credentials.
     * accepts parameter ex the BadCredentialsException
     * return an UNAUTHORIZED (401) response with a custom error message
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials(BadCredentialsException ex) {
    	log.warn("Bad credentials: "+ ex.getMessage());
        return buildErrorResponse("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles access denied errors (e.g., unauthorized access to restricted resources).
     * accepts parameter ex the AccessDeniedException
     * return a FORBIDDEN (403) response with a custom message
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
    	log.warn("Access denied: "+ ex.getMessage());
        return buildErrorResponse("Access Denied", HttpStatus.FORBIDDEN);
    }

    /**
     * Handles uncaught exceptions and logs the error.
     * Acts as a fallback for all other exception types.
     * accepts parameter ex the unexpected Exception
     * accepts parameter request the incoming HTTP request
     * return an INTERNAL_SERVER_ERROR (500) response with a generic message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error occurred: "+ ex.getMessage());
        return buildErrorResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Handles custom ResourceNotFoundException.
     * Returns a detailed error response with custom fields.
     * accepts parameter ex the ResourceNotFoundException
     * return a NOT_FOUND (404) response with structured error details
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundHandler(ResourceNotFoundException ex) {
    	log.warn("Resource not found: "+ ex.getMessage());
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("details", "Resource not found exception occurred");

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    
    /**
     * Helper method to build a structured error response map.
     * Used by multiple exception handlers to avoid repetition.
     * accepts parameter message the error message
     * accepts parameter status the corresponding HTTP status
     * return a ResponseEntity containing the error details
     */
    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        return new ResponseEntity<>(error, status);
    }
}

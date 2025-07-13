package com.jeevlifeworks.ems.Employee_Service.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

/**
 * Global exception handler for the Employee Service.
 * Catches and handles exceptions thrown by any controller in the application.
 */

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles ResourceNotFoundException.
     * Accepts parameter ex The exception thrown.
     * Accepts parameter request The current web request.
     * returns A ResponseEntity with an error message and HTTP status NOT_FOUND.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        logger.warn("ResourceNotFoundException: "+ ex.getMessage() +" in request "+ request.getDescription(false));
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles MethodArgumentNotValidException for validation failures.
     * Accepts parameter ex The exception thrown by @Valid annotation.
     * Accepts parameter request The current web request.
     * returns A ResponseEntity with a map of field errors and HTTP status BAD_REQUEST.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        logger.warn("Validation Exception: "+ ex.getMessage() +" in request "+ request.getDescription(false));
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles authentication-related JWT exceptions.
     * Accepts parameter ex The JWT exception thrown.
     * Accepts parameter request The current web request.
     * return A ResponseEntity with an error message and HTTP status UNAUTHORIZED.
     */
    @ExceptionHandler({SignatureException.class, MalformedJwtException.class, ExpiredJwtException.class})
    public ResponseEntity<String> handleJwtAuthenticationException(RuntimeException ex, WebRequest request) {
        logger.warn("JWT Authentication Exception: "+ ex.getMessage() +" in request "+ request.getDescription(false));
        String errorMessage = "Invalid or expired JWT token. Please log in again.";
        if (ex instanceof SignatureException) {
            errorMessage = "Invalid JWT signature.";
        } else if (ex instanceof ExpiredJwtException) {
            errorMessage = "JWT token has expired.";
        } else if (ex instanceof MalformedJwtException) {
            errorMessage = "Malformed JWT token.";
        }
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles all other uncaught exceptions.
     * Accepts parameter ex The exception thrown.
     * Accepts parameter request The current web request.
     * returns A ResponseEntity with a generic error message and HTTP status INTERNAL_SERVER_ERROR.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("An unexpected error occurred: "+ ex.getMessage() +" in request "+ request.getDescription(false), ex);
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

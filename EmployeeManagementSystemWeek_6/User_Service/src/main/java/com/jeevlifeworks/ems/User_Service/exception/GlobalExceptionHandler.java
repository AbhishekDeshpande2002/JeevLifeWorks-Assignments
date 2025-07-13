package com.jeevlifeworks.ems.User_Service.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Global exception handler for the User Service.
 * This class provides centralized exception handling for all controllers.
 */

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	/**
     * Handles ResourceNotFoundException.
     * Accepts parameter ex, The exception thrown.
     * Accepts parameter request, The current web request.
     * returns a ResponseEntity with an error message and HTTP status NOT_FOUND.
     */
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        logger.error("ResourceNotFoundException: "+ ex.getMessage() +" in request "+ request.getDescription(false));
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
	
	
	/**
     * Handles UserAlreadyExistsException.
     * Accepts parameter ex, The exception thrown.
     * Accepts parameter request, The current web request.
     * returns A ResponseEntity with an error message and HTTP status CONFLICT.
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex, WebRequest request) {
        logger.error("UserAlreadyExistsException: "+ ex.getMessage() +" in request " + request.getDescription(false));
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Handles BadCredentialsException
     * Accepts parameter ex The exception thrown.
     * Accepts parameter request The current web request.
     * returns a ResponseEntity with an error message and HTTP status UNAUTHORIZED.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        logger.warn("BadCredentialsException: "+ ex.getMessage() +" in request "+ request.getDescription(false));
        return new ResponseEntity<>("Invalid username or password.", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles MethodArgumentNotValidException for validation failures.
     * Accepts parameter ex, The exception thrown by valid annotation.
     * Accepts parameter request, The current web request.
     * returns a ResponseEntity with a map of field errors and HTTP status BAD_REQUEST.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        logger.warn("Validation Exception: "+ ex.getMessage() +" in request " + request.getDescription(false));
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other uncaught exceptions.
     * Accepts parameter ex The exception thrown.
     * Accepts parameter request The current web request.
     * returns a ResponseEntity with a generic error message and HTTP status INTERNAL_SERVER_ERROR.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("An unexpected error occurred: "+ ex.getMessage() +" in request " + request.getDescription(false), ex);
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

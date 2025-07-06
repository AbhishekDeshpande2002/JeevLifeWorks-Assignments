package com.jeevlifeworks.Student_Management_System_SpringBoot.exception;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

/**
 * Global exception handler to manage and format different types of exceptions
 * thrown throughout the application into structured HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	/**
     * Handles StudentNotFoundException and returns a 404 NOT FOUND response.
     *
     * e the exception thrown when a student is not found
     * return a map containing the error message
     */
	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleNotFound(StudentNotFoundException e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Map.of("error",e.getMessage()));
	}
	
	
	/**
     * Handles validation failures on method arguments.
     * Returns a 400 BAD REQUEST with field-specific error messages.
     *
     * e MethodArgumentNotValidException thrown when validation on an argument fails
     * return a map of field names to their respective error messages
     */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException e){
		Map<String, String> errors = e.getBindingResult().getFieldErrors()
				.stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
		
		return ResponseEntity.badRequest().body(errors);
	}
	
	
	 /**
     * Handles constraint violations.
     * Returns a 400 BAD REQUEST with the validation message.
     *
     * e ConstraintViolationException thrown when a constraint is violated
     * return a map containing the error message
     */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Map<String, String>> handleConstraint(ConstraintViolationException e){
		return ResponseEntity.badRequest().body(Map.of("Error",e.getMessage()));
	}
	
	
	/**
     * Handles any other unexpected exceptions.
     * Returns a 500 INTERNAL SERVER ERROR with the exception message.
     *
     * e any uncaught Exception
     * return a map containing the error message
     */
	public ResponseEntity<Map<String, String>> handleOther(Exception e){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of("Error",e.getMessage()));
	}
}

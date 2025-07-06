package com.jeevlifeworks.Student_Management_System_SpringBoot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception thrown when a requested student is not found in the system.
 * 
 * Annotated with @ResponseStatus to automatically return HTTP 404 Not Found
 * when this exception is thrown from a controller.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException{

	/**
     * Constructs a new StudentNotFoundException with a detailed message.
     */
	public StudentNotFoundException(String message) {
		super(message);
	}

	/**
     * Constructs a new StudentNotFoundException with a detailed message
     * and a cause for exception chaining.
     */
	public StudentNotFoundException(String message, Throwable cause) {
		super(message,cause);
	}
}

package com.jeevlifeworks.SpringSecurity.exception;

/**
 * Custom exception used when a requested resource (e.g., user, employee, etc.)
 * is not found in the system.
 * 
 * This exception extends RuntimeException so it is unchecked, and can be thrown
 * without needing to be declared in method signatures.
 */
public class ResourceNotFoundException extends RuntimeException{
	
	/**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * accepts parameter message the detail message explaining what resource was not found
     */
	public ResourceNotFoundException(String message) {
        super(message);// Pass the message to the superclass RuntimeException
    }
}

package com.jeevlifeworks.ems.User_Service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception for when a resource is not found.
 * Maps to HTTP 404 Not Found.
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	
	/**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     * accepts parameter message.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

package com.jeevlifeworks.ems.User_Service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException{
	
	
	/**
     * Constructs a new UserAlreadyExistsException with the specified detail message.
     * accepts parameter message.
     */
	public UserAlreadyExistsException(String message) {
        super(message);
    }
}

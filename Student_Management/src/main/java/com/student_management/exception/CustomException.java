package com.student_management.exception;


/*
 * A simple, unchecked exception used throughout the application to represent
 * known, expected failures — such as validation errors, missing data, or
 * business rule violations.
 */
public class CustomException extends RuntimeException{
	
	// Gives the human readable reason for the exception
	public CustomException(String exception) {
        super(exception);
    }
}

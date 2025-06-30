package com.Library.exception;

//  Custom runtime exception for the Library Management System.
public class CustomException extends RuntimeException{

	// Constructs a new CustomException with the specified detail message.
	public CustomException(String exception) {
        super(exception);
    }
}

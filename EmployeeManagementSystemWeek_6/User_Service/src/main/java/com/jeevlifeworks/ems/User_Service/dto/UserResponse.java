package com.jeevlifeworks.ems.User_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data Transfer Object for user response, typically after login or profile retrieval.
 * Contains user details and the JWT token.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
	
	/**
     * The unique identifier of the user.
     */
	private Long id;
	
	/**
     * The username associated with the user account.
     */
    private String username;
    
    /**
     * The email address of the user.
     */
    private String email;
    
    /**
     * The JSON Web Token (JWT) issued after successful authentication.
     * This token will be used in further requests to access secured endpoints.
     */
    private String token; // JWT token
}

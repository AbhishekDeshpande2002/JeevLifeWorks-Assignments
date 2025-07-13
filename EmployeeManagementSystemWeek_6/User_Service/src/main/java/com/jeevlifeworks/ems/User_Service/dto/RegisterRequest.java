package com.jeevlifeworks.ems.User_Service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object for user registration requests.
 * Includes validation constraints for input fields.
 */

@Data
public class RegisterRequest {
	
	/**
     * The username chosen by the user.
     * - Must not be blank.
     * - Must be between 3 and 50 characters long.
     */
	@NotBlank(message = "Username cannot be empty")
	@Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
	private String username;
	
	/**
     * The password set by the user for authentication.
     * - Must not be blank.
     * - Must be at least 6 characters long.
     */
	@NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters long")
	private String password;
	
	/**
     * The user's email address.
     * - Must not be blank.
     * - Must be in a valid email format.
     */
	@NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
	private String email;
	
}

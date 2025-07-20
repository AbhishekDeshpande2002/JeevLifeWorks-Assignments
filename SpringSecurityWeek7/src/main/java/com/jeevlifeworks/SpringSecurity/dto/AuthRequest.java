package com.jeevlifeworks.SpringSecurity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO representing an authentication request.
 * Contains credentials for login: username and password.
 */

@Data
public class AuthRequest {
	
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
}

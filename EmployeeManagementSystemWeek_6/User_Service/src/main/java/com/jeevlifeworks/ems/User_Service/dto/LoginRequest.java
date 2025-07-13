package com.jeevlifeworks.ems.User_Service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


/**
 * Data Transfer Object for user login requests.
 * Includes validation constraints for input fields.
 */

@Data
public class LoginRequest {
	
	/**
     * The username entered by the user for authentication.
     * - Must not be blank.
     */
	@NotBlank(message = "Username cannot be empty")
    private String username;

	/**
     * The password entered by the user for authentication.
     * - Must not be blank.
     */
    @NotBlank(message = "Password cannot be empty")
    private String password;
}

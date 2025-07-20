package com.jeevlifeworks.SpringSecurity.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * User Entity class 
 * Maps to users table in the database
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
public class User {
	
	/*
	 * Id is a unique primary key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
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
	 * The role set by user 
	 * - It should be either ROLE_USER or ROLE_ADMIN
	 */
	@Enumerated(EnumType.STRING) // Stores the enum value as a String in the DB (e.g., "ADMIN", "USER")
	private Role role; // Enum representing the user's role (e.g., ADMIN, USER)
}

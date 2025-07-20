package com.jeevlifeworks.SpringSecurity.entities;

/**
 * Enum representing different user roles in the system.
 * Used for role-based access control in the System.
 * Enum is used because it works well with spring security and with @Enumerated(EnumType.STRING) annotation
 */

public enum Role {
	
	ROLE_USER, // Standard user role with limited access
	ROLE_ADMIN // Admin role with all the access in the system
}

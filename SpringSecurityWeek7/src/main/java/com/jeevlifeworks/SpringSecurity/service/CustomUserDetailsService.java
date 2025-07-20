package com.jeevlifeworks.SpringSecurity.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jeevlifeworks.SpringSecurity.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Custom implementation of Spring Security's UserDetailsService.
 * Used by Spring Security to load user-specific data during authentication.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
	
	// Repository to fetch user data from the database
	private final UserRepository userRepository; 

	/**
     * Loads the user details by username for authentication.
     *
     * Accepts parameter username, The username to look up in the database
     * return UserDetails object containing username, password, and roles
     * throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	// Fetch user from DB; throw exception if not found
    	com.jeevlifeworks.SpringSecurity.entities.User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
    	// Create a Spring Security UserDetails object from our custom User entity
    	return User.withUsername(user.getUsername())
                .password(user.getPassword())
             // Extract role name from enum like "ROLE_ADMIN" → "ADMIN"
                .roles(user.getRole().name().substring(5))
                .build();
    }
}

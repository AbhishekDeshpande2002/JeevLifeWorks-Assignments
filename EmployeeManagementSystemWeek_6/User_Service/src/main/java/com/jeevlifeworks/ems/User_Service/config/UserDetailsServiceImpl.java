package com.jeevlifeworks.ems.User_Service.config;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jeevlifeworks.ems.User_Service.repository.UserRepository;

/**
 * UserDetailsServiceImpl is a custom implementation of Spring Security's UserDetailsService.
 * 
 * It is responsible for loading user-specific data from the database during authentication.
 * This class integrates with Spring Security's authentication flow and provides UserDetails based on the username.
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	private final UserRepository userRepository;

    /**
     * Constructs a new UserDetailsServiceImpl.
     * Accepts parameter userRepository The repository for accessing user data.
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Locates the user based on the username.
     * Accepts parameter username The username identifying the user whose data is required.
     * returns A fully populated user record (UserDetails).
     * throws UsernameNotFoundException if the user could not be found or the user has no GrantedAuthority.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User
                		(user.getUsername(), user.getPassword(), new ArrayList<>())) // Provide empty list for authorities/roles if not used
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}

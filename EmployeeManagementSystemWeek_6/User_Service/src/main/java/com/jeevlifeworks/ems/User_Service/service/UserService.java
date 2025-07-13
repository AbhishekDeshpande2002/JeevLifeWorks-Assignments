package com.jeevlifeworks.ems.User_Service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeevlifeworks.ems.User_Service.config.JwtService;
import com.jeevlifeworks.ems.User_Service.dto.LoginRequest;
import com.jeevlifeworks.ems.User_Service.dto.RegisterRequest;
import com.jeevlifeworks.ems.User_Service.dto.UserResponse;
import com.jeevlifeworks.ems.User_Service.entity.User;
import com.jeevlifeworks.ems.User_Service.exception.ResourceNotFoundException;
import com.jeevlifeworks.ems.User_Service.exception.UserAlreadyExistsException;
import com.jeevlifeworks.ems.User_Service.repository.UserRepository;

/**
 * UserService handles core business logic related to user management.
 * this service class Registers new users
 * this service class authenticates users and generating JWT tokens
 * Retrieves user profile information
 */

@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Constructs a new UserService.
     * Accepts parameter userRepository The repository for user data access.
     * Accepts parameter passwordEncoder The password encoder for hashing passwords.
     * Accepts parameter jwtService The JWT service for token generation.
     * Accepts parameter authenticationManager The authentication manager for user authentication.
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Registers a new user.
     * Accepts parameter request The registration request containing user details.
     * returns A UserResponse object with registered user's details and a JWT token.
     * throws UserAlreadyExistsException if a user with the given username or email already exists.
     */
    @Transactional
    public UserResponse registerUser(RegisterRequest request) {
        logger.debug("Attempting to register new user: {}", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {
            logger.warn("Registration failed: Username "+request.getUsername() +" already exists." );
            throw new UserAlreadyExistsException("Username already exists.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            logger.warn("Registration failed: Email "+ request.getEmail() +" already exists.");
            throw new UserAlreadyExistsException("Email already exists.");
        }

     // Create and save new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Hash the password
        user.setEmail(request.getEmail());

        User savedUser = userRepository.save(user);
        logger.info("User "+savedUser.getUsername() +" registered successfully with ID: "+ savedUser.getId());

     // Generate JWT token
        String jwtToken = jwtService.generateToken(new org.springframework.security.core.userdetails.User(
                savedUser.getUsername(), savedUser.getPassword(), new java.util.ArrayList<>()));

        return new UserResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(), jwtToken);
    }

    /**
     * Logs in a user and generates a JWT token upon successful authentication.
     * Accepts parameter request The login request containing username and password.
     * returns A UserResponse object with logged-in user's details and a JWT token.
     * throws org.springframework.security.authentication.BadCredentialsException if authentication fails.
     */
    public UserResponse loginUser(LoginRequest request) {
        logger.debug("Attempting to log in user: {}", request.getUsername());
        
     // Authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        
        // If authentication is successful, proceed to generate token
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found after successful authentication. This should not happen."));

     // Generate JWT token
        String jwtToken = jwtService.generateToken(new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), new java.util.ArrayList<>()));
        logger.info("User "+ user.getUsername() +" logged in successfully. Token generated.");
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), jwtToken);
    }

    /**
     * Retrieves the profile information for a given username.
     * Accepts parameter username The username of the user whose profile is to be retrieved.
     * returns A UserResponse object containing the user's details.
     * throws ResourceNotFoundException if the user is not found.
     */
    public UserResponse getUserProfile(String username) {
        logger.debug("Retrieving profile for user: "+ username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        logger.info("Profile retrieved for user: "+username);
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), null); // No token needed for profile retrieval
    }
}

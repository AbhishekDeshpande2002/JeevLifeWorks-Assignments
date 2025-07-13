package com.jeevlifeworks.ems.User_Service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig sets up the security configuration for the User Service.
 * 
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	private final JwtAuthFilter jwtAuthFilter;
	private final UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * Constructs a new SecurityConfig.
     * Accepts parameter jwtAuthFilter The JWT authentication filter.
     * Accepts parameter userDetailsServiceImpl, Custom implementation for loading user details.
     */
    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jwtAuthFilter = jwtAuthFilter;
        //this.userRepository = userRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    /**
     * Configures the security filter chain.
     * Defines access rules for different endpoints and integrates JWT filter.
     * Accepts parameter http The HttpSecurity object to configure.
     * returns The configured SecurityFilterChain.
     * throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // CSRF is not needed for token-based authentication
            .authorizeHttpRequests()
                .requestMatchers("/users/register", "/users/login").permitAll() // Permit access to registration and login
                .anyRequest().authenticated() // All other requests require authentication
            .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless sessions for JWT
            .and()
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before UsernamePasswordAuthenticationFilter
        return http.build();
    }

    /**
     * Provides a UserDetailsService bean.
     * Loads user details from the UserRepository.
     * returns An implementation of UserDetailsService.
     */
    /*@Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new java.util.ArrayList<>())) // No specific roles for now
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
    */
    
    /**
     * Provides an AuthenticationProvider bean.
     * Uses DaoAuthenticationProvider with BCrypt password encoder.
     * returns An AuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServiceImpl); // Custom user detail loading
        authProvider.setPasswordEncoder(passwordEncoder()); // Use BCrypt for password encoding
        return authProvider;
    }

    /**
     * Provides an AuthenticationManager bean.
     * Accepts parameter config The AuthenticationConfiguration.
     * returns The AuthenticationManager.
     * throws Exception if an error occurs during configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Provides a BCryptPasswordEncoder bean for password hashing.
     * returns A PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

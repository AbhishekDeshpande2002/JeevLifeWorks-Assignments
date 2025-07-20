package com.jeevlifeworks.SpringSecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jeevlifeworks.SpringSecurity.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

/**
 * Main Spring Security configuration class.
 * Sets up JWT-based authentication, authorization rules, and password encoding.
 */

@Configuration
@EnableWebSecurity // Enables Spring Security's web security support
@EnableMethodSecurity // Enables method-level security annotations like @PreAuthorize
@RequiredArgsConstructor
public class SecurityConfig {
	
	// Custom service to load user details from DB
	private final CustomUserDetailsService userDetailsService;
	
	// Custom filter to validate JWT tokens before authenticating requests
    private final JwtAuthFilter jwtAuthFilter;

    
    /**
     * Configures the security filter chain.
     * - Disables CSRF (for stateless APIs)
     * - Permits access to /api/auth/** endpoints
     * - Secures all other endpoints
     * - Adds JWT filter before Spring's default authentication filter
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disable CSRF for REST API
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // Public endpoints for login/register
                .requestMatchers("/api/profile").hasAnyRole("ADMIN", "USER") // Accessible by both admin and user roles
                //.requestMatchers("/api/employees/**").hasRole("ADMIN")
                .anyRequest().authenticated()) // All other endpoints require authentication
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider()) // Use custom authentication provider
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before Spring's auth filter

        return http.build(); // Build the filter chain
    }

    
    /**
     * Configures the authentication provider to use:
     * - CustomUserDetailsService to load users
     * - BCrypt for password encoding
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // Set custom user details service
        provider.setPasswordEncoder(new BCryptPasswordEncoder()); // Set password encoder
        return provider;
    }

    
    /**
     * Exposes the AuthenticationManager bean used to authenticate user credentials.
     * Needed for manual authentication (e.g., in login controller).
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder())
                .and().build();
    }
    
    
    /**
     * Configures and exposes a BCryptPasswordEncoder bean to hash and verify passwords securely.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Strong hashing algorithm
    }
}

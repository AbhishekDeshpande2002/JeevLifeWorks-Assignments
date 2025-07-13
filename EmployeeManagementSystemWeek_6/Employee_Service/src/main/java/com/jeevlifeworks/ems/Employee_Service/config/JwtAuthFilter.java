package com.jeevlifeworks.ems.Employee_Service.config;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JWT Authentication Filter for validating tokens in incoming requests.
 * It intercepts each request once (extends OncePerRequestFilter).
 * It extracts and validates the JWT from the Authorization header.
 * It sets up the Spring Security context if the token is valid.
 * 
 * Used primarily in the Employee Service to ensure secure access to protected endpoints.
 */

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    private final JwtService jwtService;

    /**
     * Constructor-based dependency injection for JwtAuthFilter.
     * Accepts parameter jwtService The JWT service for token validation.
     */
    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * Performs filtering for each request.
     * Validates JWT token from the Authorization header and sets the security context.
     * Accepts parameter request The HTTP servlet request.
     * Accepts parameter response The HTTP servlet response.
     * Accepts parameter filterChain The filter chain.
     * throws ServletException if a servlet-specific error occurs.
     * throws IOException if an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

     // Check if Authorization header is present and starts with Bearer
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.debug("No JWT token found in request or invalid format.");
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);// Extract token
        try {
        	// Validate the JWT
            if (jwtService.isTokenValid(jwt)) {
            	// Extract username from the token
                username = jwtService.extractUsername(jwt);
             // Create a simple UserDetails object (no roles or password needed)
                UserDetails userDetails = new User(username, "", new ArrayList<>()); // No password or roles needed for validation
             // Set authentication in the security context
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.info("Successfully validated JWT for user: "+ username);
            } else {
                logger.warn("Invalid JWT token for request: "+ request.getRequestURI());
            }
        } catch (Exception e) {
            logger.error("Error processing JWT token: "+ e.getMessage());
         // Send error response when token is invalid/expired
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired JWT token.");
            return;
        }
     // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}

package com.jeevlifeworks.SpringSecurity.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jeevlifeworks.SpringSecurity.service.CustomUserDetailsService;
import com.jeevlifeworks.SpringSecurity.security.JwtAuthFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * A custom filter that intercepts each HTTP request once,
 * extracts the JWT token, validates it, and sets the Spring Security context.
 */

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
	
	// Service to handle JWT token operations (extract username, validate token, etc.)
	private final JwtService jwtService;
	// Custom implementation of UserDetailsService to load user from DB
	private final CustomUserDetailsService userDetailsService;

	/**
     * Filter logic that runs once per request.
     * - Extracts JWT from Authorization header.
     * - Validates token and sets authentication in the context if valid.
     */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		
		// Get the Authorization header from the incoming request
		final String authHeader = request.getHeader("Authorization");
		final String token;
		final String username;
		
		// Check if the header is present and starts with "Bearer "
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			logger.debug("No JWT token found in request or invalid format.");
			filterChain.doFilter(request, response);
			return;
		}

		// Extract token by removing "Bearer " prefix
		token = authHeader.substring(7);
		
		try {
			// Extract username from the token
            username = jwtService.extractUsername(token);
        } catch (Exception e) {
            logger.warn("JWT token extraction failed: "+ e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

		// If username was successfully extracted and no authentication is set yet
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// Load user details from the database
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
			// Validate the token against the user details
			if (jwtService.isTokenValid(token, userDetails)) {
				// Create authentication token and set it in the security context
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities()
						);
				// Set authentication in security context
				SecurityContextHolder.getContext().setAuthentication(authToken);
				logger.info("Successfully authenticated user: "+ username);
			}
			else {
                logger.warn("JWT token is not valid for user: "+ username);
            }
		}
		// Continue the filter chain
		filterChain.doFilter(request, response);
	}
	
}

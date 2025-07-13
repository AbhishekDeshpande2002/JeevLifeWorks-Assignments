package com.jeevlifeworks.ems.User_Service.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JwtAuthFilter is a custom security filter that intercepts HTTP requests to:
 *  Extract the JWT from the Authorization header.
 *  Validate the token.
 *  Authenticate the user if the token is valid.
 *
 * This filter runs once per request and sets the SecurityContext if authentication is successful.
 */

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * Constructs a new JwtAuthFilter.
     * Accepts parameter jwtService, The JWT service for token operations.
     * Accepts parameter userDetailsService, The user details service to load user-specific data.
     */
    public JwtAuthFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jwtService = jwtService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    /**
     * Performs filtering for each request.
     * Validates JWT token from the Authorization header and sets the security context.
     * Accepts parameter request, The HTTP servlet request.
     * Accepts parameter response, The HTTP servlet response.
     * Accepts parameter filterChain, The filter chain to pass the request/response to the next filter.
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

     // Check if Authorization header is present and properly formatted
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.debug("No JWT token found in request or invalid format.");
            filterChain.doFilter(request, response);
            return;
        }

     // Extract JWT token from header
        jwt = authHeader.substring(7); // removes "Bearer " prefix.
        try {
            username = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            logger.warn("JWT token extraction failed: "+ e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

     // Authenticate the user if not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(username);
            
         // Validate the token against user details
            if (jwtService.isTokenValid(jwt, userDetails)) {
            	// Create authentication token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                
             // Attach request details
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
             // Set authentication in security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.info("Successfully authenticated user: "+ username);
            } else {
                logger.warn("JWT token is not valid for user: "+ username);
            }
        }
     // Continue filter chain
        filterChain.doFilter(request, response);
    }
}

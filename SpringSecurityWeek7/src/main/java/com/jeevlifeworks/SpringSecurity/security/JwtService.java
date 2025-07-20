package com.jeevlifeworks.SpringSecurity.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.jeevlifeworks.SpringSecurity.security.JwtService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * Service class responsible for generating, validating, and parsing JWT tokens.
 */

@Service
public class JwtService {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
	
	/**
     * Secret key used to sign the JWTs, injected from application.properties file.
     * Must be Base64 encoded.
     */
	@Value("${app.jwt.secret}")
	private String jwtSecret;
	
	/**
     * Token expiration duration in milliseconds, injected from application.properties file.
     */
	@Value("${app.jwt.expiration}")
	private long jwtExpirations;

	/**
     * Encodes the secret and returns an HMAC-SHA256 signing key.
     * return the signing key used for JWT signature
     */
    private Key getKey() {
    	 byte[] keyBytes = Base64.getEncoder().encode(jwtSecret.getBytes());
         return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a JWT token for the given user.
     * Includes the username and authorities as claims.
     * parameter userDetails the authenticated user's details
     * return a signed JWT token string
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // Set the subject (typically the username)
                .claim("authorities", userDetails.getAuthorities().toString()) // Add authorities as a custom claim
                .setIssuedAt(new Date()) // Token issue time
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirations)) // Token expiry time
                .signWith(getKey(), SignatureAlgorithm.HS256) // Sign the token using HS256 algorithm
                .compact(); // Build and serialize the JWT
    }

    
    /**
     * Extracts the username (subject) from the JWT token.
     * parameter token the JWT token
     * return the username stored in the token
     */
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
        		.setSigningKey(getKey()) // Set the key used to validate the signature
        		.build()
                .parseClaimsJws(token) // Parse the token
                .getBody()
                .getSubject(); // Return the subject (username)
    }

    /**
     * Validates the token by checking:
     * - if the username in the token matches the user
     * - if the token is not expired
     * parameter token the JWT token
     * parameter userDetails the user details to match against
     * return true if valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        //return extractUsername(token).equals(userDetails.getUsername()) && !isExpired(token);
    	
    	final String username = extractUsername(token);
        boolean isValid = (username.equals(userDetails.getUsername())) && !isExpired(token);
        if (!isValid) {
            logger.warn("Invalid JWT token for user: "+ username);
        }
        return isValid;
    }

    /**
     * Checks if the token has expired.
     * parameter token the JWT token
     * return true if the token is expired, false otherwise
     */
    private boolean isExpired(String token) {
        Date expiration = Jwts.parserBuilder().setSigningKey(getKey()).build()
                .parseClaimsJws(token).getBody().getExpiration(); // Extract expiration date
        return expiration.before(new Date()); // Compare with current time
    }
}

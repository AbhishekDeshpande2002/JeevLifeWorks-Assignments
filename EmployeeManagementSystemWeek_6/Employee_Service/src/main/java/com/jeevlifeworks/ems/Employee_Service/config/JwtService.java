package com.jeevlifeworks.ems.Employee_Service.config;

import java.security.Key;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Service class responsible for handling JWT operations
 *  Extracting claims and username
 *  Validating the token
 *  Decoding the secret key
 *
 * This is used in the Employee Service to validate tokens passed from the User Service.
 */

@Service
public class JwtService {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

	/**
     * Secret key used for signing and verifying JWT tokens.
     * The key is expected to be base64-encoded and configured in application.properties file.
     */
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    /**
     * Extracts the username (subject) from a JWT token.
     * Accepts parameter token The JWT token string.
     * returns The username extracted from the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token.
     * Accepts parameter token The JWT token string.
     * Accepts parameter claimsResolver A function to resolve the specific claim from the Claims object.
     * Accepts parameter <T> The type of the claim to be extracted.
     * returns The extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Validates if a JWT token is valid (not expired and signature is correct).
     * Accepts parameter token The JWT token string.
     * returns true if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token); // This will throw an exception if the token is invalid or expired
            logger.debug("JWT token is valid.");
            return true;
        } catch (Exception e) {
            logger.warn("JWT token validation failed: "+ e.getMessage());
            return false;
        }
    }

    /**
     * Extracts all claims from a JWT token.
     * Accepts parameter token The JWT token string.
     * returns The Claims object containing all claims from the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retrieves the signing key used for JWT validation.
     * Decodes the base64 encoded secret key.
     * returns The signing key.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

package com.jeevlifeworks.ems.User_Service.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * JwtService handles the creation, parsing, and validation of JSON Web Tokens (JWTs).
 * It generates JWT tokens with claims and expiration
 * It extracts claims (e.g., username, expiration) from tokens
 * Validate tokens against user information
 */

@Service
public class JwtService {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

	/**
     * Secret key used to sign the JWTs, injected from application.properties file.
     * Must be Base64 encoded.
     */
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    /**
     * Token expiration duration in milliseconds, injected from application.properties file.
     */
    @Value("${jwt.expiration}")
    private long JWT_EXPIRATION;

    /**
     * Extracts the username from a JWT token.
     * Accepts parameter token, The JWT token.
     * returns the username extracted from the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from a JWT token.
     * Accepts parameter token, The JWT token.
     * Accepts parameter claimsResolver, A function to extract the desired claim from the Claims object.
     * <T> The type of the claim.
     * returns the extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token for a given UserDetails.
     * Accepts parameter userDetails, The UserDetails object containing user information.
     * returns the generated JWT token.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with extra claims and UserDetails.
     * Accepts parameter extraClaims, Additional claims to include in the token.
     * Accepts parameter userDetails, The UserDetails object.
     * returns The generated JWT token.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        logger.info("Generating JWT token for user: {}", userDetails.getUsername());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates a JWT token against UserDetails.
     * Accepts parameter token, The JWT token to validate.
     * Accepts parameter userDetails, The UserDetails object to compare against.
     * returns True if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        boolean isValid = (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        if (!isValid) {
            logger.warn("Invalid JWT token for user: "+ username);
        }
        return isValid;
    }

    /**
     * Checks if a JWT token is expired.
     * Accepts parameter token, The JWT token.
     * returns True if the token is expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from a JWT token.
     * Accepts parameter token, The JWT token.
     * returns The expiration date.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from a JWT token.
     * Accepts parameter token, The JWT token.
     * returns All claims contained in the token.
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
     * Gets the signing key for JWT.
     * returns The signing key.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

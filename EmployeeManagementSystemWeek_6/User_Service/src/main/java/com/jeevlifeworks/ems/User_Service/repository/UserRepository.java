package com.jeevlifeworks.ems.User_Service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jeevlifeworks.ems.User_Service.entity.User;


/**
 * Repository interface for User entity.
 * Provides methods for database operations on User objects.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	/**
     * Finds a user by their username.
     * Accepts parameter username to search for.
     * returns an Optional containing the User if found, otherwise empty.
     */
	Optional<User> findByUsername(String username);
	
	/**
     * Checks if a user with the given username exists.
     * Accepts parameter username to check.
     * returns true if a user with the username exists, false otherwise.
     */
	boolean existsByUsername(String username);
	
	/**
     * Checks if a user with the given email exists.
     * Accepts parameter email to check.
     * returns true if a user with the email exists, false otherwise.
     */
	boolean existsByEmail(String email);
	
}

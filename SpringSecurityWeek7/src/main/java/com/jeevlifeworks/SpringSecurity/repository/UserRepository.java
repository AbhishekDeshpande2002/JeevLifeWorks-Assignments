package com.jeevlifeworks.SpringSecurity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jeevlifeworks.SpringSecurity.entities.User;

/**
 * Repository interface for User entity.
 * Provides built-in CRUD operations and a custom finder method.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	/**
     * Custom query method to find a user by username.
     * Returns an Optional<User> which is useful for handling user-not-found scenarios.
     *
     * Accepts parameter  username, the username to search for
     * return Optional containing the User if found, otherwise empty
     */
	Optional<User> findByUsername(String username);
}

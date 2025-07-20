package com.jeevlifeworks.SpringSecurity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jeevlifeworks.SpringSecurity.entities.Employee;

import jakarta.transaction.Transactional;

/**
 * Repository interface for Employee entity.
 * Extends JpaRepository to provide built-in CRUD operations and query methods.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	// Get all employees
	@Query(value = "SELECT * FROM employees", nativeQuery = true)
	List<Employee> findAllEmployees();

	// Update employee by ID
	@Modifying
	@Transactional
	@Query(value = "UPDATE employees SET name = :name, department = :department, salary = :salary WHERE id = :id", nativeQuery = true)
	int updateEmployeeById(Long id, String name, String department, Double salary);

	// Delete employee by ID
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM employees WHERE id = :id", nativeQuery = true)
	int deleteEmployeeById(Long id);
	
	// Find employee by ID
    @Query(value = "SELECT * FROM employees WHERE id = :id", nativeQuery = true)
    Optional<Employee> findEmployeeById(Long id);
}

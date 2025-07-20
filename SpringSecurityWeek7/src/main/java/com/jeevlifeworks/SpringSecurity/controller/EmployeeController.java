package com.jeevlifeworks.SpringSecurity.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeevlifeworks.SpringSecurity.entities.Employee;
import com.jeevlifeworks.SpringSecurity.service.EmployeeService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller to manage Employee-related operations.
 * Provides endpoints for CRUD operations on Employee entities.
 */
@RestController
@RequestMapping("/api/employees") // Base URL for employee endpoints
@RequiredArgsConstructor
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	private final EmployeeService employeeService;

	/**
     * Get a list of all employees.
     * Accessible by users with roles 'USER' or 'ADMIN'.
     *
     * return List of Employee entities wrapped in ResponseEntity
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Role-based access control
    public ResponseEntity<List<Employee>> getAll() {
    	logger.info("Fetching all employees");
    	List<Employee> employees = employeeService.getAll();
        logger.info("Total employees fetched: "+ employees.size());
        return ResponseEntity.ok(employees);
    }

    /**
     * Create a new employee.
     * Accessible only by users with role 'ADMIN'.
     *
     * Accepts parameter emp Employee object sent in the request body
     * return The saved Employee entity wrapped in ResponseEntity
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<Employee> create(@RequestBody Employee emp) {
    	logger.info("Creating new employee: "+ emp.getName());
    	Employee created = employeeService.save(emp);
        logger.info("Employee created with ID: "+ created.getId());
        return ResponseEntity.ok(created);

    }

    
    /**
     * Update an existing employee identified by ID.
     * Accessible only by users with role 'ADMIN'.
     *
     * Accepts parameter id  The ID of the employee to update
     * Accepts parameter emp The updated Employee object sent in the request body
     * return The updated Employee entity wrapped in ResponseEntity
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee emp) {
    	logger.info("Updating employee with ID: "+ id);
    	Employee updated = employeeService.update(id, emp);
        logger.info("Employee updated: "+ updated.getId());
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete an employee identified by ID.
     * Accessible only by users with role 'ADMIN'.
     *
     * Accepts parameter id The ID of the employee to delete
     * return ResponseEntity with HTTP 204 No Content status after successful deletion
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
    	logger.info("Deleting employee with ID: "+ id);
        employeeService.delete(id);
        logger.info("Employee deleted with ID: "+ id);
        return ResponseEntity.noContent().build();
    }
}

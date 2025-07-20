package com.jeevlifeworks.SpringSecurity.service;

import java.util.List;

import com.jeevlifeworks.SpringSecurity.entities.Employee;

/**
 * Service interface for handling business logic related to Employee operations.
 * Acts as an abstraction layer between the controller and repository.
 */
public interface EmployeeService {
	
	/**
     * Retrieves all employees from the database.
     * return List of all Employee entities
     */
	List<Employee> getAll();
	
	/**
     * Saves a new employee to the database.
     *
     * Accepts parameter emp, Employee object to be saved
     * return The saved Employee entity
     */
    Employee save(Employee emp);
    
    /**
     * Updates an existing employee identified by ID.
     *
     * Accepts parameter id  ID of the employee to update
     * Accepts parameter emp Updated Employee data
     * return The updated Employee entity
     */
    Employee update(Long id, Employee emp);
    
    /**
     * Deletes an employee from the database based on ID.
     *
     * Accepts parameter id ID of the employee to delete
     */
    void delete(Long id);
}

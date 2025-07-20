package com.jeevlifeworks.SpringSecurity.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import com.jeevlifeworks.SpringSecurity.entities.Employee;
import com.jeevlifeworks.SpringSecurity.exception.ResourceNotFoundException;
import com.jeevlifeworks.SpringSecurity.repository.EmployeeRepository;


import lombok.RequiredArgsConstructor;

/**
 * Implementation of the EmployeeService interface.
 * Handles business logic and interacts with the EmployeeRepository.
 */
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	private final EmployeeRepository repository;

	/**
	 * Retrieves all employees from the database.
	 *
	 * return List of all Employee entities
	 */
	@Override
	public List<Employee> getAll() {
		logger.info("Fetching all employees from database");
		// calls the custom repository method to retrieve employee details using native SQL query
		return repository.findAllEmployees();
	}

	/**
	 * Saves a new employee to the database.
	 *
	 * Accepts parameter emp, Employee object to be saved
	 * return The saved Employee entity
	 */
	@Override
	public Employee save(Employee emp) {
		logger.info("Saving new employee: "+ emp.getName());
		Employee saved = repository.save(emp);
		logger.info("Employee saved with ID: "+ saved.getId());
		return saved;
	}

	/**
	 * Updates an existing employee identified by ID.
	 *
	 * Accepts parameter id  ID of the employee to update
	 * Accepts parameter emp Updated Employee data
	 * return The updated Employee entity
	 */
	@Override
	public Employee update(Long id, Employee emp) {
		logger.info("Updating employee with ID: "+ id);

		// Call the custom repository method to update employee details using native SQL query
		int updatedCount = repository.updateEmployeeById(id, emp.getName(), emp.getDepartment(), emp.getSalary());

		// If no rows were updated, employee with given ID does not exist
		if (updatedCount == 0) {
			logger.warn("Employee with ID "+ id +" not found");
			throw new ResourceNotFoundException("Employee with id " + id + " not found");
		}

		// Fetch and return the updated employee entity, or throw if not found
		return repository.findEmployeeById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " not found after update"));
	}

	/**
	 * Deletes an employee from the database based on ID.
	 *
	 * Accepts parameter id ID of the employee to delete
	 */
	@Override
	public void delete(Long id) {
		logger.info("Deleting employee with ID: "+ id);

		// Call the custom repository method to delete employee by ID using native SQL query
		int deletedCount = repository.deleteEmployeeById(id);

		// If no rows were deleted, employee with given ID does not exist
		if (deletedCount == 0) {
			logger.warn("Attempted to delete non-existent employee with ID "+ id);
			throw new ResourceNotFoundException("Employee with id " + id + " not found");
		}

		logger.info("Employee with ID " + id + " deleted successfully");
	}
}

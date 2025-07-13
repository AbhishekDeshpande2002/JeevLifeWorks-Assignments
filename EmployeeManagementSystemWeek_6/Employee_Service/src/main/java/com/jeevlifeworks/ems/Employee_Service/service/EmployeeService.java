package com.jeevlifeworks.ems.Employee_Service.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeevlifeworks.ems.Employee_Service.dto.EmployeeDto;
import com.jeevlifeworks.ems.Employee_Service.entity.Employee;
import com.jeevlifeworks.ems.Employee_Service.exception.ResourceNotFoundException;
import com.jeevlifeworks.ems.Employee_Service.repository.EmployeeRepository;

/**
 * Service layer for handling business logic related to Employee operations.
 */

@Service
public class EmployeeService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    /**
     * Constructs a new EmployeeService.
     * Accepts parameter employeeRepository The repository for employee data access.
     */
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Retrieves all employees.
     * return A list of all Employee objects.
     */
    public List<Employee> getAllEmployees() {
        logger.debug("Retrieving all employees from repository.");
        return employeeRepository.findAll();
    }

    /**
     * Retrieves an employee by their ID.
     * Accepts parameter id The ID of the employee to retrieve.
     * return The Employee object.
     * throws ResourceNotFoundException if the employee with the given ID is not found.
     */
    public Employee getEmployeeById(Long id) {
        logger.debug("Retrieving employee with ID: {}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    /**
     * Creates a new employee.
     * Accepts parameter employeeDto The EmployeeDto containing the details of the employee to create.
     * return The newly created Employee object with its generated ID.
     */
    @Transactional
    public Employee createEmployee(EmployeeDto employeeDto) {
        logger.debug("Creating new employee with name: {}", employeeDto.getName());
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setDepartment(employeeDto.getDepartment());
        employee.setPosition(employeeDto.getPosition());
        employee.setSalary(employeeDto.getSalary());
        Employee savedEmployee = employeeRepository.save(employee);
        logger.info("Employee '{}' created successfully with ID: {}", savedEmployee.getName(), savedEmployee.getId());
        return savedEmployee;
    }

    /**
     * Updates an existing employee's details.
     * Accepts parameter id The ID of the employee to update.
     * Accepts parameter employeeDto The EmployeeDto containing the updated details.
     * return The updated Employee object.
     * throws ResourceNotFoundException if the employee with the given ID is not found.
     */
    @Transactional
    public Employee updateEmployee(Long id, EmployeeDto employeeDto) {
        logger.debug("Updating employee with ID: {}", id);
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        existingEmployee.setName(employeeDto.getName());
        existingEmployee.setDepartment(employeeDto.getDepartment());
        existingEmployee.setPosition(employeeDto.getPosition());
        existingEmployee.setSalary(employeeDto.getSalary());

        employeeRepository.update(existingEmployee);
        logger.info("Employee with ID: {} updated successfully.", id);
        return existingEmployee;
    }

    /**
     * Deletes an employee by their ID.
     * Accepts parameter id The ID of the employee to delete.
     * throws ResourceNotFoundException if the employee with the given ID is not found.
     */
    @Transactional
    public void deleteEmployee(Long id) {
        logger.debug("Attempting to delete employee with ID: {}", id);
        int rowsAffected = employeeRepository.deleteById(id);
        if (rowsAffected == 0) {
            logger.warn("Deletion failed: Employee with ID: {} not found.", id);
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        logger.info("Employee with ID: {} deleted successfully.", id);
    }

    /**
     * Groups all employees by their department.
     * Uses Java 8 Streams and Collectors.
     * return A Map where keys are department names (String) and values are Lists of Employees.
     */
    public Map<String, List<Employee>> groupEmployeesByDepartment() {
        logger.debug("Grouping employees by department using Java 8 Streams.");
        return employeeRepository.findAll().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }

    /**
     * Filters employees whose salary is greater than the specified minimum salary.
     * Uses Java 8 Streams.
     * Accepts parameter minSalary The minimum salary threshold.
     * return A List of Employee objects matching the criteria.
     */
    public List<Employee> filterEmployeesBySalary(Double minSalary) {
        logger.debug("Filtering employees by salary > "+ minSalary);
        return employeeRepository.findAll().stream()
                .filter(employee -> employee.getSalary() > minSalary)
                .collect(Collectors.toList());
    }

    /**
     * Sorts all employees by their salary in ascending order.
     * Uses Java 8 Streams and Comparator.
     * return A List of Employee objects sorted by salary.
     */
    public List<Employee> sortEmployeesBySalary() {
        logger.debug("Sorting employees by salary using Java 8 Streams.");
        return employeeRepository.findAll().stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary))
                .collect(Collectors.toList());
    }
}

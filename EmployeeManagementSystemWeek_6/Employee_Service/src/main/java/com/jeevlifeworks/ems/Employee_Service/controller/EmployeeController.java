package com.jeevlifeworks.ems.Employee_Service.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jeevlifeworks.ems.Employee_Service.dto.EmployeeDto;
import com.jeevlifeworks.ems.Employee_Service.entity.Employee;
import com.jeevlifeworks.ems.Employee_Service.service.EmployeeService;

import jakarta.validation.Valid;

/**
 * REST controller for handling all employee-related operations.
 */

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    /**
     * Constructs a new EmployeeController.
     * Accepts parameter employeeService The employee service to handle business logic.
     */
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * GET API to retrieve all employees.
     * return A ResponseEntity containing a list of all employees.
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        logger.info("Fetching all employees.");
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    /**
     * GET API to retrieve an employee by their ID.
     * Accepts parameter id The ID of the employee to retrieve.
     * return A ResponseEntity containing the employee, or NOT_FOUND if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        logger.info("Fetching employee with ID: {}", id);
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    /**
     * POST API to create a new employee.
     * Accepts parameter employeeDto The EmployeeDto containing the details of the employee to create.
     * return A ResponseEntity containing the created employee and HTTP status CREATED.
     */
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        logger.info("Creating new employee: {}", employeeDto.getName());
        Employee createdEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    /**
     * PUT API to update an existing employee.
     * Accepts parameter id The ID of the employee to update.
     * Accepts parameter employeeDto The EmployeeDto containing the updated details.
     * return A ResponseEntity containing the updated employee.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDto employeeDto) {
        logger.info("Updating employee with ID: {}", id);
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDto);
        return ResponseEntity.ok(updatedEmployee);
    }

    /**
     * DELETE API to delete an employee by their ID.
     * Accepts parameter id The ID of the employee to delete.
     * return A ResponseEntity with HTTP status NO_CONTENT if successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        logger.info("Deleting employee with ID: {}", id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET API to group employees by department.
     * return A ResponseEntity containing a map where keys are department names and values are lists of employees in that department.
     */
    @GetMapping("/group-by-department")
    public ResponseEntity<Map<String, List<Employee>>> groupEmployeesByDepartment() {
        logger.info("Grouping employees by department.");
        Map<String, List<Employee>> groupedEmployees = employeeService.groupEmployeesByDepartment();
        return ResponseEntity.ok(groupedEmployees);
    }

    /**
     * GET API to filter employees with a salary greater than a specified amount.
     * Accepts parameter minSalary The minimum salary to filter by.
     * return A ResponseEntity containing a list of employees with salary greater than minSalary.
     */
    @GetMapping("/filter-by-salary")
    public ResponseEntity<List<Employee>> filterEmployeesBySalary(@RequestParam Double minSalary) {
        logger.info("Filtering employees by salary greater than: {}", minSalary);
        List<Employee> filteredEmployees = employeeService.filterEmployeesBySalary(minSalary);
        return ResponseEntity.ok(filteredEmployees);
    }

    /**
     * GET API to sort employees by salary in ascending order.
     * return A ResponseEntity containing a list of employees sorted by salary.
     */
    @GetMapping("/sort-by-salary")
    public ResponseEntity<List<Employee>> sortEmployeesBySalary() {
        logger.info("Sorting employees by salary.");
        List<Employee> sortedEmployees = employeeService.sortEmployeesBySalary();
        return ResponseEntity.ok(sortedEmployees);
    }
}

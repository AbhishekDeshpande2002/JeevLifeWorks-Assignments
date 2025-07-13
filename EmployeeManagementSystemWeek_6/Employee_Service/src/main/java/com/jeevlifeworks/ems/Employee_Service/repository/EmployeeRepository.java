package com.jeevlifeworks.ems.Employee_Service.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.jeevlifeworks.ems.Employee_Service.entity.Employee;

/**
 * Repository class for performing CRUD operations on Employee entities using JDBC.
 */

@Repository
public class EmployeeRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeRepository.class);

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a new EmployeeRepository.
     * Accepts parameter jdbcTemplate The JdbcTemplate instance for database access.
     */
    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * RowMapper to map a ResultSet row to an Employee object.
     */
    private static final class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setId(rs.getLong("id"));
            employee.setName(rs.getString("name"));
            employee.setDepartment(rs.getString("department"));
            employee.setPosition(rs.getString("position"));
            employee.setSalary(rs.getDouble("salary"));
            return employee;
        }
    }

    /**
     * Finds all employees in the database.
     * return A list of all employees.
     */
    public List<Employee> findAll() {
        logger.debug("Fetching all employees from database.");
        return jdbcTemplate.query("SELECT id, name, department, position, salary FROM employees", new EmployeeRowMapper());
    }

    /**
     * Finds an employee by their ID.
     * Accepts parameter id The ID of the employee to find.
     * return An Optional containing the Employee if found, otherwise empty.
     */
    public Optional<Employee> findById(Long id) {
        logger.debug("Fetching employee with ID: "+ id);
        try {
            Employee employee = jdbcTemplate.queryForObject(
                    "SELECT id, name, department, position, salary FROM employees WHERE id = ?",
                    new EmployeeRowMapper(), id);
            return Optional.ofNullable(employee);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            logger.warn("Employee with ID:"+ id +"not found.");
            return Optional.empty();
        }
    }

    /**
     * Saves a new employee to the database.
     * Accepts parameter employee The employee object to save.
     * return The saved employee with the generated ID.
     */
    public Employee save(Employee employee) {
        logger.debug("Saving new employee: "+ employee.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO employees (name, department, position, salary) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getDepartment());
            ps.setString(3, employee.getPosition());
            ps.setDouble(4, employee.getSalary());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;
        if (generatedId != null) {
            employee.setId(generatedId);
            logger.info("Employee "+ employee.getName() +" saved with ID: "+ generatedId);
        } else {
            logger.error("Failed to retrieve generated ID for new employee: "+ employee.getName());
        }
        return employee;
    }

    /**
     * Updates an existing employee's details in the database.
     * Accepts parameter employee The employee object with updated details.
     * return The number of rows affected (should be 1 if successful).
     */
    public int update(Employee employee) {
        logger.debug("Updating employee with ID: {}", employee.getId());
        int rowsAffected = jdbcTemplate.update(
                "UPDATE employees SET name = ?, department = ?, position = ?, salary = ? WHERE id = ?",
                employee.getName(), employee.getDepartment(), employee.getPosition(), employee.getSalary(), employee.getId());
        if (rowsAffected > 0) {
            logger.info("Employee with ID: "+ employee.getId() +" updated successfully.");
        } else {
            logger.warn("No employee found with ID: "+ employee.getId()+" to update.");
        }
        return rowsAffected;
    }

    /**
     * Deletes an employee from the database by their ID.
     * Accepts parameter id The ID of the employee to delete.
     * return The number of rows affected (should be 1 if successful).
     */
    public int deleteById(Long id) {
        logger.debug("Deleting employee with ID: "+ id);
        int rowsAffected = jdbcTemplate.update("DELETE FROM employees WHERE id = ?", id);
        if (rowsAffected > 0) {
            logger.info("Employee with ID: "+ id +"deleted successfully.");
        } else {
            logger.warn("No employee found with ID: "+ id +" to delete.");
        }
        return rowsAffected;
    }
}

package com.jeevlifeworks.ems.Employee_Service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for transferring employee data between layers.
 * This class is used in API requests and responses for employee operations.
 * It includes validation constraints to ensure data integrity before processing.
 */

@Data
public class EmployeeDto {
	
	/**
     * Employee ID.
     * Included mainly for update operations; may be null for create requests.
     */
	private Long id; // Included for update operations

	/**
     * Name of the employee.
     * Cannot be blank and must be between 2 and 100 characters.
     */
    @NotBlank(message = "Employee name cannot be empty")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    /**
     * Department to which the employee belongs.
     * Cannot be blank and must be between 2 and 100 characters.
     */
    @NotBlank(message = "Department cannot be empty")
    @Size(min = 2, max = 100, message = "Department must be between 2 and 100 characters")
    private String department;

    /**
     * Position or role of the employee.
     * Cannot be blank and must be between 2 and 100 characters.
     */
    @NotBlank(message = "Position cannot be empty")
    @Size(min = 2, max = 100, message = "Position must be between 2 and 100 characters")
    private String position;

    /**
     * Salary of the employee.
     * Must not be null and must be a non-negative value.
     */
    @NotNull(message = "Salary cannot be null")
    @Min(value = 0, message = "Salary must be non-negative")
    private Double salary;
}

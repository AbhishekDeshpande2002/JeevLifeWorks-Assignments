package com.jeevlifeworks.SpringSecurity.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Employee Entity class 
 * Maps to employees table in the database
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee {
	
	/*
	 * Id is a unique primary key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
     * The employee name chosen by the user.
     * - Must not be blank.
     * - Must be between 3 and 20 characters long.
     */
	@NotBlank(message = "Name cannot be empty")
	@Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
	private String name;
	
	/**
     * The employee department chosen by the user.
     * - Must not be blank.
     * - Must be between 3 and 20 characters long.
     */
	@NotBlank(message = "Department cannot be empty")
	@Size(min = 3, max = 20, message = "Department must be between 3 and 20 characters")
	private String department;
	

	/**
     * The employee salary chosen by the user.
     * - Must not be null.
     * - Must be between a positive value.
     */
	@NotNull(message = "Salary cannot be null")
    @Min(value = 0, message = "Salary must be non-negative")
	private Double salary;
	
}

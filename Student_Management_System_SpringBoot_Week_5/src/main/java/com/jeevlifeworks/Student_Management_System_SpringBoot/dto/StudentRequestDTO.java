package com.jeevlifeworks.Student_Management_System_SpringBoot.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for creating or updating a Student.
 * Carries validated data from client requests to the service layer.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDTO {
	
	@NotBlank(message = "Name cannot be empty")
	private String name;
	
	@Min(value = 1, message = "Age must be positive")
	private int age;
	
	@NotBlank(message = "Grade cannot be empty")
    @Pattern(regexp = "^[A-D][+-]?$", message = "Grade must be like A+, B, C-, D")
	private String grade;
	
	private String address;
}

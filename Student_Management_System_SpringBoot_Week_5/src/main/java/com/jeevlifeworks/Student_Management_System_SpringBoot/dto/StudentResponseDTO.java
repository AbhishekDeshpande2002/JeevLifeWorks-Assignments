package com.jeevlifeworks.Student_Management_System_SpringBoot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for sending student data in API responses.
 * Represents the structure of student data returned to the client.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO {
	
	private Long id;
    private String name;
    private int age;
    private String grade;
    private String address;
}

package com.jeevlifeworks.Student_Management_System_SpringBoot.service;

import java.util.List;

import com.jeevlifeworks.Student_Management_System_SpringBoot.dto.StudentRequestDTO;
import com.jeevlifeworks.Student_Management_System_SpringBoot.dto.StudentResponseDTO;

/**
 * Service interface for handling business logic related to student operations.
 * 
 * Declares methods for CRUD operations that are implemented in StudentServiceImpl.
 */
public interface StudentService {
	
	/*
	 * Adds a new student to the database
	 * accepts studentRequestDTO parameter containing student details 
	 */
	StudentResponseDTO addStudent(StudentRequestDTO studentRequestDTO);
	
	/*
	 * Deletes existing student from the database by Id
	 * accepts Id as a parameter
	 */
	void deleteStudent(Long id);
	
	/*
	 * Updates existing student in the database by Id
	 * accepts Id and studentRequestDTO parameter containing student details
	 */
	StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDTO);
	
	/*
	 * Retrieves student by Id
	 * Accepts Id as a parameter
	 */
	StudentResponseDTO getStudentById(Long id);
	
	/*
	 * Retrieves a list of all students from database
	 */
	List<StudentResponseDTO> getAllStudents();
}

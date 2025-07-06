package com.jeevlifeworks.Student_Management_System_SpringBoot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeevlifeworks.Student_Management_System_SpringBoot.dto.StudentRequestDTO;
import com.jeevlifeworks.Student_Management_System_SpringBoot.dto.StudentResponseDTO;
import com.jeevlifeworks.Student_Management_System_SpringBoot.service.StudentService;

import jakarta.validation.Valid;


/**
 * REST Controller to handle all student-related HTTP requests.
 * Provides endpoints for CRUD operations (Create, Retrieve, Update, Delete).
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
	StudentService service;
	
	/*
	 * POST API for add student request.
	 * communicates with service layer and returns the response
	 * it accepts validated student credentials
	 */
	@PostMapping
	public ResponseEntity<StudentResponseDTO> addStudent(@Validated @RequestBody StudentRequestDTO studentRequestDTO){
		LOGGER.info("Received request to add student with name: "+ studentRequestDTO.getName());
		
		StudentResponseDTO newStudent = service.addStudent(studentRequestDTO);
		return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
	}
	
	/*
	 * DELETE API for delete student request
	 * communicates with service layer
	 * It accepts id as a Path Variable(parameter)
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable Long id){
		LOGGER.info("Received request to delete student with Id: "+id);
		
		service.deleteStudent(id);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	/*
	 * PUT API for update student request
	 * communicates with service layer and returns the response
	 * It accepts id as a Path Variable(parameter) and validated student credentials
	 */
	@PutMapping("/{id}")
	public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long id, @Validated @RequestBody StudentRequestDTO studentRequestDTO){
		LOGGER.info("Received request to update student by Id: "+id);
		
		StudentResponseDTO updatedStudent = service.updateStudent(id, studentRequestDTO);
		
		return new ResponseEntity<>(updatedStudent,HttpStatus.OK);
	}
	
	/*
	 * GET API for retrieve student by id request
	 * communicates with service layer and returns the response
	 * It accepts id as a Path Variable(parameter)
	 */
	@GetMapping("/{id}") 
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        LOGGER.info("Received request to get student with ID: {}", id);
        
        StudentResponseDTO student = service.getStudentById(id);
        
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
	
	/*
	 * GET API for retrieve all student request
	 * communicates with service layer and returns the response
	 */
	@GetMapping // Maps GET requests to /api/v1/students
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        LOGGER.info("Received request to get all students.");
        List<StudentResponseDTO> students = service.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
}

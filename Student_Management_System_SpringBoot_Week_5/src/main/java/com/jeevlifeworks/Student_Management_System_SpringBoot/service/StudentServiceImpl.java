package com.jeevlifeworks.Student_Management_System_SpringBoot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeevlifeworks.Student_Management_System_SpringBoot.dto.StudentRequestDTO;
import com.jeevlifeworks.Student_Management_System_SpringBoot.dto.StudentResponseDTO;
import com.jeevlifeworks.Student_Management_System_SpringBoot.exception.StudentNotFoundException;
import com.jeevlifeworks.Student_Management_System_SpringBoot.model.Student;
import com.jeevlifeworks.Student_Management_System_SpringBoot.repository.StudentRepository;

/*
 * Implementation StudentService interface 
 * Handles business logic for creating, retrieving, updating, and deleting students.
 * All database interactions are sent to StudentRepository.
 */
@Service
public class StudentServiceImpl implements StudentService{

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);
	
	/*
	 * JPA repository providing CRUD access to the student table.
	 * @Autowired is used for dependency injection
	 */
	@Autowired
	StudentRepository repository;
	
	/*
	 * Adds a new student to the database
	 * accepts studentRequestDTO parameter containing student details 
	 */
	@Override
	@Transactional
	public StudentResponseDTO addStudent(StudentRequestDTO studentRequestDTO) {
		
		LOGGER.info("Attempting to add new student", studentRequestDTO.getName());
		
		Student student = new Student();
		
		student.setName(studentRequestDTO.getName());
		student.setAge(studentRequestDTO.getAge());
		student.setGrade(studentRequestDTO.getGrade());
		student.setAddress(studentRequestDTO.getAddress());
		
		Student savedStudent = repository.save(student);
		LOGGER.info("Successfully added student with Id: ",savedStudent.getId());
		
		return new StudentResponseDTO(
				savedStudent.getId(),savedStudent.getName(),savedStudent.getAge(),savedStudent.getGrade(),savedStudent.getAddress());
	}

	/*
	 * Deletes existing student from the database by Id
	 * accepts Id as a parameter
	 */
	@Override
	@Transactional
	public void deleteStudent(Long id) {
		
		LOGGER.info("Attempting to delete student with Id: ",id);
		
		if(!repository.existsById(id)) {
			LOGGER.warn("Student with Id: "+id+" not found");
			throw new StudentNotFoundException("Student not found with Id: "+id);
		}
		
		repository.deleteById(id);
		LOGGER.info("Successfully deleted student with Id: "+id);
		
	}

	/*
	 * Updates existing student in the database by Id
	 * accepts Id and studentRequestDTO parameter containing student details
	 */
	@Override
	@Transactional
	public StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDTO) {
		
		LOGGER.info("Attempting to update student with Id: "+id);
		Student existingStudent = repository.findById(id)
				.orElseThrow(() -> {
					LOGGER.warn("Student with Id: "+id+" not found for update");
					throw new StudentNotFoundException("Student not found with ID: " + id);
				});
		
		existingStudent.setName(studentRequestDTO.getName());
		existingStudent.setAge(studentRequestDTO.getAge());
		existingStudent.setGrade(studentRequestDTO.getGrade());
		existingStudent.setAddress(studentRequestDTO.getAddress());
		
		Student updatedStudent = repository.save(existingStudent);
		
		LOGGER.info("Successfully updated student with Id: "+updatedStudent.getId());
		
		return new StudentResponseDTO(
				updatedStudent.getId(),updatedStudent.getName(),updatedStudent.getAge(),updatedStudent.getGrade(),updatedStudent.getAddress());
	}

	/*
	 * Retrieves student by Id
	 * Accepts Id as a parameter
	 */
	@Override
	public StudentResponseDTO getStudentById(Long id) {
		
		LOGGER.info("Attempting to retrieve student by Id: "+id);
		
		//Student student = new Student();
		
		Student existingStudent = repository.findById(id)
				.orElseThrow(() -> {
					LOGGER.warn("Student with Id: "+id+" not found");
					throw new StudentNotFoundException("Student not found with ID: " + id);
				});
		
		LOGGER.info("Successfully Retrieved Student with Id: "+id);
		
		return new StudentResponseDTO(existingStudent.getId(),existingStudent.getName(),existingStudent.getAge(),existingStudent.getGrade(),existingStudent.getAddress());
	}

	/*
	 * Retrieves a list of all students from database
	 */
	@Override
	public List<StudentResponseDTO> getAllStudents() {
		
		LOGGER.info("Attempting to retrieve all students");
		
		List<Student> students = repository.findAll();
		List<StudentResponseDTO> studentResponseDTOs = students.stream()
				.map(student -> new StudentResponseDTO(student.getId(),student.getName(),student.getAge(),student.getGrade(),student.getAddress()))
				.collect(Collectors.toList());
		
		LOGGER.info("Found "+ studentResponseDTOs.size() +" students");
		return studentResponseDTOs;
	}

	
}

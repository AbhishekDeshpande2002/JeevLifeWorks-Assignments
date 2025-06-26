package com.student_management.dao;

import java.util.List;

import com.student_management.model.Student;

/*
 * This Interface defines all the operations that can be performed
 * It Abstracts the persistence logic 
 */
public interface StudentDAO {
	
	// Inserts a new Student into database
	void addStudent(Student student);
	
	// Updates the existing student record in the database
	void updateStudent(Student student);
	
	// Delete a student record from the database based on Id
	void deleteStudent(int id);
	
	// Retrieves all the student records from the database 
	List<Student> getAllStudents();
	
	// Retrieves the student record by Id
    Student getStudentById(int id);
    
}

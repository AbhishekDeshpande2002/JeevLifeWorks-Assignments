package com.student_management.service;

import java.io.IOException;
import java.util.List;

import com.student_management.model.Student;

/*
 *  This is an Service Layer Interface that sits between UI and DAO layer 
 */
public interface StudentService {
	
	
	void registerStudent(Student student);
	
    void updateStudent(Student student);
    
    void removeStudent(int id);
    
    Student fetchStudent(int id);
    
    List<Student> fetchAllStudents();
    
    void exportStudentsToCSV(String filepath) throws IOException;
}

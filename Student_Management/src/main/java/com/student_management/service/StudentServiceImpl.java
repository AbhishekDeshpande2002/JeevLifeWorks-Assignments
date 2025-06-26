package com.student_management.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import com.student_management.dao.StudentDAO;
import com.student_management.dao.StudentDaoImpl;
import com.student_management.exception.CustomException;
import com.student_management.model.Student;

/*
 * This is a service layer implementation that defines business logic for managing student data
 * It as a middle layer that provides validation and performs operations 
 */

public class StudentServiceImpl implements StudentService{

	private final StudentDAO dao = new StudentDaoImpl();


	// registers a new student after accepting student object and validating input data 
	// throws exception if validation fails
	@Override
	public void registerStudent(Student student) {
		validate(student);
		dao.addStudent(student);
	}

	// updates the existing student after accepting student object and validating input data
	@Override
	public void updateStudent(Student student) {
		validate(student);
		dao.updateStudent(student);
	}

	// deletes the student by accepting id as parameter
	@Override
	public void removeStudent(int id) {
		dao.deleteStudent(id);
	}

	// retrievers the student by accepting id as parameter
	// throws exception if student with id not found 
	@Override
	public Student fetchStudent(int id) {
		Student student = dao.getStudentById(id);
		if (student == null) {
			throw new CustomException("Student with id " + id + " not found");
		}
		return student;
	}

	// retrieves all the student records from the database 
	@Override
	public List<Student> fetchAllStudents() {
		return dao.getAllStudents();
	}


	// helper method to validate the user input
	private void validate(Student s) {
		if (s.getStudentName() == null || s.getStudentName().trim().isEmpty()) {
			throw new CustomException("Name cannot be empty");
		}
		if (s.getStudentGrade() == null || s.getStudentGrade().trim().isEmpty()) {
			throw new CustomException("Invalid grade - enter grade (for eg. PASS or FAIL)");
		}
		if (s.getStudentAge() < 0|| s.getStudentAge() > 100) {
			throw new CustomException("Age must be between 0 and 100");
		}
		if (s.getStudentAddress() == null || s.getStudentAddress().trim().isEmpty()) {
			throw new CustomException("Address cannot be empty");
		}
	}

	// Export all the student records into a CSV file at a specified path
	@Override
	public void exportStudentsToCSV(String filePath) throws IOException {
		List<Student> studentsList = fetchAllStudents();
		if(studentsList.isEmpty()) {
			throw new CustomException("No Students found to Export.");
		}

		try (FileWriter writer = new FileWriter(filePath)) {
			writer.append("ID,Name,Age,Grade,Address\n");
			for (Student s : studentsList) {
				writer.append(String.format("%d,%s,%d,%s,%s\n", s.getStudentId(), s.getStudentName(), s.getStudentAge(), s.getStudentGrade(),s.getStudentAddress()));
			}
		}

	}

}

package com.student_management.model;

import java.util.Objects;

// Represents a Student Entity with properties like Id,Name,Age,Grade,Address
public class Student {

	private int studentId;
	private String studentName;
	private int studentAge;
	private String studentGrade;
	private String studentAddress;


	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getStudentAge() {
		return studentAge;
	}

	public void setStudentAge(int studentAge) {
		this.studentAge = studentAge;
	}

	public String getStudentGrade() {
		return studentGrade;
	}

	public void setStudentGrade(String studentGrade) {
		this.studentGrade = studentGrade;
	}

	public String getStudentAddress() {
		return studentAddress;
	}

	public void setStudentAddress(String studentAddress) {
		this.studentAddress = studentAddress;
	}

	public Student(int studentId, String studentName, int studentAge, String studentGrade, String studentAddress) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentAge = studentAge; 
		this.studentGrade = studentGrade;
		this.studentAddress = studentAddress;
	}

	// Constructor without Id for inserts done by database auto-increment
	public Student(String studentName, int studentAge, String studentGrade, String studentAddress) {
		this.studentName = studentName;
		this.studentAge = studentAge; 
		this.studentGrade = studentGrade;
		this.studentAddress = studentAddress;
	}

	public Student() {}

	@Override
	public String toString() {
		return String.format("Student{id=%d, name='%s', age='%d', grade='%s', address='%s'}", studentId,studentName,studentAge,studentGrade,studentAddress);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Student)) return false;
		Student student = (Student) o;
		return studentId == student.studentId && 
				studentAge == student.studentAge &&
				Objects.equals(studentName, student.studentName) &&
				Objects.equals(studentGrade, student.studentGrade) &&
				Objects.equals(studentAddress, student.studentAddress);
	}

	@Override
	public int hashCode() {
		return Objects.hash(studentId, studentName, studentAge, studentGrade, studentAddress);
	}
}

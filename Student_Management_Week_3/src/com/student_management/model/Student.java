package com.student_management.model;

import java.io.Serializable;
import java.util.Objects;

/*
 * This class implements  Serializable - allows saving instances to disk.
 * this class implements Comparable - enables natural sorting ordering 
 */
public class Student implements Serializable, Comparable<Student>{
	private static final long serialVersionUID = 1L;
	
	private int studentId;
	private String studentName;
	private int studentAge;
	private String studentGrade;
	private String studentAddress;
	
	
	public Student() {}
	
	
	public Student(int studentId, String studentName, int studentAge, String studentGrade, String studentAddress) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentAge = studentAge;
		this.studentGrade = studentGrade;
		this.studentAddress = studentAddress;
	}
	
	
	// Natural Sorting order - first by case-insensitive name, then by ID
	@Override
	public int compareTo(Student otherName) {
		int compare = this.studentName.compareToIgnoreCase(otherName.studentName);
		return (compare != 0) ? compare : Integer.compare(this.studentId, otherName.studentId);
	}
	
	
	// toString() for human-readable representation
	@Override
	public String toString() {
		return String.format("ID: %-4d | Name: %-15s | Age: %-2d | Grade: %-3s | Address: %s", 
				studentId,studentName,studentAge,studentGrade,studentAddress);
	}
	

	// Hash Code based on studentId
	@Override
	public int hashCode() {
		return Objects.hash(studentId);
	}


	// equals() is useful to prevent accidental collisions when two students happen to share an ID
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(studentAddress, other.studentAddress) && studentAge == other.studentAge
				&& Objects.equals(studentGrade, other.studentGrade) && studentId == other.studentId
				&& Objects.equals(studentName, other.studentName);
	}
	
	
	// getters and setters 
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

}

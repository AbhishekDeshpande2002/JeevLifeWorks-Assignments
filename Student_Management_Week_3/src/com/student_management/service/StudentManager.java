package com.student_management.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.student_management.model.Student;


/* 
 * this class is responsible for in-memory management 
 * 
 * List<Student> - preserves insertion order which is useful for serialization 
 * Map<Integer, Student> - look-ups by Student ID
 * TreeSet<Student> - students sorted by name 
 * 
 * 
 */
public class StudentManager {

	// Insertion‑order list – used for serialization/deserialization.
	private final List<Student> students = new ArrayList<Student>();
	
	// Fast look‑up by primary key (student ID).
	private final Map<Integer, Student> studentMap = new HashMap<Integer, Student>();
	
	// View sorted by natural order (name, then ID).
	private final Set<Student> setByName = new TreeSet<Student>();

	
	/*
	 *  Adds a new student to all collections.
	 *  This method return true if student is added, return false if ID already exists or validation fails 
	 */
	public boolean addStudent(Student student) {
		if(studentMap.containsKey(student.getStudentId())) {
			return false;
		}
		if(!isValid(student)) {
			return false;
		}

		students.add(student);
		studentMap.put(student.getStudentId(), student);
		setByName.add(student);
		return true;
	}


	/*
	 * Removes a student by ID.
	 * return true if removed 
	 * return false if no such ID exists
	 */
	public boolean removeStudent(int studentId) {
		Student student = studentMap.remove(studentId);
		if(student == null) {
			return false;
		}

		students.remove(student);
		setByName.remove(student);
		return true;
	}


	
	/*
	 * updates student credentials 
	 * The method removes the student from {@code setByName} before mutating
     * to keep the sorted set’s ordering consistent, then reinserts it.
	 */
	public boolean updateStudent(int id, String name, int age, String grade, String address) {
		Student student = studentMap.get(id);
		if (student == null) {
			return false;
		}

		// validate proposed changes
		if (name == null || name.trim().isEmpty()) {
			return false;
		}
		if (age <= 0) {
			return false;
		}

		// remove before mutating to keep ordering stable
		setByName.remove(student);           
		student.setStudentName(name.trim());
		student.setStudentAge(age);
		student.setStudentGrade(grade.trim());
		student.setStudentAddress(address.trim());
		setByName.add(student);
		return true;
	}
	
	
	/*
	 *  Finds a student by ID
	 *  return matching student if found 
	 *  return null if not found 
	 */
	public Student searchStudent(int id) {
        return studentMap.get(id);
    }
	
	
	/*
	 * Displays all the students in sorted order 
	 */
	public void displayAllStudents() {
        if (setByName.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        setByName.forEach(System.out::println);
    }
	
	
	/*
	 * Deserializes students from file
	 */
	@SuppressWarnings("unchecked")
	public void loadFromFile(String file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Student> list = (ArrayList<Student>) ois.readObject();
            list.forEach(this::addStudent);   // ensures maps/sets are populated consistently
        } catch (FileNotFoundException e) {
            // first run – nothing to load
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load data: " + e.getMessage());
        }
    }
	
	
	/*
	 * serializes current data into file
	 */
	public void saveToFile(String file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.err.println("Failed to save data: " + e.getMessage());
        }
    }
	
	
	/*
	 * simple validation rules for adding student 
	 * age must not be negative 
	 * name,grade,address must not be empty 
	 */
	private boolean isValid(Student student) {
		return student.getStudentAge() > 0
				&& !student.getStudentName().trim().isEmpty()
				&& !student.getStudentGrade().trim().isEmpty()
				&& !student.getStudentAddress().trim().isEmpty();
	}
}

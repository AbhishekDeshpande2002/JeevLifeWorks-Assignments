package com.student_management.main;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.student_management.model.Student;
import com.student_management.service.StudentManager;


/*
 * Entry point of Student management system
 * 
 * This is simple console-based application that lets users: 
 * Add, Remove, Update, Search students 
 * save students data to a local file (students.dat)	 
 */
public class MainAppliaction {
	
	
	// File name to store/load serialized student data
	private static final String DATA_FILE = "students.dat";
	private static Scanner scanner = new Scanner(System.in);
	
	
	public static void main(String[] args) {
		
		StudentManager studentManager = new StudentManager();
		
		studentManager.loadFromFile(DATA_FILE);
		
		
		while(true) {
			// Display Menu
			showMenu();
			
			// get validated integer input
			int choice = safeInt(scanner);
			
			// based on user choice switch will invoke particular method
			switch(choice) {
			case 1:
				addStudentUI(studentManager);
				break;
				
			case 2:
				removeStudnentUI(studentManager);
				break;
				
			case 3:
				updateStudentUI(studentManager);
				break;
				
			case 4:
				searchStudentUI(studentManager);
				break;
				
			case 5:
				studentManager.displayAllStudents();
				break;
				
			case 6:
				studentManager.saveToFile(DATA_FILE);
				System.out.println("Data Saved.");
				break;
				
			case 7:
				System.out.println("Exiting Student Management System. BYe!!");
				System.exit(0);
				
			default:
				System.out.println("Invalid choice. Try again.");
			}
			
		}
		
	}
	
	// prints the main menu with available operations 
	private static void showMenu() {
		System.out.println("""
	            ===== Student Management System =====
	            1. Add a new student
	            2. Remove a student by ID
	            3. Update student details by ID
	            4. Search for a student by ID
	            5. Display all students (sorted by name)
	            6. Save students.dat file
	            7. Exit
	            -------------------------------------""");
	        System.out.print("Enter choice: ");
	}
	
	
	// method to accept input from user to add student
	private static void addStudentUI(StudentManager studentManager) {
		System.out.println("Id: ");
		int id = safeInt(scanner);
		
		System.out.println("Name: ");
		scanner.nextLine();
		String name = scanner.nextLine().trim();
		
		System.out.println("Age: ");
		int age = safeInt(scanner);
		
		System.out.println("Grade: ");
		scanner.nextLine();
		String grade = scanner.nextLine();
		
		System.out.println("Address: ");
		String address = scanner.nextLine();
		
		Student student = new Student(id,name,age,grade,address);
		
	
		if(studentManager.addStudent(student)) {
			System.out.println("Student added successfully.");
		}
		else {
			System.out.println("Failed to add student!!");
		}
	}
	
	
	// method to accept the Id of student which user want to remove
	private static void removeStudnentUI(StudentManager studentManager) {
		System.out.print("Enter ID to remove: ");
        int id = safeInt(scanner);

        if (studentManager.removeStudent(id)) {
        	System.out.println("Student removed.");
        }
        else{
        	System.out.println("Student not found.");
        }
	}
	
	// method to accept student credentials for updating the student 
	private static void updateStudentUI(StudentManager studentManager) {
		System.out.print("Enter ID to update: ");
        int id = safeInt(scanner);

        System.out.print("New name: ");
        scanner.nextLine();
        String name = scanner.nextLine().trim();

        System.out.print("New age: ");
        int age = safeInt(scanner);

        System.out.print("New grade: ");
        scanner.nextLine();
        String grade = scanner.nextLine();

        System.out.print("New address: ");
        String address = scanner.nextLine();

        if (studentManager.updateStudent(id, name, age, grade, address)) {
        	System.out.println("Student updated.");
        }
        else{
        	System.out.println("Update failed (invalid ID or data).");
        }
	}
	
	// method to search student based in Id
	private static void searchStudentUI(StudentManager studentManager) {
		System.out.print("Enter ID to search: ");
        int id = safeInt(scanner);

        Student student = studentManager.searchStudent(id);
        if (student != null)
            System.out.println(student);
        else
            System.out.println("Student not found.");
	}
	
	// Helper method to validate the integer input from the user
	private static int safeInt(Scanner sc) {
        while (true) {
            try {
                int n = sc.nextInt();
                sc.nextLine(); // consume leftover newline
                return n;
            } catch (InputMismatchException e) {
                sc.nextLine(); // flush invalid
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}

package com.student_management.main;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.student_management.model.Student;
import com.student_management.service.StudentService;
import com.student_management.service.StudentServiceImpl;

/*
 * This is the Main Class and entry point for Student_Management_System
 */
public class MainApplication {
	
	// logger to record the application events 
	private static final Logger LOGGER = Logger.getLogger(MainApplication.class.getName());
	// scanner to accept user inputs 
	private static final Scanner sc = new Scanner(System.in);
	private static final StudentService service = new StudentServiceImpl();
	
	
	// Main method where the application execution starts
	public static void main(String[] args) {
		// configures logging 
		try {
            LogManager.getLogManager().readConfiguration(
                MainApplication.class.getClassLoader()
                    .getResourceAsStream("logging.properties")
            );
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }

        LOGGER.info("Application started");
		
        
        // Simple Menu which runs infinitely until user exits
		while(true) {
			
			printMenu();
			
			int choice = sc.nextInt();
			
			try {
                switch (choice) {
                    case 1 :
                    	addStudent();
                    	break;
                    case 2 :
                    	updateStudent();
                    	break;
                    case 3 :
                    	deleteStudent();
                    	break;
                    case 4 :
                    	viewStudent();
                    	break;
                    case 5 :
                    	viewAllStudents();
                    	break;
                    case 6 :
                    	exportToCSV();
                    	break;
                    case 7 :
                    	exit();
                    	break;
                    default : System.out.println("Invalid choice, try again!");
                }
            } catch (Exception ex) {
            	// this prints the error message occurred during execution 
                System.err.println("Error: " + ex.getMessage());
            }
		}
	}
	
	private static void printMenu() {
        System.out.println("\n===== Student Management System =====");
        System.out.println("1. Add Student");
        System.out.println("2. Update Student");
        System.out.println("3. Delete Student");
        System.out.println("4. View Student by ID");
        System.out.println("5. View All Students");
        System.out.println("6. Export to CSV");
        System.out.println("7. Exit");
        System.out.print("Enter choice: ");
    }
	
	// Collects user input for new student and send to service layer 
	private static void addStudent() {
        Student s = readStudent(false);
        service.registerStudent(s);
        System.out.println("Student added with id: " + s.getStudentId());
    }
	
	// Collects user input for update student and send to service layer
	private static void updateStudent() {
        Student s = readStudent(true);
        service.updateStudent(s);
        System.out.println("Student updated!");
    }
	
	// Collects user input for delete student and send to service layer
	private static void deleteStudent() {
        System.out.print("Enter id to delete: ");
        int id = sc.nextInt();
        service.removeStudent(id);
        System.out.println("Deleted successfully!");
    }

	// Collects user input for retrieving student and send to service layer
	private static void viewStudent() {
        System.out.print("Enter id to view: ");
        int id = sc.nextInt();
        System.out.println(service.fetchStudent(id));
    }
	
	// retrieves all students and prints in console
	private static void viewAllStudents() {
        List<Student> students = service.fetchAllStudents();
        students.forEach(System.out::println);
    }
	
	// Exports all student records to a CSV file specified by the user.
	private static void exportToCSV() {
		System.out.print("Enter output file path (e.g., students.csv): ");
        String path = sc.next();
        try {
        	service.exportStudentsToCSV(path);
        	System.out.println("Exported to " + path);
        }catch (IOException e) {
            System.err.println("Failed to export: " + e.getMessage());
        }
	}
	
	// Terminates the application gracefully.
	private static void exit() {
        System.out.println("Goodbye!");
        System.exit(0);
    }
	
	// Helper method used to accept user input 
	private static Student readStudent(boolean withId) {
        int id = 0;
        if (withId) {
            System.out.print("Enter ID: ");
            id = sc.nextInt();
        }
        System.out.print("Enter Name: ");
	sc.nextLine();	
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        System.out.print("Enter Grade (for eg. PASS or FAIL) : ");
	sc.nextLine();
        String grade = sc.nextLine();
        System.out.print("Enter Address: ");
        String address = sc.next();

        return withId ? new Student(id, name, age, grade, address) : new Student(name, age, grade, address);
    }
}

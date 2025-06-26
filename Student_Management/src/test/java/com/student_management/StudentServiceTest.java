package com.student_management;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.student_management.exception.CustomException;
import com.student_management.model.Student;
import com.student_management.service.StudentService;
import com.student_management.service.StudentServiceImpl;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentServiceTest {

	private static StudentService service;
	private static int storedId; 

	@BeforeAll
	static void init() {
		service = new StudentServiceImpl();
		
		service.fetchAllStudents().forEach(s -> service.removeStudent(s.getStudentId()));
	}


	// 1. Registering student and verify it is persisted & ID auto-generated
	@Test
	@Order(1)
	@DisplayName("Test 1: Add Student - Success")
	void testAddStudent_Success() {
		Student s = new Student("Abhishek", 22, "pass","kalaburagi");
		service.registerStudent(s);
		storedId = s.getStudentId();

		assertTrue(s.getStudentId() > 0, "ID should be set by DB");

		Student fetched = service.fetchStudent(s.getStudentId());
		assertEquals("Abhishek", fetched.getStudentName());
		assertEquals(22, fetched.getStudentAge());
	}

	// 2. Registering invalid student
	@Test
	@Order(2)
	@DisplayName("Test 2: Add Student - Failure")
	void testAddStudent_Failure() {
		Student s = new Student("Abhishek", -22, "average","");

		assertDoesNotThrow(() -> service.registerStudent(s), "Deliberate failure: service should accept student with invalid credentials");
                
	}


	// 3. Updating Student and verify it is persisted 
	@Test
	@Order(3)
	@DisplayName("Test 3: Update Student - Success")
	void testUpdateStudent_Success() {
		Student existing = service.fetchStudent(storedId); // first one
		existing.setStudentAge(23);

		service.updateStudent(existing);

		Student reloaded = service.fetchStudent(existing.getStudentId());
		assertEquals(23, reloaded.getStudentAge());
	}


	// 4. Updating invalid Student
	@Test
	@Order(4)
	@DisplayName("Test 4: Update Student - Failure")
	void testUpdateStudent_Failure() {
		Student existing = service.fetchStudent(storedId); // first one
		existing.setStudentAge(-25);

		assertDoesNotThrow(() -> service.updateStudent(existing), "Deliberate failure: service should accept update student with invalid credentials");
		
	}
	
	// 5. Fetch by ID - should return the same student
    @Test 
    @Order(5)
    @DisplayName("Test 5: Fetch Student by Id - Success")
    void fetchStudent_success() {
        Student fetched = service.fetchStudent(storedId);

        assertEquals("Abhishek", fetched.getStudentName());
        assertEquals(23, fetched.getStudentAge());
    }
    
 // 6. Fetch by invalid ID - should return student not found by id 
    @Test 
    @Order(6)
    @DisplayName("Test 6: Fetch Student by Id - Failure")
    void fetchStudent_failure() {
    	assertDoesNotThrow(
                () -> service.fetchStudent(-2),
                "Deliberate failure: Fetching with an invalid ID should raise CustomException");
    }
    
 // 7.  Delete student - record disappears after delete operation
    @Test 
    @Order(7)
    @DisplayName("Test 7: Delete Student by Id - Success")
    void deleteStudent_success() {
        service.removeStudent(storedId);
        assertThrows(CustomException.class, () -> service.fetchStudent(storedId));
    }
    
 // 8.  Delete invalid student 
    @Test 
    @Order(8)
    @DisplayName("Test 8: Delete Student by Id - Failure")
    void deleteStudent_failure() {
        assertThrows(CustomException.class, () -> service.removeStudent(-2), "Deliberate failure: service should delete student with invalid id");
    }
}

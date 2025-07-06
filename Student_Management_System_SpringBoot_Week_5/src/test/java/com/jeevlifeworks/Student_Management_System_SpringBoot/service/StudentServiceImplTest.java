package com.jeevlifeworks.Student_Management_System_SpringBoot.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jeevlifeworks.Student_Management_System_SpringBoot.dto.StudentRequestDTO;
import com.jeevlifeworks.Student_Management_System_SpringBoot.dto.StudentResponseDTO;
import com.jeevlifeworks.Student_Management_System_SpringBoot.exception.StudentNotFoundException;
import com.jeevlifeworks.Student_Management_System_SpringBoot.model.Student;
import com.jeevlifeworks.Student_Management_System_SpringBoot.repository.StudentRepository;

/*
 * Unit tests for StudentServiceImpl using JUnit 5 and Mockito.
 * The repository is mocked so that we test service logic in isolation.
 */
@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

	// Mocked repository injected into the service under test.
	@Mock
	StudentRepository repository;
	
	// Service instance with mocks injected.
	@InjectMocks
	StudentServiceImpl service;
	
	/*
	 * Positive test case
	 * Ensures that addStudent() returns the saved entity with an ID.
	 */
	@Test
    void addStudent_success() {
        StudentRequestDTO req = new StudentRequestDTO("Abhi", 22, "A", "Kalaburagi");
        Student saved = new Student(1L, "Abhi", 22, "A", "Kalaburagi");
        when(repository.save(any(Student.class))).thenReturn(saved);

        StudentResponseDTO res = service.addStudent(req);

        assertThat(res.getId()).isEqualTo(1L);
        verify(repository).save(any(Student.class));
    }
	
	/*
	 * Negative test case
	 * Ensures addStudent_repositoryThrows() throws RuntimeException when DB not found.
	 */
	@Test
    void addStudent_repositoryThrows() {
        StudentRequestDTO req = new StudentRequestDTO("Abhi", 22, "A", "Kalaburagi");
        when(repository.save(any(Student.class)))
                .thenThrow(new RuntimeException("DB down"));

        assertThrows(RuntimeException.class, () -> service.addStudent(req));
    }
	
	/*
	 * Positive test case
	 * Ensures that getStudentById() returns data when the student exists.
	 */
	@Test
    void getStudentById_success() {
        Student s = new Student(1L, "A", 20, "B", "X");
        when(repository.findById(1L)).thenReturn(Optional.of(s));

        StudentResponseDTO dto = service.getStudentById(1L);

        assertThat(dto.getName()).isEqualTo("A");
    }

	/*
	 * Negative test case
	 * Ensures getStudentById() throws StudentNotFoundException when not found.
	 */
    @Test
    void getStudentById_notFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class, () -> service.getStudentById(99L));
    }
    
    /*
     * Negative test case
     * Ensures updateStudent() throws StudentNotFoundException when ID is absent.
     */
    @Test
    void updateStudent_notFound() {
        when(repository.findById(77L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class,
                     () -> service.updateStudent(77L,
                            new StudentRequestDTO("x", 1, "A", null)));
    }
    
    /*
     * Positive test case
     * Ensures that deleteStudent() calls the repository when the student exists.
     */
    @Test
    void deleteStudent_success() {
        when(repository.existsById(1L)).thenReturn(true);
        service.deleteStudent(1L);
        verify(repository).deleteById(1L);
    }

    /*
     * Negative test case
     * Ensures deleteStudent() throws StudentNotFoundException when ID is absent.
     */
    @Test
    void deleteStudent_notFound() {
        when(repository.existsById(123L)).thenReturn(false);
        assertThrows(StudentNotFoundException.class, () -> service.deleteStudent(123L));
    }
}

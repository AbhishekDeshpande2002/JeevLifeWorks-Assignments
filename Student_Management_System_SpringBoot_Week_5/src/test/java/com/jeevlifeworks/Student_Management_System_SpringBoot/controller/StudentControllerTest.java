package com.jeevlifeworks.Student_Management_System_SpringBoot.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeevlifeworks.Student_Management_System_SpringBoot.dto.*;
import com.jeevlifeworks.Student_Management_System_SpringBoot.exception.StudentNotFoundException;
import com.jeevlifeworks.Student_Management_System_SpringBoot.service.StudentService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/*
 * Unit tests for StudentController using MockMvc.
 * This test class verifies request validation, HTTP response codes,
 * and response body structure by mocking the service layer.
 */
@WebMvcTest(StudentController.class)
public class StudentControllerTest {

	// Injects the MockMvc instance for simulating HTTP requests.
	@Autowired
	MockMvc mockMvc;
	
	// Used to convert Java objects to/from JSON.
	@Autowired
	ObjectMapper mapper;
	
	// Mocked service layer dependency.
	@MockBean
	StudentService service;
	
	/**
     * Test: Successfully adds a student and returns HTTP 201 with the created student's ID.
     */
	@Test
    void addStudent_success() throws Exception {
        StudentRequestDTO req = new StudentRequestDTO("Kiran", 23, "B", "Chennai");
        StudentResponseDTO res = new StudentResponseDTO(1L, "Kiran", 23, "B", "Chennai");
        Mockito.when(service.addStudent(req)).thenReturn(res);

        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id", is(1)));
    }

	/**
     * Test: Triggers validation errors and expects HTTP 400 with validation messages.
     */
    @Test
    void addStudent_validationFail() throws Exception {
        // empty name + invalid age + wrong grade
        StudentRequestDTO bad = new StudentRequestDTO("", -4, "PASS", "BLR");

        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bad)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.name").exists())
               .andExpect(jsonPath("$.age").exists())
               .andExpect(jsonPath("$.grade").exists());
    }
    
    /**
     * Test: Fetching a student by ID that doesn't exist should return 404.
     */
    @Test
    void getStudentById_notFound() throws Exception {
        Mockito.when(service.getStudentById(9L))
               .thenThrow(new StudentNotFoundException("Not found"));

        mockMvc.perform(get("/api/students/9"))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.error").value("Not found"));
    }
}

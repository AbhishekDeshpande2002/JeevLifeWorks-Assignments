package com.jeevlifeworks.Student_Management_System_SpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jeevlifeworks.Student_Management_System_SpringBoot.model.Student;

/**
 * Repository interface for performing CRUD operations on Student entities.
 * 
 * Extends JpaRepository to inherit methods like save, findById, findAll, deleteById, etc.
 * This acts as a Data Access Layer between the service and database.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

	// No need to define any methods here unless you need custom queries.
}

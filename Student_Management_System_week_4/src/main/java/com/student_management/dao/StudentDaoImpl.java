package com.student_management.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.student_management.model.Student;
import com.student_management.utility.DBConnection;
/*
 * Implementation class of StudentDAO interface
 * This class performs all the CRUD operations via JDBC 
 */
public class StudentDaoImpl implements StudentDAO{
	
	private static final Logger LOGGER = Logger.getLogger(StudentDAO.class.getName());

	
	// This method is to add student credentials into database 
	@Override
	public void addStudent(Student student) {
		
		Connection connection = null;
		try {
			connection = DBConnection.buildConnection();
			
			// PreparedStatement is used to accept dynamic parameter values provided by the user
			PreparedStatement ps = connection.prepareStatement("INSERT INTO students(name, age, grade, address) VALUES(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, student.getStudentName());
			ps.setInt(2, student.getStudentAge());
			ps.setString(3, student.getStudentGrade());
			ps.setString(4, student.getStudentAddress());
			
			// executeUpdate returns either 1 or 0 which is stored in insertedRows variable 
			int insertedRows = ps.executeUpdate();
			
			// this if condition validates student added successfully or not 
			if(insertedRows > 0) {
				ResultSet keys = ps.getGeneratedKeys();
				if(keys.next()) {
					int id = keys.getInt(1);
					student.setStudentId(id);
					LOGGER.info("Student added successfully with ID: " + id);
				}
			}
			
		}
		catch(SQLException | ClassNotFoundException e ){
			LOGGER.log(Level.SEVERE,"Student not added!!",e);
			throw new RuntimeException(e);
		}
		// After performing operations with database it is best practice to close the connection.
		finally {
			connectionClose(connection);
		}
	}

	
	
	// this method is to update the existing records in the database
	@Override
	public void updateStudent(Student student) {
		Connection connection = null;
		
		try {
			connection = DBConnection.buildConnection();
			
			PreparedStatement ps = connection.prepareStatement("UPDATE students SET name=?, age=?, grade=?, address=? WHERE id=?");
			
			ps.setString(1, student.getStudentName());
			ps.setInt(2, student.getStudentAge());
			ps.setString(3, student.getStudentGrade());
			ps.setString(4, student.getStudentAddress());
			ps.setInt(5, student.getStudentId());
			
			// executeUpdate returns either 1 or 0 which is stored in updatedRows variable
			int updatedRows = ps.executeUpdate();
			
			// this if condition validates student updated successfully or not
			if(updatedRows > 0) {
				LOGGER.info("Student with ID: "+ student.getStudentId()+" updated successfully.");
			}
			else {
				LOGGER.info("No Student found with Id: "+student.getStudentId());
			}
		}
		catch (SQLException | ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE, "Error updating student", e);
            throw new RuntimeException(e);
		}
		finally {
			connectionClose(connection);
		}
		
	}

	
	// this method is to delete the student records from the database
	@Override
	public void deleteStudent(int id) {
		Connection connection = null;
		
		try {
			connection = DBConnection.buildConnection();
			
			PreparedStatement ps = connection.prepareStatement("DELETE FROM students WHERE id=?");
			
			ps.setInt(1, id);
			
			// executeUpdate returns either 1 or 0 which is stored in deletedRows variable
			int deletedRows = ps.executeUpdate();
			
			// this if condition validates student deleted successfully or not
			if(deletedRows>0) {
				LOGGER.info("Student with ID: " + id +" deleted successfully.");
			}
			else {
				LOGGER.info("No Student found with Id: "+id);
			}
		}
		catch (SQLException | ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE, "Error deleting student", e);
            throw new RuntimeException(e);
		}
		finally {
			connectionClose(connection);
		}
		
	}

	// this method is to retrieve all the student records from the database
	@Override
	public List<Student> getAllStudents() {
		List<Student> studentList = new ArrayList<>();
		Connection connection = null;
		
		try {
			connection = DBConnection.buildConnection();
			
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM students");
			
			// executeQuery returns ResultSet object that contains the data which is executed by the query
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				studentList.add(mapResultSet(rs));
			}
			
		}
		catch (SQLException | ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE, "Error fetching all students", e);
		}
		finally {
			connectionClose(connection);
		}
		
		return studentList;
	}

	// this method is to retrieve the student records by id from the database
	@Override
	public Student getStudentById(int id) {
		Connection connection = null;
		
		try {
			connection = DBConnection.buildConnection();
			
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM students WHERE id=?");
			
			ps.setInt(1, id);
			
			// executeQuery returns ResultSet object that contains the data which is executed by the query
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return mapResultSet(rs);
			}
			
		}
		catch (SQLException | ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE, "Error fetching student by id", e);
		}
		finally {
			connectionClose(connection);
		}
		
		return null;
	}
	
	
	// Helper method to convert ResultSet into Student object 
	private Student mapResultSet(ResultSet rs) throws SQLException{
		return new Student(rs.getInt("id"),
				rs.getString("name"),
				rs.getInt("age"),
				rs.getString("grade"),
				rs.getString("address"));
	}
	
	// helper method to close the connection after use
	private void connectionClose(Connection connection) {
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Connection not closed!", e);
			}
		}
	}
	
}

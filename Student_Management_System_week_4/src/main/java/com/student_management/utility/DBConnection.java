package com.student_management.utility;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/* 
 * Utility class for managing the MySQL Database connection using JDBC 
 * This class loads database credentials from resources/configuration/db.properties
 */
public class DBConnection {
	
	private static Connection connection;
	
	// buildConnection() is called when ever DB operations are to be done. 
	public static Connection buildConnection() throws SQLException, ClassNotFoundException{
		
		if(connection == null || connection.isClosed()) {
			
			// Loads the credentials from properties file  
			try (InputStream input = DBConnection.class.getClassLoader()
                    .getResourceAsStream("configuration/db.properties")) {
                if (input == null) {
                    throw new SQLException("configuration/db.properties not found!");
                }
                
                
                Properties props = new Properties();
                props.load(input);
                
                // Register MySQL JDBC Driver 
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Builds the MySQL Database connection using the accessed credentials
                connection = DriverManager.getConnection(
                        props.getProperty("url"),
                        props.getProperty("username"),
                        props.getProperty("password"));
            } catch (IOException e) {
                throw new SQLException("Unable to load Database properties", e);
            }
		}
		return connection;
	}
}

Student Management System (Console-based Java Application)

This is a simple **Student Management System** built in Java using core object-oriented principles. It supports basic CRUD operations (Create, Read, Update, Delete) on student data, allows data to be stored locally using file serialization, and offers a clean CLI interface for interaction.

---

Features

- Add a new student  
- Update student details  
- Remove a student by ID  
- Search for a student by ID  
- Display all students (sorted by name)  
- Save and load data using file (`students.dat`)  
- Input validation and safe integer entry  
- Fully commented code with clean structure (Model → Service → Main)

---

Project Structure

- com.student_management.model
   Student.java
- com.student_management.service
   StudentManager.java
- com.student_management.main
   MainApplication.java
students.dat

Technologies Used

- Java 19
- Core Java: Collections, Serialization, OOP Principles
- Console-based interaction
- No external dependencies


How to Run 

- import Student_management project in your IDE 
- Run MainApplication in IDE 


Sample menu 

===== Student Management System =====
1. Add a new student
2. Remove a student by ID
3. Update student details by ID
4. Search for a student by ID
5. Display all students (sorted by name)
6. Save students.dat file
7. Exit
-------------------------------------
Enter choice: 


Sample Output 

ID: 1001 | Name: Rahul Sharma     | Age: 20 | Grade: A+  | Address: Pune, MH
ID: 1002 | Name: Anjali Verma     | Age: 21 | Grade: A   | Address: Delhi

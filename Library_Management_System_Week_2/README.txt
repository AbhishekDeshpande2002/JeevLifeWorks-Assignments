Library Management System (Java OOP Console Application)
========================================================

Overview
--------
This is a simple console-based Library Management System built using core Object-Oriented Programming (OOP) principles in Java. It manages books, members, and librarian operations like issuing, returning, reserving, and searching for books.

The project emphasizes:
- Abstraction, Inheritance, Encapsulation, and Polymorphism
- Exception Handling
- Role-based access control
- Modular design without Java 8+ features (compatible with Java 7)

Project Structure
-----------------
- com.Library.model
    - Book.java
    - Member.java (abstract)
    - StudentMember.java
    - TeacherMember.java
    - GuestMember.java
    - Librarian.java

- com.Library.functionality
    - LibraryFunctionality.java

- com.Library.exception
    - CustomException.java

- com.Library.main
    - LibraryMainApplication.java

Key Features
------------
1. Add Book: Adds a new book to the library's catalog.
2. Register Member: Allows registering Student, Teacher, or Guest members.
3. Issue Book: Issues a book to a member based on their type and borrowing limit.
4. Return Book: Returns a book and auto-assigns it to the next reserved member (if any).
5. Reserve Book: Allows members to queue for a book that is currently issued.
6. Search Books: Search books by title, author, or genre.
7. View Issued Books: Shows all books currently issued to a member with due dates.
8. View Overdue Books: Lists books overdue for return along with member info.
9. Remove Book: Only librarians can remove a book if it is not currently issued.
10. Exception Handling: Clear custom error messages for invalid operations.

How to Compile and Run
----------------------
1. Ensure Java is installed on your system.
2. Open a terminal or command prompt.
3. Navigate to the root directory containing the `com` folder.
4. Compile the source files:
    javac com/Library/**/*.java

5. Run the application:
    java com.Library.main.LibraryMainApplication

Usage Notes
-----------
- On startup, a default librarian account is created (Admin).
- Book search and member retrieval are case-insensitive.
- All errors are gracefully handled with custom exceptions.
- All changes exist in memory (no database or file persistence).

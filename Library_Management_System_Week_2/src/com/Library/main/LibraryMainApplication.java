package com.Library.main;

import java.util.Scanner;

import com.Library.exception.CustomException;
import com.Library.functionality.LibraryFunctionality;
import com.Library.model.Book;
import com.Library.model.GuestMember;
import com.Library.model.Librarian;
import com.Library.model.Member;
import com.Library.model.StudentMember;
import com.Library.model.TeacherMember;

// Library Main Class 
public class LibraryMainApplication {

	private static Scanner scanner = new Scanner(System.in);
	
	// Creating object of LibraryFunctionality class
	private static LibraryFunctionality functionality = new LibraryFunctionality();
	
	// Creating Librarian object 
	static Librarian admin = new Librarian("Admin", "admin@gmail.com", "9999999999");

	public static void main(String[] args) {
		// registering Librarian member 
		functionality.registerMember(admin);

		while(true){
			// this method call prints Library menu
			menu();
			
			// this method call performs Console UI Logic 
			UILogic();
		}
	}

	// Method to print Library menu
	public static void menu() {
		System.out.println("\n==== Library Menu ====");
		System.out.println("1  Add book");
		System.out.println("2  Remove book");
		System.out.println("3  Register member");
		System.out.println("4  Issue book");
		System.out.println("5  Return book");
		System.out.println("6  Reserve book");
		System.out.println("7  Search books");
		System.out.println("8  View issued books of member");
		System.out.println("9  View overdue books");
		System.out.println("10  Exit");
		System.out.print("Choose the task number: ");
	}

	// Method to perform UI logic operations 
	public static void UILogic() {

		try {
			// accepts the user choice of operation 
			int choice = Integer.parseInt(scanner.nextLine());

			
			switch(choice) {
			
			// this case will validate the member and add book to library 
			case 1:
				// if not librarian then exits the switch
				if(librarianValidation() == false) {
					System.out.println("Add book is only accessed by Admin/Librarian!");
					break;
				}
				System.out.println("Enter the Title: ");
				String title = scanner.nextLine();
				System.out.println("Enter the Author: ");
				String author = scanner.nextLine();
				System.out.println("Enter the Genre: ");
				String genre = scanner.nextLine();
				// calls addBook method in libraryFunctionality class
				functionality.addBook(new Book(title, author, genre));
				break;
				
			// this case will validate member and remove book from library if book is not issued to member	
			case 2:
				if(librarianValidation() == false) {
					System.out.println("Remove book is only accessed by Admin/Librarian!");
					break;
				}
				System.out.println("Enter the Title: ");
				String removeTitle = scanner.nextLine();
				// calls removeBook method in libraryFunctionality class
				functionality.removeBook(functionality.findBookByTitle(removeTitle));
				break;

			// this case will register member 	
			case 3:
				if(librarianValidation() == false) {
					System.out.println("Register member is only accessed by Admin/Librarian!");
					break;
				}
				System.out.print("Name: ");
				String name = scanner.nextLine();
				System.out.print("Email: ");
				String email = scanner.nextLine();
				System.out.print("Phone: ");
				String phone = scanner.nextLine();
				System.out.print("Type (student/teacher/guest): ");
				String type = scanner.nextLine().toLowerCase();
				Member member;               // declare first
				
				// switch case to decide the type of member
				switch (type) {
				case "student":
					member = new StudentMember(name, email, phone);
					break;
				case "teacher":
					member = new TeacherMember(name, email, phone);
					break;
				case "guest":
					member = new GuestMember(name, email, phone);
					break;
				default:
					member = null;
				}

				// validates the member object 
				if(member != null) {
					functionality.registerMember(member);
				}
				else {
					System.out.println("Invalid type!");
				}
				break;

			
			// this case is to issue book 	
			case 4:
				if(librarianValidation() == false) {
					System.out.println("Issue book is only accessed by Admin/Librarian!");
					break;
				}
				System.out.print("Book title: ");
				String bookTitle = scanner.nextLine();
				System.out.print("Member email: ");
				String memberEmail = scanner.nextLine();
				// finds book by title, member by email and issues book
				functionality.issueBook(functionality.findBookByTitle(bookTitle), functionality.findMemberByEmail(memberEmail));
				break;

			// this case to return book by member 
			case 5:
				System.out.print("Book title: ");
				String returnTitle = scanner.nextLine();
				System.out.print("Member email: ");
				String returnEmail = scanner.nextLine();
				//finds book by title, member by email and returns book
				functionality.returnBook(functionality.findBookByTitle(returnTitle), functionality.findMemberByEmail(returnEmail));
				break;

			// this case to reserve book 
			case 6:
				System.out.print("Book title: ");
				String reserveTitle = scanner.nextLine();
				System.out.print("Member email: ");
				String reserveEmail = scanner.nextLine();
				//finds book by title, member by email and reserves book
				functionality.reserveBook(functionality.findBookByTitle(reserveTitle), functionality.findMemberByEmail(reserveEmail));
				break;

			// this case will search book based on keyword 	
			case 7:
				System.out.println("Keyword: ");
				String keyword = scanner.nextLine();
				functionality.searchBooks(keyword);
				break;

			// this case will display the issued books 	
			case 8:
				System.out.println("Member email: ");
				String findEmail = scanner.nextLine();
				// validates member 
				if(functionality.findMemberByEmail(findEmail) == null) {
					System.out.println("Member not Found");
				}
				else {
					functionality.viewIssuedBooks(functionality.findMemberByEmail(findEmail));
				}
				break;

			// this case will display overdue books 	
			case 9:
				if(librarianValidation() == false) {
					System.out.println("View Overdue book is only accessed by Admin/Librarian!");
					break;
				}
				functionality.viewOverdueBooks();
				break;
				
			// this case will exit the application	
			case 10:
				System.out.println("Bye!");
				System.exit(0);

			default :
				System.out.println("Invalid Choice");
			}
		}catch(CustomException | NumberFormatException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Helper method to validate Librarian by email
	public static boolean librarianValidation() {
		System.out.println("Enter you email: ");
		String adminEmail = scanner.nextLine();
		if(!adminEmail.equals(admin.getEmail())) {
			return false;
		}
		else {
			return true;
		}
	}
}

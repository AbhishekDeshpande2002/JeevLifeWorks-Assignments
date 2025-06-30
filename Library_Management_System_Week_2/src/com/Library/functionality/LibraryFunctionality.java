package com.Library.functionality;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.Library.exception.CustomException;
import com.Library.model.Book;
import com.Library.model.Member;


/**
 * This class handles the core functionality of the Library Management System,
 * including book issuing, returning, reserving, searching, and member registration.
 */
public class LibraryFunctionality {

	private final List<Book> books = new ArrayList<Book>();
	private final List<Member> members = new ArrayList<Member>();

	// Adds a book to the library's collection.
	public void addBook(Book book) {
		books.add(book);
		System.out.println("Book Added");
	}

	// Removes a book from the library if it is not currently issued.
	public void removeBook(Book book) {
		if(book.isIssued()) {
			throw new CustomException("Cannot remove - book is issued");
		}
		else{
			books.remove(book);
			System.out.println("Book removed");
		}
	}

	// Issues a book to a member if it is not already issued and the member is allowed more books.
	public void issueBook(Book book, Member member) {
		if(book.isIssued()) {
			throw new CustomException("Book already issued.");
		}
		else if(!member.canIssueMore()) {
			throw new CustomException("Member limit reached.");
		}
		else {
			LocalDate due = LocalDate.now().plusDays(member.getMaxAllowedDays());
			book.markIssued(member, due);
			member.getCurrentlyIssuedBooks().add(book);
			System.out.println("Issued. Due on "+due);
		}
	}

	// Returns a book from a member and automatically issues it to the next person in the reservation queue, if any.
	public void returnBook(Book book, Member member) {
		if(!book.isIssued() || book.getIssuedTo() != member) {
			throw new CustomException("Book is not issued to "+member.getName());
		}
		else {
			member.getCurrentlyIssuedBooks().remove(book);
			book.markReturned();

			if(!book.getReservationQueue().isEmpty()) {
				Member nextMember = book.getReservationQueue().poll();
				issueBook(book, nextMember);
				System.out.println("Auto Issued to "+nextMember.getName());
			}
			else {
				System.out.println("Returned.");
			}
		}
	}


	// Adds a member to the reservation queue for a book, if not already present.
	public void reserveBook(Book book, Member member) {
		if(!book.isIssued()) {
			System.out.println("Book Available - issue it!");
		}
		if(book.getReservationQueue().contains(member)) {
			System.out.println("Already in queue.");
		}
		else {
			book.getReservationQueue().add(member);
			System.out.println("Reserved");
		}
	}


	// Searches for books in the library by title, author, or genre (case-insensitive).
	public void searchBooks(String bookName) {
		List<Book> matches = books.stream()
		.filter(book -> book.getTitle().toLowerCase().contains(bookName.toLowerCase())
				|| book.getAuthor().toLowerCase().contains(bookName.toLowerCase())
				|| book.getGenre().toLowerCase().contains(bookName.toLowerCase()))
		.collect(Collectors.toList());
		
		if (matches.isEmpty()) {
			throw new CustomException("Book not found");
		}
		else {
			matches.forEach(System.out::println);
		}
	}

	// Displays all books currently issued to a given member.
	public void viewIssuedBooks(Member member) {
		List<Book> issuedBooks = member.getCurrentlyIssuedBooks();
		
		if(issuedBooks.isEmpty()) {
			throw new CustomException("No issued books found!");
		}
		else {
		      issuedBooks.forEach(book -> System.out.println(book+" | due "+book.getDueDate()));
		}
	}

	// Displays all books in the library that are overdue.
	public void viewOverdueBooks() {
		LocalDate today = LocalDate.now();

		List<Book> dueBooks = books.stream()
		     .filter(Book::isIssued)
		     .filter(book -> book.getDueDate().isBefore(today))
		     .collect(Collectors.toList());
		
		if(dueBooks.isEmpty()) {
			throw new CustomException("No overdue books found!");
		}
		else {
		     dueBooks.forEach(book -> System.out.println(book+" | Borrower: "+book.getIssuedTo().getName()));
		}
	}
	
	// Registers a new library member if they do not already exist (based on email or phone).
	public void registerMember(Member member) {
		boolean memberExists = members.stream()
				.anyMatch(m -> m.getEmail().equalsIgnoreCase(member.getEmail())
						|| m.getPhone().equals(member.getPhone()));
		
		if(memberExists) {
			System.out.println("Member with email/phone exists");
		}
		else {
			members.add(member);
			System.out.println("Member Registered");
		}
	}
	
	//  Finds a book by its exact title
	public Book findBookByTitle(String name) {
		Book bookMatch = books.stream()
				.filter(book -> book.getTitle().equalsIgnoreCase(name))
				.findFirst().orElse(null);
		
		if(bookMatch == null) {
			throw new CustomException("Book not found!");
		}
		else {
			return bookMatch;
		}
	}
	
	//  Finds a library member by their email
	public Member findMemberByEmail(String email) {
		Member memberMatch = members.stream()
				.filter(member -> member.getEmail().equalsIgnoreCase(email))
				.findFirst().orElse(null);
		if(memberMatch == null) {
			throw new CustomException("Member not found!");
		}
		else {
			return memberMatch;
		}
		
	}
	
	
}

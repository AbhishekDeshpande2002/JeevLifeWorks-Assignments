package com.Library.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Abstract class representing a library member.
 * This serves as the base class for different types of members (e.g., Student, Teacher, Guest).
 */
public abstract class Member {

	private final UUID memberId = UUID.randomUUID();
	private final String name;
	private final String email;
	private final String phone;
	// Maximum number of books this member can issue
	private final int maxBooksAllowed;
	private final List<Book> currentlyIssuedBooks = new ArrayList<Book>();

	public UUID getMemberID(){
		return memberId;
	}
	public String getName(){
		return name;
	}
	public String getEmail(){
		return email;
	}
	public String getPhone(){
		return phone;
	}
	public int getMaxBooksAllowed(){
		return maxBooksAllowed;
	}
	public List<Book> getCurrentlyIssuedBooks(){
		return currentlyIssuedBooks;
	}
	
	public Member(String name, String email, String phone, int maxBooksAllowed) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.maxBooksAllowed = maxBooksAllowed;
	}
	
	//Returns the maximum number of days a book can be issued for this member type.
	public abstract int getMaxAllowedDays();
	
	// Returns the type of member (e.g., "Student", "Teacher", "Guest").
	public abstract String getMemberType();
	
	
	// Checks whether the member can issue more books.
	public boolean canIssueMore() {
        return currentlyIssuedBooks.size() < maxBooksAllowed;
    }
	
	@Override
	public String toString() {
        return "%s: %s (%s)".formatted(getMemberType(), name, email);
    }
}

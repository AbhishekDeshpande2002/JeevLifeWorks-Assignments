package com.Library.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class Book {

	//Unique identifier generated at instantiation time
	private final UUID bookId = UUID.randomUUID();
	private final String title;
	private final String author;
	private final String genre;

	
	//True while the book is checked out to a member.
	private boolean isIssued = false;
	private LocalDate dueDate;
	private Queue<Member> reservationQueue = new LinkedList<Member>();
	//Member who currently holds the book
	private Member issuedTo;


	public Book(String title, String author, String genre) {
		super();
		this.title = title;
		this.author = author;
		this.genre = genre;
	}

	public UUID getBookID(){
		return bookId;
	}
	public String getTitle(){
		return title; 
	}
	public String getAuthor(){ 
		return author;
	}
	public String getGenre(){ 
		return genre;
	}
	public boolean isIssued() { 
		return isIssued;
	}
	public Member getIssuedTo(){ 
		return issuedTo;
	}
	public LocalDate getDueDate(){
		return dueDate;
	}
	public Queue<Member> getReservationQueue(){
		return reservationQueue;
	}

	
	/**
	 * Marks the book as issued to the specified member
	 * Called only by the service layer after it has validated business rules.
	 */
	public void markIssued(Member member, LocalDate due) {
        this.isIssued = true;
        this.issuedTo = member;
        this.dueDate  = due;
    }
	
	/**
     * Reverts the book to “available” state.
     * Clears borrower, due‑date, and issued flag.
     */
    public void markReturned() {
        this.isIssued = false;
        this.issuedTo = null;
        this.dueDate  = null;
    }
    
    @Override public String toString() {
        return "%s by %s (%s) %s".formatted(
            title, author, genre, isIssued ? "— ISSUED" : "— Available");
    }
}

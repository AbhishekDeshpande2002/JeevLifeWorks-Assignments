package com.Library.model;

// Represents a librarian working at the library.
public class Librarian extends Member{

	public Librarian(String name, String email, String phone) {
		super(name, email, phone, 1000);
	}

	@Override
	public int getMaxAllowedDays() {
		return 365;
	}

	@Override
	public String getMemberType() {
		return "Librarian";
	}
	
}

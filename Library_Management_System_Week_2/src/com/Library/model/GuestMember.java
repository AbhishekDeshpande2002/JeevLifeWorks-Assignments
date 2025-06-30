package com.Library.model;

//Concrete implementation of Member that represents a guest.
public class GuestMember extends Member{

	public GuestMember(String name, String email, String phone) {
		super(name, email, phone, 1);
	}

	// the maximum number of days a guest may keep an issued book
	@Override
	public int getMaxAllowedDays() {
		return 7;
	}

	@Override
	public String getMemberType() {
		return "Guest";
	}
	
}
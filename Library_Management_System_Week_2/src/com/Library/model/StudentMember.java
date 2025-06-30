package com.Library.model;


// Concrete implementation of Member that represents a student.
public class StudentMember extends Member{

	public StudentMember(String name, String email, String phone) {
		super(name, email, phone, 3);
	}

	// the maximum number of days a student may keep an issued book
	@Override
	public int getMaxAllowedDays() {
		return 14;
	}

	@Override
	public String getMemberType() {
		return "Student";
	}
	
}

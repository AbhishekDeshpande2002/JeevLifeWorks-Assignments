package com.Library.model;

// Concrete implementation of Member that represents a teacher.
public class TeacherMember extends Member{

	public TeacherMember(String name, String email, String phone) {
		super(name, email, phone, 5);
	}

	// the maximum number of days a teacher may keep an issued book
	@Override
	public int getMaxAllowedDays() {
		return 30;
	}

	@Override
	public String getMemberType() {
		return "Teacher";
	}
	
}

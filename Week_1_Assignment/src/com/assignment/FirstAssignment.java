package com.assignment;

import java.util.Scanner;

public class FirstAssignment {
	
	private static final Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		
		System.out.println("First Assignment");
		System.out.println("==========================");
		
		System.out.println("Enter first integer : ");
		int firstInt = sc.nextInt();
		
		System.out.println("Enter second integer : ");
		int secondInt = sc.nextInt();
		
		System.out.println("Enter floating-point number : ");
		double decimalNum = sc.nextDouble();
		
		System.out.println("Enter a single character : ");
		String input = sc.next();
		char singleChar = input.charAt(0);
		
		System.out.println("Enter a boolean value (true/false) : ");
		boolean boolValue = sc.nextBoolean();
		
		System.out.println("Enter your name : ");
		sc.nextLine();
		String name = sc.nextLine();
		
		
		sumOfTwo(firstInt, secondInt);
		
		subOfTwo(firstInt, secondInt);
		
		multiOfTwo(firstInt, secondInt);
		
		multiplyOfDecimalNum(decimalNum);
		
		nextChar(singleChar);
		
		reverseBoolean(boolValue);
		
		printHello(name);
	}
	
	// this method calculates sum of two numbers provided by user
	public static void sumOfTwo(int firstInt, int secondInt) {
		System.out.println("Sum of "+ firstInt+" and "+ secondInt+" is: "+ (firstInt+secondInt));
	}
	
	// this method calculates difference between two numbers provided by user
	public static void subOfTwo(int firstInt, int secondInt) {
		System.out.println("Difference between "+ firstInt+" and "+ secondInt+" is: "+ (firstInt-secondInt));
	}
	
	// this method calculates product of two numbers provided by user
	public static void multiOfTwo(int firstInt, int secondInt) {
		System.out.println("Product of "+ firstInt+" and "+ secondInt+" is: "+ (firstInt*secondInt));
	}
	
	// this method calculates product of decimal number provided by user with 2 
	public static void multiplyOfDecimalNum(double decimalNum) {
		System.out.println(decimalNum+" multiplied by 2 is: "+decimalNum*2);
	}
	
	// this method generates the next character based on user input using ASCII  
	public static void nextChar(char singleChar) {
		System.out.println("The next character after "+singleChar+ " is: "+(++singleChar));
	}
	
	// this method reverses the boolean value using !(NOT) logical operator 
	public static void reverseBoolean(boolean boolValue) {
		System.out.println("The opposite of "+boolValue+" is: "+!boolValue);
	}
	
	// this method prints hello with the user provided name
	public static void printHello(String name) {
		System.out.println("Hello, "+name);
	}
}

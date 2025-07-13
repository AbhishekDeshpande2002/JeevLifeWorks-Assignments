package com.jeevlifeworks.ems.Employee_Service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an Employee in the Employee Service.
 * It includes basic attributes like ID, name, department, position, and salary.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	
	//Unique identifier for the employee
	private Long id;
	//Full name of the employee
    private String name;
    //Department in which the employee works
    private String department;
    //Job position/title of the employee
    private String position;
    //Monthly or annual salary of the employee
    private Double salary;
}

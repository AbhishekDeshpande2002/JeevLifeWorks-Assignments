package com.jeevlifeworks.Smart.Task.Manager.App.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCompletionDto {
	
	private String employeeName;
    private long completedCount;
}

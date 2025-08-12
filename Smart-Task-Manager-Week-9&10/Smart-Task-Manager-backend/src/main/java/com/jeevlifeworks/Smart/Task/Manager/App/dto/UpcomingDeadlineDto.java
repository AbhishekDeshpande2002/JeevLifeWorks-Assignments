package com.jeevlifeworks.Smart.Task.Manager.App.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpcomingDeadlineDto {
	
	private String title;
    private LocalDate dueDate;
    private boolean overdue;
}

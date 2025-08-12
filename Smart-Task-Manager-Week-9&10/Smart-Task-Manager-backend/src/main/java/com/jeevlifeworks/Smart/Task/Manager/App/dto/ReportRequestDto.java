package com.jeevlifeworks.Smart.Task.Manager.App.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequestDto {
	
	private String status;       
    private Long assigneeId;     
    private LocalDate startDate; 
    private LocalDate endDate;
}

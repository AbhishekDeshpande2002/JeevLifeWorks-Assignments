package com.jeevlifeworks.Smart.Task.Manager.App.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDistributionDto {
	
	private String status;
    private Long count;
}

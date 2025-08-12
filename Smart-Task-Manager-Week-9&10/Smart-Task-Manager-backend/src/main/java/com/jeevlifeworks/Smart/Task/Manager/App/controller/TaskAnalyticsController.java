package com.jeevlifeworks.Smart.Task.Manager.App.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jeevlifeworks.Smart.Task.Manager.App.dto.EmployeeCompletionDto;
import com.jeevlifeworks.Smart.Task.Manager.App.dto.ReportRequestDto;
import com.jeevlifeworks.Smart.Task.Manager.App.dto.TaskCountsDto;
import com.jeevlifeworks.Smart.Task.Manager.App.dto.TaskDistributionDto;
import com.jeevlifeworks.Smart.Task.Manager.App.dto.UpcomingDeadlineDto;
import com.jeevlifeworks.Smart.Task.Manager.App.service.ReportService;
import com.jeevlifeworks.Smart.Task.Manager.App.service.TaskAnalyticsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST Controller for analytics and reporting features of the Smart Task Manager.
 * Handles requests for task statistics, distribution, completion rates, deadlines, and report generation.
 */
@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@Slf4j
public class TaskAnalyticsController {
	
	private final TaskAnalyticsService analyticsService;
    private final ReportService reportService;

    /**
     * Returns task counts for the currently logged-in user (Assigned, Completed, Pending, Overdue).
     */
    @GetMapping("/counts")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_EMPLOYEE')")
    public TaskCountsDto counts(@AuthenticationPrincipal UserDetails user) {
    	log.info("Fetching task counts for user: "+ user.getUsername());
//        return analyticsService.getTaskCounts(user);
    	TaskCountsDto counts = analyticsService.getTaskCounts(user);
        log.debug("Task counts for "+ user.getUsername() +": "+ counts);
        return counts;
    }

    /**
     * Returns task distribution by status for the currently logged-in user.
     */
    @GetMapping("/distribution")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_EMPLOYEE')")
    public List<TaskDistributionDto> distribution(@AuthenticationPrincipal UserDetails user) {
//        return analyticsService.getTaskDistribution(user);
    	log.info("Fetching task distribution for user: "+ user.getUsername());
        List<TaskDistributionDto> distribution = analyticsService.getTaskDistribution(user);
        log.debug("Task distribution for "+ user.getUsername() +": "+ distribution);
        return distribution;
    }

    /**
     * Returns the number of completed tasks per employee.
     * Accessible only to ADMIN and MANAGER roles.
     */
    @GetMapping("/completion-per-employee")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public List<EmployeeCompletionDto> completionByEmployee() {
//        return analyticsService.getCompletionPerEmployee();
    	log.info("Fetching completed task counts per employee");
        List<EmployeeCompletionDto> completions = analyticsService.getCompletionPerEmployee();
        log.debug("Completion per employee: "+ completions);
        return completions;
    }

    /**
     * Returns upcoming deadlines and overdue tasks for the currently logged-in user.
     */
    @GetMapping("/deadlines")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_EMPLOYEE')")
    public List<UpcomingDeadlineDto> deadlines(@AuthenticationPrincipal UserDetails user) {
//        return analyticsService.getUpcomingDeadlines(user);
    	log.info("Fetching deadlines for user: "+ user.getUsername());
        List<UpcomingDeadlineDto> deadlines = analyticsService.getUpcomingDeadlines(user);
        log.debug("Deadlines for "+ user.getUsername() +": "+ deadlines);
        return deadlines;
    }

    /**
     * Generates a report (PDF or Excel) based on filters provided in ReportRequestDto.
     * Only accessible by ADMIN and MANAGER roles.
     *
     * Accept parameter req Request body containing report filter criteria
     * Accept parameter format File format ("pdf" or "excel")
     * return Byte array of the generated report with appropriate HTTP headers
     */
    @PostMapping("/report")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<byte[]> report(
            @RequestBody ReportRequestDto req,
            @RequestParam String format) {
    	
    	log.info("Generating report in "+ format +" format with filters: "+ req);
        byte[] bytes = reportService.generateReport(req, format);
     // Prepare HTTP headers for file download
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(format.equals("pdf") ? MediaType.APPLICATION_PDF : 
                                                     MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "tasks." + format);
        log.debug("Report generated successfully in "+ format +" format, size: "+ bytes.length +" bytes");
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}

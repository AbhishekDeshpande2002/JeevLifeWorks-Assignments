package com.jeevlifeworks.Smart.Task.Manager.App.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.jeevlifeworks.Smart.Task.Manager.App.Entity.Task;
import com.jeevlifeworks.Smart.Task.Manager.App.Entity.User;
import com.jeevlifeworks.Smart.Task.Manager.App.dto.EmployeeCompletionDto;
import com.jeevlifeworks.Smart.Task.Manager.App.dto.TaskCountsDto;
import com.jeevlifeworks.Smart.Task.Manager.App.dto.TaskDistributionDto;
import com.jeevlifeworks.Smart.Task.Manager.App.dto.UpcomingDeadlineDto;
import com.jeevlifeworks.Smart.Task.Manager.App.exception.ResourceNotFoundException;
import com.jeevlifeworks.Smart.Task.Manager.App.repository.TaskRepository;
import com.jeevlifeworks.Smart.Task.Manager.App.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * Service responsible for generating task analytics and statistics for dashboards.
 * Works with repositories to fetch task data, processes it, and returns
 * DTOs suitable for frontend display.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TaskAnalyticsService {
	
	private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    /**
     * Retrieves counts of assigned, completed, pending, and overdue tasks for a given user.
     *
     * Accepts parameter userDetails Authenticated user details from Spring Security
     * return TaskCountsDto containing counts for dashboard display
     */
    public TaskCountsDto getTaskCounts(UserDetails userDetails) {
    	log.info("Fetching task counts for user: "+ userDetails.getUsername());
    	// Get the authenticated user from the database
        User user = userRepository.findByEmail(userDetails.getUsername())
                     .orElseThrow(()-> {
                         log.error("User not found: "+ userDetails.getUsername());
                         return new ResourceNotFoundException("User not found");
                     });
     // Fetch all tasks assigned to the user
        List<Task> tasks = taskRepository.findByAssigneeId(user.getId());
        log.debug("Total tasks fetched for "+ user.getEmail() +": "+ tasks.size());

     // Calculate task statistics
        int assigned = tasks.size();
        int completed = (int) tasks.stream().filter(t -> t.getStatus().equalsIgnoreCase("Completed")).count();
        int overdue = (int) tasks.stream()
                            .filter(t -> !"Completed".equalsIgnoreCase(t.getStatus()) && t.getDueDate().isBefore(LocalDate.now()))
                            .count();
        int pending = assigned - completed;
        
        log.info("Task counts for {}: Assigned={}, Completed={}, Pending={}, Overdue={}",
                user.getEmail(), assigned, completed, pending, overdue);

        return new TaskCountsDto(assigned, completed, pending, overdue);
    }

    
    /**
     * Retrieves the distribution of tasks by status for the logged-in user.
     *
     * Accepts parameter userDetails Authenticated user details
     * return List of TaskDistributionDto with status and count
     */
    public List<TaskDistributionDto> getTaskDistribution(UserDetails userDetails) {
    	log.info("Fetching task distribution for user: "+ userDetails.getUsername());
    	// Get user details from DB
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> {
                    log.error("User not found: {}", userDetails.getUsername());
                    return new ResourceNotFoundException("User not found");
                });
//        return taskRepository.countTasksByStatusForUser(user.getId())
//                .stream()
//                .map(o -> new TaskDistributionDto((String) o[0], (Long) o[1]))
//                .collect(Collectors.toList());
        
     // Fetch status counts from DB
        List<Object[]> results = taskRepository.countTasksByStatusForUser(user.getId());
        log.debug("Task distribution raw data for "+ user.getEmail() +": "+ results);

        // Convert raw data into DTOs
        List<TaskDistributionDto> distribution = results.stream()
                .map(o -> new TaskDistributionDto((String) o[0], (Long) o[1]))
                .collect(Collectors.toList());

        log.info("Task distribution for "+ user.getEmail() +": "+ distribution);
        return distribution;
    }

    /**
     * Retrieves the number of completed tasks for each employee in the system.
     * Used for admin/manager analytics.
     *
     * return List of EmployeeCompletionDto with employee name and completed task count
     */
    public List<EmployeeCompletionDto> getCompletionPerEmployee() {
    	log.info("Fetching completed task count per employee");
//        return taskRepository.completedTasksPerEmployee()
//                .stream()
//                .map(o -> new EmployeeCompletionDto((String) o[0], (Long) o[1]))
//                .collect(Collectors.toList());
    	
    	// Fetch data from DB
        List<Object[]> results = taskRepository.completedTasksPerEmployee();
        log.debug("Raw completion data: +", results);

        // Map to DTOs
        List<EmployeeCompletionDto> completions = results.stream()
                .map(o -> new EmployeeCompletionDto((String) o[0], (Long) o[1]))
                .collect(Collectors.toList());

        log.info("Completed task counts per employee: "+ completions);
        return completions;
    }

    
    /**
     * Retrieves upcoming and overdue tasks for the logged-in user.
     *
     * Accepts parameter userDetails Authenticated user details
     * return List of UpcomingDeadlineDto with task title, due date, and overdue flag
     */
    public List<UpcomingDeadlineDto> getUpcomingDeadlines(UserDetails userDetails) {
    	log.info("Fetching upcoming and overdue deadlines for user: "+userDetails.getUsername());
    	// Get user from DB
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> {
                    log.error("User not found: "+ userDetails.getUsername());
                    return new ResourceNotFoundException("User not found");
                });
//        return taskRepository.findUpcomingAndOverdue(user.getId()).stream()
//                .map(t -> new UpcomingDeadlineDto(t.getTitle(), t.getDueDate(),
//                        t.getDueDate().isBefore(LocalDate.now())))
//                .collect(Collectors.toList());
        
     // Fetch upcoming and overdue tasks from DB
        List<Task> tasks = taskRepository.findUpcomingAndOverdue(user.getId());
        log.debug("Upcoming/Overdue tasks fetched for "+ user.getEmail() +": "+ tasks);

        // Map to DTOs with overdue flag
        List<UpcomingDeadlineDto> deadlines = tasks.stream()
                .map(t -> new UpcomingDeadlineDto(t.getTitle(), t.getDueDate(),
                        t.getDueDate().isBefore(LocalDate.now())))
                .collect(Collectors.toList());

        log.info("Upcoming and overdue deadlines for "+ user.getEmail() +": "+ deadlines);
        return deadlines;
    }
}

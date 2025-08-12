import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TaskService } from '../../tasks/services/task.service';
import { TaskCounts, TaskDistribution, TaskDto, TaskStatus } from '../../shared/models/task.model';
import { ToastrService } from 'ngx-toastr';
import { Chart, ChartData, ChartOptions, ChartType, registerables, ArcElement, Tooltip, Legend } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { DashboardAnalyticsService } from '../../services/dashboard-analytics.service';

Chart.register(...registerables);



@Component({
  selector: 'app-employee-dashboard',
  templateUrl: './employee-dashboard.component.html',
  styleUrls: ['./employee-dashboard.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, BaseChartDirective]
})
export class EmployeeDashboardComponent implements OnInit {
  // Stores the list of tasks assigned to the logged-in employee
  tasks: TaskDto[] = [];
  // Possible statuses a task can have
  taskStatuses: TaskStatus[] = ['To Do', 'In Progress', 'Completed'];
  loading: boolean = true; // Used to show a loading spinner
  errorMessage: string | null = null; // Stores error messages

  // Store all tasks separately for full dataset
  allTasks: TaskDto[] = [];
  searchTerm: string = '';

  @ViewChild(BaseChartDirective) Chart?: BaseChartDirective;

  counts?: TaskCounts;
  distribution: TaskDistribution[] = [];
  deadlines: any[] = [];

  pieChartData!: ChartData<'pie', number[], string | string[]>;
  pieChartOptions: ChartOptions<'pie'> = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top'
      }
    }
  };


  constructor(
    private router: Router,
    private taskService: TaskService,
    private toastr: ToastrService,
    private analytics: DashboardAnalyticsService
  ) { }

  /**
   * Angular lifecycle hook
   * Runs automatically when the component is initialized
   */
  ngOnInit(): void {
    this.loadAssignedTasks(); // Load all tasks assigned to the logged-in employee

    this.analytics.getCounts().subscribe(c => this.counts = c);
    this.analytics.getDistribution().subscribe(data => {
      this.updatePieChart(data);
    });
    this.analytics.getDeadlines().subscribe(dd => this.deadlines = dd);
  }

  /**
   * Fetches all tasks assigned to the logged-in employee from the backend
   */
  loadAssignedTasks(): void {
    this.taskService.getAssignedTasks().subscribe({
      next: (tasks) => {
        this.tasks = tasks; // Store tasks in component variable
        this.loading = false; // Stop loading indicator
        this.allTasks = tasks;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load assigned tasks. Please try again later.';
        this.loading = false;
        console.error('Error fetching assigned tasks', err);
      }
    });
  }

  /**
   * Updates the status of a specific task
   * Accepts parameter taskId - The ID of the task being updated
   * Accepts parameter event - The HTML select change event containing the new status
   */
  updateTaskStatus(taskId: number, event: Event): void {
    const status = (event.target as HTMLSelectElement).value as TaskStatus;
    this.taskService.updateTaskStatus(taskId, status).subscribe({
      next: () => {
        this.toastr.success('Task status updated successfully.');
        this.loadAssignedTasks(); // Reload tasks after updating

        // Reload updated analytics data (counts and distribution)
        this.analytics.getCounts().subscribe(c => this.counts = c);
        this.analytics.getDistribution().subscribe(data => {
          this.updatePieChart(data);
        });
      },
      error: (err) => this.toastr.error('Failed to update task status: ' + err.message)
    });
  }

  /**
   * Logs the user out by removing token and redirecting to login page
   */
  logout(): void {
    localStorage.removeItem('authToken'); // Remove JWT token
    this.router.navigate(['/login']); // Redirect to login page
  }

  updatePieChart(data: TaskDistribution[]) {
    this.distribution = data;
    this.pieChartData = {
      labels: data.map(d => d.status),
      datasets: [{
        data: data.map(d => d.count),
        backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40'],
        hoverBackgroundColor: ['#FF6384CC', '#36A2EBCC', '#FFCE56CC', '#4BC0C0CC', '#9966FFCC', '#FF9F40CC']
      }]
    };
    // Trigger chart update (redraw)
    this.Chart?.update();
  }

  // Called when search term changes
  filterTasks(): void {
    const term = this.searchTerm.trim().toLowerCase();

    if (!term) {
      // If search box is empty, show all tasks
      this.tasks = [...this.allTasks];
    } else {
      this.tasks = this.allTasks.filter(task =>
        task.title.toLowerCase().includes(term) ||
        task.description?.toLowerCase().includes(term) ||
        task.priority?.toLowerCase().includes(term) ||
        (task.status && task.status.toLowerCase().includes(term))
      );
    }
  }
}

import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { TaskService } from '../../tasks/services/task.service';
import { UserService } from '../../users/services/user.service';
import { TaskDto } from '../../shared/models/task.model';
import { User } from '../../shared/models/user.model';
import { TaskFormComponent } from '../../tasks/components/task-form/task-form.component';
import { ToastrService } from 'ngx-toastr';
import { BaseChartDirective } from 'ng2-charts';
import { Chart, ChartData, ChartOptions, ChartType, BarController, BarElement, CategoryScale, LinearScale, Title, Tooltip, Legend } from 'chart.js';
import { DashboardAnalyticsService } from '../../services/dashboard-analytics.service';
import { EmployeeCompletion } from '../../shared/models/task.model';
import { saveAs } from 'file-saver';
import { FormsModule } from '@angular/forms';

// Register bar chart related components
Chart.register(BarController, BarElement, CategoryScale, LinearScale, Title, Tooltip, Legend);

@Component({
  selector: 'app-manager-dashboard',
  templateUrl: './manager-dashboard.component.html',
  styleUrls: ['./manager-dashboard.component.scss'],
  standalone: true,
  imports: [CommonModule, TaskFormComponent, BaseChartDirective, FormsModule]
})
export class ManagerDashboardComponent implements OnInit {

  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;

  // Separate arrays for original and filtered tasks
  allTasks: TaskDto[] = [];
  tasks: TaskDto[] = [];
  searchTerm: string = ''; // For search box
  // Array to store employees
  employees: User[] = [];

  completionData!: ChartData<'bar'>;
  barChartOptions: ChartOptions<'bar'> = {
    responsive: true,
    plugins: { legend: { display: false }, title: { display: true, text: 'Tasks Completed per Employee' } }
  };
  barChartType: ChartType = 'bar';

  // Report filter fields
  selectedStatus: string = '';
  assigneeId?: number;
  startDate?: string;
  endDate?: string;

  constructor(
    private router: Router,
    private taskService: TaskService,
    private userService: UserService,
    private toastr: ToastrService,
    private analyticsService: DashboardAnalyticsService
  ) {}

  /**
   * Lifecycle hook that runs once when the component is initialized
   * Here we load both tasks and employees for display
   */
  ngOnInit(): void {
    this.loadTasks();
    this.loadEmployees();
    this.loadCompletionChart();
  }

  /**
   * Fetches all tasks from the backend using TaskService
   * Assigns the fetched data to the 'tasks' array
   */
  loadTasks(): void {
    this.taskService.getTasks().subscribe({
      next: (data) => {
        this.allTasks = data;   // Store original
        this.tasks = data;      // Display initially
      },
      error: (err) => this.toastr.error('Failed to load tasks: ' + err.message)
    });
  }

  /**
   * Fetches all users from the backend using UserService
   * Filters the list to only include users with the role 'ROLE_EMPLOYEE'
   */
  loadEmployees(): void {
    this.userService.getUsers().subscribe({
      next: (data) => {
        // Keep only users who have ROLE_EMPLOYEE in their roles
        this.employees = data.filter(user => user.roles.some(role => role.name === 'ROLE_EMPLOYEE'));
      },
      error: (err) => this.toastr.error('Failed to load employees: ' + err.message)
    });
  }

  /**
   * Called when a new task is successfully created from the TaskFormComponent
   * Shows a success message and reloads the task list
   */
  onTaskCreated(): void {
    this.toastr.success('Task created successfully!');
    this.loadTasks();
  }

  /**
   * Logs out the manager by removing the JWT token from localStorage
   * Then navigates to the login page
   */
  logout(): void {
    localStorage.removeItem('authToken');
    this.router.navigate(['/login']);
  }

  /** Load and update completion per employee chart */
  loadCompletionChart() {
    this.analyticsService.getCompletionPerEmployee().subscribe((data: EmployeeCompletion[]) => {
      this.completionData = {
        labels: data.map(e => e.employeeName),
        datasets: [{ label: 'Completed Tasks', data: data.map(e => e.completedCount), backgroundColor: '#36A2EB' }]
      };
      this.chart?.update();
    });
  }

  /** Common download logic */
  downloadReport(format: 'pdf' | 'excel') {
    const filters = {
      status: this.selectedStatus || null,
      assigneeId: this.assigneeId || null,
      startDate: this.startDate || null,
      endDate: this.endDate || null
    };
    this.analyticsService.downloadReport(filters, format).subscribe(blob => {
      saveAs(blob, `tasks-report.${format === 'pdf' ? 'pdf' : 'xlsx'}`);
    });
  }

  // 🔹 This runs when typing in search box
  filterTasks(): void {
    const term = this.searchTerm.trim().toLowerCase();
    if (!term) {
      this.tasks = [...this.allTasks]; // Reset to all
    } else {
      this.tasks = this.allTasks.filter(task =>
        task.title.toLowerCase().includes(term) ||
        task.status?.toLowerCase().includes(term) ||
        task.dueDate?.toString().toLowerCase().includes(term) ||
        (task.assignees && task.assignees.some(a => a.username.toLowerCase().includes(term)))
      );
    }
  }

}

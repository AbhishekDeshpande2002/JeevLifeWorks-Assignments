import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../users/services/user.service';
import { User, UpdateUserRoleDto } from '../../shared/models/user.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BaseChartDirective } from 'ng2-charts';
import { Chart, ChartData, ChartOptions, ChartType, BarController, BarElement, CategoryScale, LinearScale, Title, Tooltip, Legend } from 'chart.js';
import { DashboardAnalyticsService } from '../../services/dashboard-analytics.service';
import { EmployeeCompletion } from '../../shared/models/task.model';
import { saveAs } from 'file-saver';

Chart.register(BarController, BarElement, CategoryScale, LinearScale, Title, Tooltip, Legend);

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, BaseChartDirective]
})
export class AdminDashboardComponent implements OnInit {

  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;

  // Array to store list of users fetched from backend
  users: User[] = [];
  // Available roles to assign to a user
  roles = ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_EMPLOYEE'];


  completionData!: ChartData<'bar'>;
  barChartOptions: ChartOptions<'bar'> = {
    responsive: true,
    plugins: { legend: { display: false }, title: { display: true, text: 'Tasks Completed per Employee' } }
  };
  barChartType: ChartType = 'bar';

  selectedStatus: string = '';
  assigneeId?: number;
  startDate?: string;
  endDate?: string;

  constructor(
    private router: Router,
    private userService: UserService,
    private toastr: ToastrService,
    private analyticsService: DashboardAnalyticsService
  ) {}

  /**
   * Lifecycle hook: called when the component is initialized.
   * We use it to load the user list as soon as the dashboard is shown.
   */
  ngOnInit(): void {
    this.loadUsers();
    this.loadCompletionChart();
  }

  /**
   * Fetches the list of users from the backend.
   * On success → stores data in `users`.
   * On error → shows an error message.
   */
  loadUsers(): void {
    this.userService.getUsers().subscribe({
      next: (data) => this.users = data,
      error: (err) => this.toastr.error('Failed to load users: ' + err.message)
    });
  }

  /**
   * Checks if a given user already has a specific role.
   * Used to mark the correct role as "selected" in the dropdown.
   */
  hasRole(user: User, role: string): boolean {
    return user.roles.some(r => r.name === role);
  }

  /**
   * Updates a user's role when the admin changes the dropdown selection.
   * Accepts parameter userId - ID of the user to update
   * Accepts parameter event - The change event from the dropdown
   */
  updateUserRole(userId: number, event: Event): void {
    const role = (event.target as HTMLSelectElement).value;
    const roleDto: UpdateUserRoleDto = { role: role as 'ROLE_ADMIN' | 'ROLE_MANAGER' | 'ROLE_EMPLOYEE' };
    this.userService.updateUserRole(userId, roleDto).subscribe({
      next: () => {
        this.toastr.success('User role updated successfully.');
        this.loadUsers();
      },
      error: (err) => this.toastr.error('Failed to update role: ' + err.message)
    });
  }

  /**
   * Deletes a user after confirming with the admin.
   * Accepts parameter userId - ID of the user to delete
   */
  deleteUser(userId: number): void {
    if (confirm('Are you sure you want to delete this user?')) {
      this.userService.deleteUser(userId).subscribe({
        next: () => {
          this.toastr.success('User deleted successfully.');
          this.loadUsers();
        },
        error: (err) => this.toastr.error('Failed to delete user: ' + err.message)
      });
    }
  }

  /**
   * Logs the admin out by removing the token and navigating to login page.
   */
  logout(): void {
    localStorage.removeItem('authToken');
    this.router.navigate(['/login']);
  }

  loadCompletionChart() {
    this.analyticsService.getCompletionPerEmployee().subscribe((data: EmployeeCompletion[]) => {
      this.completionData = {
        labels: data.map(e => e.employeeName),
        datasets: [{ label: 'Completed Tasks', data: data.map(e => e.completedCount), backgroundColor: '#FF6384' }]
      };
      this.chart?.update();
    });
  }

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
}

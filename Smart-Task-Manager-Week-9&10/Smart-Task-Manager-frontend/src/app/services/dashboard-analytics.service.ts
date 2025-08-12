import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TaskCounts, TaskDistribution } from '../shared/models/task.model';
import { Observable } from 'rxjs';


/**
 * Service to interact with the backend Analytics API.
 * This handles fetching dashboard analytics data (counts, distribution, deadlines)
 * and generating reports (PDF / Excel) for the Smart Task Manager.
 */
@Injectable({ providedIn: 'root' })
export class DashboardAnalyticsService {
  // Base API URL for analytics endpoints (from environment configuration)
  private apiUrl = `${environment.apiBaseUrl}/analytics`;
  constructor(private http: HttpClient) {}

  /**
   * Fetch total task counts for the logged-in user.
   * Includes: assigned, completed, pending, and overdue task counts.
   */
  getCounts() { return this.http.get<TaskCounts>(`${this.apiUrl}/counts`); }

  /**
   * Fetch distribution of tasks by status for the logged-in user.
   * Example: { "To Do": 5, "In Progress": 3, "Completed": 7 }
   */
  getDistribution() { return this.http.get<TaskDistribution[]>(`${this.apiUrl}/distribution`); }

  /**
   * Fetch completed task counts for each employee.
   * Accessible only to ADMIN and MANAGER roles.
   */
  getCompletionPerEmployee() { return this.http.get<any[]>(`${this.apiUrl}/completion-per-employee`); }

  /**
   * Fetch upcoming deadlines and overdue tasks for the logged-in user.
   */
  getDeadlines() { return this.http.get<any[]>(`${this.apiUrl}/deadlines`); }

  /**
   * Download a report (PDF or Excel) from the backend based on provided filters.
   * Accepts parameter filters - Report filter criteria (status, date range, assignee, etc.)
   * Accepts parameter format - 'pdf' or 'excel'
   * returns Blob file data for download
   */
  downloadReport(filters: any, format: 'pdf' | 'excel') {
    return this.http.post(`${this.apiUrl}/report?format=${format}`, filters, { responseType: 'blob' });
  }
}
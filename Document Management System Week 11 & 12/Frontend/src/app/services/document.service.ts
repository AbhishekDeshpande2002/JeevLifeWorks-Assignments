import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Document } from '../models/document.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' }) 
export class DocumentService {
  // Base API URL configured in environment.ts (for metadata service)
  private base = environment.apiMetadataBase;

  // Hardcoded API URL (not used in current methods, can be removed if redundant)
  private apiUrl = 'http://localhost:8080/api/documents'; 

  constructor(private http: HttpClient) {}

  /**
   * Create a new document (metadata only, file handled separately)
   * @param doc Document metadata to be saved
   * @returns Observable<Document>
   */
  create(doc: Document): Observable<Document> {
    return this.http.post<Document>(this.base, doc);
  }

  /**
   * Get all documents or search by title if provided
   * @param title Optional title filter
   * @returns Observable<Document[]>
   */
  getAll(title?: string): Observable<Document[]> {
    if (title) {
      const params = new HttpParams().set('title', title);
      return this.http.get<Document[]>(`${this.base}/search`, { params });
    }
    return this.http.get<Document[]>(this.base);
  }

  /**
   * Get a single document by its ID
   * @param id Document ID
   * @returns Observable<Document>
   */
  getById(id: number | string): Observable<Document> {
    return this.http.get<Document>(`${this.base}/${id}`);
  }

  /**
   * Update an existing document
   * @param id Document ID
   * @param doc Updated document data
   * @returns Observable<Document>
   */
  update(id: number | string, doc: Document): Observable<Document> {
    return this.http.put<Document>(`${this.base}/${id}`, doc);
  }

  /**
   * Delete a document by ID
   * @param id Document ID
   * @returns Observable<void>
   */
  delete(id: number | string): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}

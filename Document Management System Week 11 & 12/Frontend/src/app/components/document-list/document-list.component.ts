import { Component, OnInit } from '@angular/core';
import { DocumentService } from '../../services/document.service';
import { FileService } from '../../services/file.service';
import { Document } from '../../models/document.model';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { DatePipe, CommonModule } from '@angular/common';

@Component({
  selector: 'app-document-list',
  templateUrl: './document-list.component.html',
  standalone: true,
  imports: [
    CommonModule,          // Required for structural directives (*ngFor, *ngIf)
    FormsModule,           // Enables two-way binding [(ngModel)] for search input
    MatProgressBarModule,  // Angular Material progress bar (if needed for downloads)
    DatePipe               // Allows formatting dates using | date pipe
  ]
})
export class DocumentListComponent implements OnInit {
  documents: Document[] = [];   // Stores list of documents fetched from backend
  searchTitle = '';             // Search input bound to text field

  constructor(
    private docService: DocumentService,   // Service for CRUD on document metadata
    private fileService: FileService,      // Service for file upload/download
    private router: Router                 // Angular Router for navigation
  ) {}

  ngOnInit(): void {
    // Load document list on component initialization
    this.load();
  }

  load() {
    // Fetch documents, optionally filtered by title
    if (this.searchTitle) {
      this.docService.getAll(this.searchTitle).subscribe(d => this.documents = d);
    } else {
      this.docService.getAll().subscribe(d => this.documents = d);
    }
  }

  // Navigate to document view page
  view(doc: Document) { this.router.navigate(['/documents', doc.id]); }

  // Navigate to document edit page
  edit(doc: Document) { this.router.navigate(['/documents', doc.id, 'edit']); }

  // Delete a document after confirmation
  delete(doc: Document) {
    if (!doc.id) return;  // Guard clause: ensure doc has an ID
    if (!confirm('Delete?')) return; // Ask for confirmation
    this.docService.delete(doc.id).subscribe(() => this.load()); // Reload list after delete
  }

  // Download file by reconstructing it from chunks
  async download(doc: Document) {
    if (!doc.fileId || !doc.totalChunks) { 
      alert('File meta missing'); 
      return; 
    }
    await this.fileService.downloadFileFromChunks(
      doc.fileId, 
      doc.fileName || 'file', 
      doc.totalChunks
    );
  }
}

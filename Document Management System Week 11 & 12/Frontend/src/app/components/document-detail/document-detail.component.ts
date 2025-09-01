import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { DocumentService } from '../../services/document.service';
import { FileService } from '../../services/file.service';
import { Document } from '../../models/document.model';
import { FormsModule } from '@angular/forms';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { DatePipe } from '@angular/common';  

@Component({
  selector: 'app-document-detail',
  templateUrl: './document-detail.component.html',
  standalone: true,
  imports: [
    FormsModule,           // Enables ngModel binding if needed
    MatProgressBarModule,  // For Angular Material progress bar
    DatePipe,              // Allows date formatting in template
    CommonModule
  ]
})
export class DocumentDetailComponent implements OnInit {
  doc?: Document;   // Holds the document being displayed
  loading = false;  // Indicates if a download is in progress

  // Injects services for routing, API calls, and file handling
  constructor(
    private route: ActivatedRoute, 
    private docService: DocumentService, 
    private fileService: FileService, 
    private router: Router
  ) {}

  // On component init → load document details using ID from route
  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')!;  // Get :id from URL
    this.docService.getById(id).subscribe(d => this.doc = d);  // Fetch document
  }

  // Downloads the document in chunks
  async download() {
    if (!this.doc || !this.doc.fileId) { 
      alert('No file'); 
      return; 
    }

    this.loading = true; // Show progress indicator
    let totalChunks = this.doc.totalChunks!;

    // If totalChunks not in document, fetch metadata from FileService
    if (!totalChunks) {
      const meta = await this.fileService.getFileMeta(this.doc.fileId!);
      totalChunks = meta.totalChunks;
    }

    // Download file by combining all chunks
    await this.fileService.downloadFileFromChunks(
      this.doc.fileId!, 
      this.doc.fileName!, 
      totalChunks
    );

    this.loading = false; // Hide progress after download
  }

  // Navigate to edit page for this document
  edit() { 
    this.router.navigate(['/documents', this.doc!.id, 'edit']); 
  }

  // Delete document after confirmation
  delete() {
    if (!this.doc?.id) return;
    if (!confirm('Delete?')) return;

    // Call API and redirect to home after successful delete
    this.docService.delete(this.doc.id).subscribe(() => 
      this.router.navigate(['/'])
    );
  }
}

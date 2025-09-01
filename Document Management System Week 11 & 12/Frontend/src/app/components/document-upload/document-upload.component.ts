import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DocumentService } from '../../services/document.service';
import { FileService } from '../../services/file.service';
import { Document } from '../../models/document.model';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-document-upload',
  templateUrl: './document-upload.component.html',
  standalone: true,
    imports: [
      FormsModule,          // Needed for two-way binding [(ngModel)]
      MatProgressBarModule,  // Angular Material progress bar module
      CommonModule
    ]
})
export class DocumentUploadComponent {
  // Form fields
  title = '';
  description = '';
  uploadedBy = '';

  // File selection and upload state
  selectedFile?: File;
  progress = 0;     // upload progress percentage
  uploading = false; // upload in-progress flag

  constructor(private docService: DocumentService, private fileService: FileService) {}

  // Triggered when user selects a file
  onFileSelected(e: any) {
    this.selectedFile = e.target.files?.[0];
  }

  // Handles full upload process: chunking, uploading, and metadata save
  async upload() {
    if (!this.selectedFile) { 
      alert('Select file'); 
      return; 
    }
    this.uploading = true;
    this.progress = 0;

    // Generate a unique fileId for chunk upload
    const fileId = this.fileService.generateFileId();
    const chunkSize = this.fileService.chunkSize;
    const totalChunks = Math.ceil(this.selectedFile.size / chunkSize);

    try {
      // Upload file in chunks
      for (let i = 0; i < totalChunks; i++) {
        const start = i * chunkSize;
        const end = Math.min(start + chunkSize, this.selectedFile.size);
        const chunk = this.selectedFile.slice(start, end);

        // Upload current chunk
        await this.fileService.uploadChunk(fileId, chunk, i, totalChunks);

        // Update progress bar
        this.progress = Math.round(((i + 1) / totalChunks) * 100);
      }

      // Notify backend that upload is complete
      await this.fileService.finalizeUpload(fileId, this.selectedFile.name);

      // Create metadata object to save in DB
      const meta: Document = {
        title: this.title || this.selectedFile.name,
        description: this.description,
        fileName: this.selectedFile.name,
        fileSize: this.selectedFile.size,
        uploadedBy: this.uploadedBy || 'unknown',
        fileId,
        totalChunks
      };

      // Save metadata via document service
      this.docService.create(meta).subscribe(() => {
        alert('Upload completed and metadata saved');
        this.reset(); // Reset form
      }, err => {
        console.error(err);
        alert('Metadata save failed');
      });

    } catch (err) {
      // Handle any error during upload
      console.error('Upload error', err);
      alert('Upload failed');
    } finally {
      // Reset uploading flag after process
      this.uploading = false;
    }
  }

  // Reset form fields after upload
  reset() {
    this.title = this.description = this.uploadedBy = '';
    this.selectedFile = undefined;
    this.progress = 0;
  }
}

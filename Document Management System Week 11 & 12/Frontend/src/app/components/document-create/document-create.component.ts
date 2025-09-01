// Angular core import for Component decorator
import { Component } from '@angular/core';

// Service to call backend API for documents
import { DocumentService } from '../../services/document.service';

// Document model (defines structure of document object)
import { Document } from '../../models/document.model';

// Import FormsModule (needed for ngModel in standalone component)
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-document-create',          // Component selector to use in HTML
  standalone: true,                         // Declares this as a standalone component
  templateUrl: './document-create.component.html', // External template file
  imports: [FormsModule]                    // Import FormsModule for template-driven forms
})
export class DocumentCreateComponent {

  // Initialize an empty Document object (bound to form fields via ngModel)
  document: Document = {
    title: '',
    description: '',
    uploadedBy: ''
  };

  // Inject DocumentService to call backend
  constructor(private documentService: DocumentService) {}

  // Called when form is submitted
  onSubmit() {
    // Call service method to send data to backend
    this.documentService.create(this.document).subscribe({
      // Success callback
      next: (res) => {
        alert('Document created successfully!');
        console.log(res);
      },
      // Error callback
      error: (err) => {
        alert('Error creating document');
        console.error(err);
      }
    });
  }
}

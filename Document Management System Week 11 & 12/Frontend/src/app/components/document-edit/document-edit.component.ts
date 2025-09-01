import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DocumentService } from '../../services/document.service';
import { Document } from '../../models/document.model';
import { FormsModule } from '@angular/forms'; 
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-document-edit',
  templateUrl: './document-edit.component.html',
  standalone: true,   // standalone component (no need to declare in NgModule)
  imports: [
    FormsModule,           // enables [(ngModel)] for two-way binding
    MatProgressBarModule,  // material progress bar (if you want to show upload/update progress)
    RouterModule           // required for routerLink navigation
  ]
})
export class DocumentEditComponent implements OnInit {
  
  // Local document object bound to the form
  doc: Document = { 
    title: '', 
    description: '', 
    fileName: '', 
    fileSize: 0, 
    uploadedBy: '' 
  };

  // Injecting required Angular services:
  // - ActivatedRoute: to fetch route parameters (e.g., document ID)
  // - DocumentService: to interact with backend CRUD APIs
  // - Router: to navigate after successful save
  constructor(
    private route: ActivatedRoute, 
    private docService: DocumentService, 
    private router: Router
  ) {}

  ngOnInit(): void {
    // Get 'id' from URL route parameters
    const id = this.route.snapshot.paramMap.get('id')!;
    
    // Fetch document details by ID and assign to doc
    this.docService.getById(id).subscribe(d => this.doc = d);
  }

  // Save updated document
  save() {
    // Prevent update if ID is missing
    if (!this.doc.id) return;

    // Call update API from service
    this.docService.update(this.doc.id, this.doc).subscribe(() => {
      alert('Updated');  // notify user
      // Navigate back to document details page after saving
      this.router.navigate(['/documents', this.doc.id]);
    });
  }
}

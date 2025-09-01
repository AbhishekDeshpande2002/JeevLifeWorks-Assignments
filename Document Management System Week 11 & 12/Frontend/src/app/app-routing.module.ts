import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DocumentListComponent } from './components/document-list/document-list.component';
import { DocumentUploadComponent } from './components/document-upload/document-upload.component';
import { DocumentDetailComponent } from './components/document-detail/document-detail.component';
import { DocumentEditComponent } from './components/document-edit/document-edit.component';
import { DocumentCreateComponent } from './components/document-create/document-create.component';

// Define all application routes
const routes: Routes = [
  { path: '', component: DocumentListComponent },               // Default route -> show document list
  { path: 'upload', component: DocumentUploadComponent },       // File upload page
  { path: 'documents/:id', component: DocumentDetailComponent },// View details of a specific document
  { path: 'documents/:id/edit', component: DocumentEditComponent }, // Edit an existing document
  { path: 'create-document', component: DocumentCreateComponent },  // Create a new document
  { path: '**', redirectTo: '' }                               // Wildcard -> redirect to home
];

@NgModule({
  imports: [RouterModule.forRoot(routes)], // Register routes in Angular
  exports: [RouterModule]                  // Export so AppModule can use routing
})
export class AppRoutingModule { }

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule, Routes } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';  // Custom routing module
import { AppComponent } from './app.component';

// Feature components
import { DocumentListComponent } from './components/document-list/document-list.component';
import { DocumentUploadComponent } from './components/document-upload/document-upload.component';
import { DocumentDetailComponent } from './components/document-detail/document-detail.component';
import { DocumentEditComponent } from './components/document-edit/document-edit.component';
import { DocumentCreateComponent } from './components/document-create/document-create.component';

// Define application routes
const routes: Routes = [
  { path: 'documents', component: DocumentListComponent },     // List all documents
  { path: 'upload', component: DocumentUploadComponent },      // Upload a document
  { path: 'documents/:id', component: DocumentDetailComponent }, // View a document by ID
  { path: 'edit/:id', component: DocumentEditComponent },      // Edit document by ID
  { path: 'create', component: DocumentCreateComponent },      // Create new document
  { path: '', redirectTo: '/documents', pathMatch: 'full' }    // Default route
];

@NgModule({
  declarations: [
    // Normally declare components here,
    // but since you're using standalone components,
    // this array is empty.
  ],
  imports: [
    BrowserModule,               // Required for browser apps
    AppRoutingModule,            // Custom routing module
    RouterModule.forRoot(routes),// Register defined routes
    FormsModule,                 // Template-driven forms
    HttpClientModule,            // HTTP requests
    BrowserAnimationsModule,     // Required for Angular Material animations
    MatProgressBarModule,        // Material progress bar
    MatButtonModule,             // Material button
    AppComponent,                // Standalone root component
    DocumentListComponent,       // Standalone components imported directly
    DocumentUploadComponent,
    DocumentDetailComponent,
    DocumentEditComponent,
    DocumentCreateComponent
  ],
  providers: [],                 // Services (if any)
  bootstrap: [AppComponent]      // Root component to bootstrap
})
export class AppModule { }

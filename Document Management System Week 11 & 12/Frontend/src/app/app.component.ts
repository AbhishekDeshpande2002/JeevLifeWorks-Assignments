import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-root',        // Root selector used in index.html
  standalone: true,            // Mark this as a standalone component (no NgModule needed)
  imports: [RouterModule],     // Import RouterModule to enable <router-outlet>
  templateUrl: './app.component.html' // External HTML template
})
export class AppComponent {}   // Empty class since this component just holds the router outlet

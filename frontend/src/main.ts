import { bootstrapApplication } from '@angular/platform-browser';
import { HomeComponent } from './app/home/home.component'; 
import { appConfig } from './app/app.config';

bootstrapApplication(HomeComponent, appConfig)
  .catch(err => console.error(err));
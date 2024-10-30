/// <reference types="@angular/localize" />

import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { ProductionPlansComponent } from './app/components/production-plans/production-plans.component';
import { LoginComponent } from './app/components/login/login.component';
import { AccountManagermentComponent } from './app/components/account-managerment/account-managerment.component';
import { PlanCampaignsComponent } from './app/components/plan-campaigns/plan-campaigns.component';
import { ScheduleCampaignsComponent } from './app/components/schedule-campaigns/schedule-campaigns.component';
import { WorkerSchedulesComponent } from './app/components/worker-schedules/worker-schedules.component';
import { EmployeeViewComponent } from './app/components/employee-view/employee-view.component';
import { AppComponent } from './app/app.component';

bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));

/// <reference types="@angular/localize" />

import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { ProductionPlansComponent } from './app/production-plans/production-plans.component';
import { LoginComponent } from './app/login/login.component';
import { AccountManagermentComponent } from './app/account-managerment/account-managerment.component';
import { PlanCampaignsComponent } from './app/plan-campaigns/plan-campaigns.component';
import { ScheduleCampaignsComponent } from './app/schedule-campaigns/schedule-campaigns.component';
import { WorkerSchedulesComponent } from './app/worker-schedules/worker-schedules.component';
import { AttendentComponent } from './app/attendent/attendent.component';

bootstrapApplication(AttendentComponent, appConfig)
  .catch((err) => console.error(err));

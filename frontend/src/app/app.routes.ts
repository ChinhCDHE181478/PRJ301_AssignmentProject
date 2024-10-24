import { Routes } from '@angular/router';
import { AccountManagermentComponent } from './components/account-managerment/account-managerment.component';
import { ProductionPlansComponent } from './components/production-plans/production-plans.component';
import { EmployeeViewComponent } from './components/employee-view/employee-view.component';
import { PlanCampaignsComponent } from './components/plan-campaigns/plan-campaigns.component';
import { ScheduleCampaignsComponent } from './components/schedule-campaigns/schedule-campaigns.component';
import { WorkerSchedulesComponent } from './components/worker-schedules/worker-schedules.component';
import { LoginComponent } from './components/login/login.component';

export const routes: Routes = [
    {
        path: 'account-managerment',
        component: AccountManagermentComponent
    },
    {
        path: 'production-plans',
        component: ProductionPlansComponent
    },
    {
        path: 'employee-view',
        component: EmployeeViewComponent
    },
    {
        path: 'plan-campaigns',
        component: PlanCampaignsComponent
    },
    {
        path: 'schedule-campaigns',
        component: ScheduleCampaignsComponent
    },
    {
        path: 'worker-schedules',
        component: WorkerSchedulesComponent
    },
    {
        path: 'login',
        component: LoginComponent
    }
];

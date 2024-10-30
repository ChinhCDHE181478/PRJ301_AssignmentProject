import { Routes } from '@angular/router';
import { AccountManagermentComponent } from './components/account-managerment/account-managerment.component';
import { ProductionPlansComponent } from './components/production-plans/production-plans.component';
import { EmployeeViewComponent } from './components/employee-view/employee-view.component';
import { PlanCampaignsComponent } from './components/plan-campaigns/plan-campaigns.component';
import { ScheduleCampaignsComponent } from './components/schedule-campaigns/schedule-campaigns.component';
import { WorkerSchedulesComponent } from './components/worker-schedules/worker-schedules.component';
import { LoginComponent } from './components/login/login.component';
import { HeaderComponent } from './components/header/header.component';
import { authGuard } from './auth.guard';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
    },
    {
        path: 'account',
        component: AccountManagermentComponent,
        canActivate: [authGuard]
    },
    {
        path: 'plan',
        component: ProductionPlansComponent,
        canActivate: [authGuard]
    },
    {
        path: 'employee',
        component: EmployeeViewComponent,
        canActivate: [authGuard]
    },
    {
        path: 'campaign',
        component: PlanCampaignsComponent,
        canActivate: [authGuard]
    },
    {
        path: 'schedule',
        component: ScheduleCampaignsComponent,
        canActivate: [authGuard]
    },
    {
        path: 'worker',
        component: WorkerSchedulesComponent,
        canActivate: [authGuard]
    },
    {
        path: 'login',
        component: LoginComponent
    }
];

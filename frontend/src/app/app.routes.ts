import { Routes } from '@angular/router';
import { AccountManagermentComponent } from './account-managerment/account-managerment.component';
import { ProductionPlansComponent } from './production-plans/production-plans.component';

export const routes: Routes = [
    {
        path: '',
        component: AccountManagermentComponent
    },
    {
        path: './production-plans/production-plans.component',
        component: ProductionPlansComponent
    }
];

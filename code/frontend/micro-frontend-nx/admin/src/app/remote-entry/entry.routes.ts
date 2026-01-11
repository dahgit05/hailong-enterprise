import { Route } from '@angular/router';
import { RemoteEntry } from './entry';
import { AdminLayoutComponent } from './ui/layout/admin-layout/admin-layout.component';

export const remoteRoutes: Route[] = [
    {
        path: '',
        component: AdminLayoutComponent,
        children: [
            { path: '', loadComponent: () => import('./features/menu-management/menu-management.component').then(m => m.MenuManagementComponent) },
            { path: 'logs', loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent) },
            { path: 'security', loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent) }
        ]
    }
];

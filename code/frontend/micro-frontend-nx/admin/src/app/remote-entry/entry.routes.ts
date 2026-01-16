import { Route } from '@angular/router';
import { RemoteEntry } from './entry';
import { AdminLayoutComponent } from './ui/layout/admin-layout/admin-layout.component';

export const remoteRoutes: Route[] = [
    {
        path: '',
        component: AdminLayoutComponent,
        children: [
            { path: '', loadComponent: () => import('./features/menu-management/menu-management.component').then(m => m.MenuManagementComponent) },
            { path: 'coming-soon', loadComponent: () => import('./features/coming-soon/coming-soon.component').then(m => m.ComingSoonComponent) },
            { path: 'not-found', loadComponent: () => import('./features/not-found/not-found.component').then(m => m.NotFoundComponent) },
            { path: 'forbidden', loadComponent: () => import('./features/forbidden/forbidden.component').then(m => m.ForbiddenComponent) },
            { path: 'logs', loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent) },
            { path: 'security', loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent) },
            {
                path: 'settings',
                loadComponent: () => import('./features/settings/settings-layout.component').then(m => m.SettingsLayoutComponent),
                children: [
                    { path: '', redirectTo: 'system', pathMatch: 'full' },
                    { path: 'system', loadComponent: () => import('./features/settings/system/system.component').then(m => m.SystemComponent) },
                    { path: 'security', loadComponent: () => import('./features/settings/security/security.component').then(m => m.SecurityComponent) },
                    { path: 'notification', loadComponent: () => import('./features/settings/notification/notification.component').then(m => m.NotificationComponent) },
                    { path: 'log', loadComponent: () => import('./features/settings/log/log.component').then(m => m.LogComponent) }
                ]
            },
            { path: '**', redirectTo: 'not-found' }
        ]
    }
];

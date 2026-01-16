import { Route } from '@angular/router';
import { MainLayoutComponent } from './layouts/main-layout/main-layout';

export const appRoutes: Route[] = [
  // Admin routes - loads entirely from admin remote app (no shell layout)
  {
    path: 'admin',
    loadChildren: () => import('admin/Routes').then((m) => m!.remoteRoutes),
  },
  // Main layout routes - shared by factory, hrm, machine
  {
    path: '',
    component: MainLayoutComponent,
    children: [
      {
        path: 'machine',
        loadChildren: () => import('machine/Routes').then((m) => m!.remoteRoutes),
      },
      {
        path: 'hrm',
        loadChildren: () => import('hrm/Routes').then((m) => m!.remoteRoutes),
      },
      {
        path: 'factory',
        loadChildren: () => import('factory/Routes').then((m) => m!.remoteRoutes),
      },
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'factory',
      },
      {
        path: '**',
        redirectTo: 'admin/not-found',
      },
    ],
  },
];

import { NxWelcome } from './nx-welcome';
import { Route } from '@angular/router';

export const appRoutes: Route[] = [
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
    component: NxWelcome,
  },
];

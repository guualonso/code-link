import { Routes } from '@angular/router';

export const POSTS_ROUTES: Routes = [
  {
    path: 'create',
    loadComponent: () =>
      import('./create/create.component').then(m => m.CreateComponent)
  },
  {
    path: ':id',
    loadComponent: () =>
      import('./detail/detail.component').then(m => m.DetailComponent)
  }
];

import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'home',
    loadChildren: () =>
      import('./features/feed/feed.routes').then(m => m.FEED_ROUTES)
  },

  {
    path: 'posts',
    loadChildren: () =>
      import('./features/posts/posts.routes').then(m => m.POSTS_ROUTES)
  },

  {
    path: '',
    redirectTo: 'auth/login',
    pathMatch: 'full'
  },

  {
    path: 'auth',
    loadChildren: () =>
      import('./core/auth/auth.routes').then(m => m.AUTH_ROUTES)
  }
];

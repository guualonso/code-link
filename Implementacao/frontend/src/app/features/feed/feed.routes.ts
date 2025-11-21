import { Routes } from '@angular/router';

export const FEED_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./components/feed.component').then(m => m.FeedComponent)
  }
];

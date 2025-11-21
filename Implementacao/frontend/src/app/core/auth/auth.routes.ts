import { Routes } from '@angular/router';
import { Login as LoginComponent } from '../../features/auth/login/components/login';
import { Register as RegisterComponent } from '../../features/auth/register/components/register';

export const AUTH_ROUTES: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent }
];

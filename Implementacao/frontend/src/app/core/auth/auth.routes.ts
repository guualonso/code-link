import { Routes } from '@angular/router';
import { Login as LoginComponent } from './login/login';
import { Register as RegisterComponent } from './register/register';

export const AUTH_ROUTES: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent }
];

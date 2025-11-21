import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.scss']
})
export class Login {

  email = '';
  senha = '';

  private auth = inject(AuthService);
  private router = inject(Router);

  login() {
    this.auth.login(this.email, this.senha).subscribe({
      next: () => this.router.navigate(['/home']),
      error: () => alert('Usuário ou senha inválidos')
    });
  }
  goToRegister() {
    this.router.navigate(['/auth/register']);
  }
}

import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.html',
  styleUrls: ['./register.scss']
})
export class Register {

  usuario = {
    nome: '',
    email: '',
    senha: '',
    bio: '',
    telefone: ''
  };

  private auth = inject(AuthService);
  private router = inject(Router);

  register() {
    this.auth.register(this.usuario).subscribe({
      next: () => this.router.navigate(['/auth/login']),
      error: () => alert('Erro ao cadastrar')
    });
  }

  goToLogin() {
    this.router.navigate(['/auth/login']);
  }
}
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { PostService } from '../../../core/services/post.service';

@Component({
  standalone: true,
  selector: 'app-post-create',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss']
})
export class CreateComponent {

  form!: FormGroup;
  previewImage: string | null = null;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private postService: PostService,
    private router: Router
  ) {
    this.form = this.fb.group({
      conteudo: ['', Validators.required],
      imagem: [''],
      comunidadeId: [null]
    });
  }

  onImageChange(event: any) {
    const file = event.target.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = () => {
      this.previewImage = reader.result as string;
      this.form.patchValue({ imagem: this.previewImage });
    };
    reader.readAsDataURL(file);
  }

  submit() {
    if (this.form.invalid) return;

    this.loading = true;

    this.postService.create(this.form.value).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/home'], { state: { refresh: true } });
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
      }
    });
  }
}

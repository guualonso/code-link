import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PostService } from '../../../core/services/post.service';
import { PostDTO } from '../../../shared/models/post.dto';

@Component({
  selector: 'app-post-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  post?: PostDTO;

  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private postService = inject(PostService);

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.postService.getOne(id).subscribe({
      next: (data) => (this.post = data),
      error: () => alert('Post não encontrado')
    });
  }

  delete() {
    if (!this.post?.id) return;

    this.postService.delete(this.post.id).subscribe({
      next: () => this.router.navigate(['/posts']),
      error: () => alert('Erro ao excluir')
    });
  }
}

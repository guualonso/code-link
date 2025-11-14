import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { PostService } from '../../../core/services/post.service';
import { PostDTO } from '../../../shared/models/post.dto';

@Component({
  selector: 'app-posts-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {

  posts: PostDTO[] = [];

  private postService = inject(PostService);
  private router = inject(Router);

  ngOnInit() {
    this.postService.list(0, 20).subscribe({
      next: (data) => (this.posts = data.content),
      error: () => alert('Erro ao carregar posts')
    });
  }

  openDetail(id: number) {
    this.router.navigate(['/posts', id]);
  }

  createNew() {
    this.router.navigate(['/posts/create']);
  }
}

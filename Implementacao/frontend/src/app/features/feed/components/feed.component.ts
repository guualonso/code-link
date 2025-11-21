import { Component, ElementRef, ViewChild, inject, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { PostService } from '../../posts/services/post.service';

@Component({
  standalone: true,
  selector: 'app-feed',
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss']
})
export class FeedComponent implements AfterViewInit {

  @ViewChild('anchor') anchor!: ElementRef;

  private observer!: IntersectionObserver;
  private postService = inject(PostService);

  posts: any[] = [];
  page = 0;
  loading = false;

  ngOnInit() {
    if (history.state.refresh) {
      this.page = 0;
      this.posts = [];
    }

    this.loadPosts();
  }

  ngAfterViewInit() {
    // Configura o Observer
    this.observer = new IntersectionObserver((entries) => {
      if (entries.some(entry => entry.isIntersecting)) {
        this.loadPosts();
      }
    });

    // Começa observar o sentinela
    this.observer.observe(this.anchor.nativeElement);
  }

  loadPosts() {
    if (this.loading) return;

    this.loading = true;

    this.postService.list(this.page, 10).subscribe({
      next: (response) => {
        const content = response.content ?? response;
        this.posts.push(...content);
        this.page++;
        this.loading = false;
      },
      error: err => {
        console.error(err);
        this.loading = false;
      }
    });
  }

  ngOnDestroy() {
    this.observer.disconnect();
  }

  like(post: any) {
    this.postService.like(post.id).subscribe({
      next: (updatedPost) => {
        post.likes = updatedPost.likes;
      },
      error: err => console.error(err)
    });
  }
}

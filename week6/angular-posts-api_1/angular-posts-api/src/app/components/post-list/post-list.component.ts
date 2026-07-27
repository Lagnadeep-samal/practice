import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Post } from '../../models/post';
import { PostService } from '../../services/post.service';

@Component({
  selector: 'app-post-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './post-list.component.html',
  styleUrl: './post-list.component.css'
})
export class PostListComponent implements OnInit {
  posts: Post[] = [];
  loading = false;
  error: string | null = null;

  newTitle = '';
  newBody = '';

  editingId: number | null = null;
  editTitle = '';
  editBody = '';

  // JSONPlaceholder doesn't actually persist new posts (it fakes an id,
  // usually 101+, but that id doesn't exist server-side). We track those
  // ids here so Edit/Delete on them update the local list directly
  // instead of sending a request the fake API would reject.
  private locallyCreatedIds = new Set<number>();

  constructor(private postService: PostService) {}

  isLocallyCreated(id: number): boolean {
    return this.locallyCreatedIds.has(id);
  }

  ngOnInit(): void {
    this.fetchPosts();
  }

  // READ
  fetchPosts(): void {
    this.loading = true;
    this.error = null;
    this.postService.getPosts().subscribe({
      next: (data) => {
        // JSONPlaceholder has 100 posts; slice for a manageable demo list
        this.posts = data.slice(0, 10);
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load posts.';
        this.loading = false;
        console.error(err);
      }
    });
  }

  // CREATE
  addPost(): void {
    if (!this.newTitle.trim()) return;
    this.postService.createPost({
      title: this.newTitle.trim(),
      body: this.newBody.trim(),
      userId: 1
    }).subscribe({
      next: (created) => {
        // JSONPlaceholder is a fake API: it echoes back a new post
        // (usually id: 101) but doesn't persist it server-side, so we
        // remember its id to handle future edits/deletes locally.
        this.locallyCreatedIds.add(created.id);
        this.posts = [created, ...this.posts];
        this.newTitle = '';
        this.newBody = '';
      },
      error: (err) => {
        this.error = 'Failed to create post.';
        console.error(err);
      }
    });
  }

  // UPDATE
  startEdit(post: Post): void {
    this.editingId = post.id;
    this.editTitle = post.title;
    this.editBody = post.body;
  }

  saveEdit(post: Post): void {
    // Locally-created posts (id 101+) don't exist on JSONPlaceholder's
    // server, so a PUT to them would fail. Update the local list directly.
    if (this.isLocallyCreated(post.id)) {
      const idx = this.posts.findIndex(p => p.id === post.id);
      if (idx > -1) {
        this.posts[idx] = { ...this.posts[idx], title: this.editTitle, body: this.editBody };
      }
      this.editingId = null;
      return;
    }

    this.postService.updatePost(post.id, {
      ...post,
      title: this.editTitle,
      body: this.editBody
    }).subscribe({
      next: (updated) => {
        const idx = this.posts.findIndex(p => p.id === post.id);
        if (idx > -1) {
          this.posts[idx] = { ...this.posts[idx], ...updated };
        }
        this.editingId = null;
      },
      error: (err) => {
        this.error = 'Failed to update post.';
        console.error(err);
      }
    });
  }

  cancelEdit(): void {
    this.editingId = null;
  }

  // DELETE
  deletePost(id: number): void {
    // Same reasoning as saveEdit: a locally-created post has no
    // matching record on the server, so skip the API call.
    if (this.isLocallyCreated(id)) {
      this.posts = this.posts.filter(p => p.id !== id);
      this.locallyCreatedIds.delete(id);
      return;
    }

    this.postService.deletePost(id).subscribe({
      next: () => {
        this.posts = this.posts.filter(p => p.id !== id);
      },
      error: (err) => {
        this.error = 'Failed to delete post.';
        console.error(err);
      }
    });
  }
}

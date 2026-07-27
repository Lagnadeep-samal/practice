import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Post } from '../models/post';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private apiUrl = 'https://jsonplaceholder.typicode.com/posts';

  constructor(private http: HttpClient) {}

  // READ (all)
  getPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(this.apiUrl);
  }

  // READ (one)
  getPost(id: number): Observable<Post> {
    return this.http.get<Post>(`${this.apiUrl}/${id}`);
  }

  // CREATE
  createPost(post: Partial<Post>): Observable<Post> {
    return this.http.post<Post>(this.apiUrl, post);
  }

  // UPDATE
  updatePost(id: number, post: Partial<Post>): Observable<Post> {
    return this.http.put<Post>(`${this.apiUrl}/${id}`, post);
  }

  // DELETE
  deletePost(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PostDTO } from '../../shared/models/post.dto';
import { PostCreateDTO } from '../../shared/models/post-create.dto';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private API = 'http://localhost:8080/api/posts';

  constructor(private http: HttpClient) {}

  getAll(): Observable<PostDTO[]> {
    return this.http.get<PostDTO[]>(this.API);
  }

  list(page: number = 0, size: number = 10): Observable<any> {
    return this.http.get<any>(`${this.API}?page=${page}&size=${size}`);
  }

  getOne(id: number): Observable<PostDTO> {
    return this.http.get<PostDTO>(`${this.API}/${id}`);
  }

  create(dto: PostCreateDTO): Observable<PostDTO> {
    return this.http.post<PostDTO>(this.API, dto);
  }

  update(id: number, dto: PostCreateDTO): Observable<PostDTO> {
    return this.http.put<PostDTO>(`${this.API}/${id}`, dto);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API}/${id}`);
  }

  like(id: number): Observable<PostDTO> {
    return this.http.post<PostDTO>(`${this.API}/${id}/like`, {});
  }

}

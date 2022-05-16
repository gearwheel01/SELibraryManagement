import { Injectable } from '@angular/core';
import { Author } from '../dataModels/author'
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) { }

  getAuthors(): Observable<Author[]> {
    return this.http.get<Author[]>(`${this.apiServerUrl}/author`);
  }

  addAuthors(author: Author): Observable<Author> {
    let p = new HttpParams();
    p = p.append("firstName", author.firstName);
    p = p.append("lastName", author.lastName);
    return this.http.post<Author>(`${this.apiServerUrl}/author`, author, {params: p});
  }

  updateAuthors(author: Author): Observable<Author> {
    return this.http.put<Author>(`${this.apiServerUrl}/author`, author);
  }

  deleteAuthors(authorId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/author/${authorId}`);
  }
}

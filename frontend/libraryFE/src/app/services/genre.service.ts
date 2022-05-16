import { Injectable } from '@angular/core';
import { Genre } from '../dataModels/genre'
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GenreService {

  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) { }

  getGenres(): Observable<Genre[]> {
    return this.http.get<Genre[]>(`${this.apiServerUrl}/genre`);
  }

  addGenres(genre: Genre): Observable<Genre> {
    return this.http.post<Genre>(`${this.apiServerUrl}/genre`, genre);
  }

  updateGenres(genre: Genre): Observable<Genre> {
    return this.http.put<Genre>(`${this.apiServerUrl}/genre`, genre);
  }

  deleteGenres(genreId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/genre/${genreId}`);
  }
}

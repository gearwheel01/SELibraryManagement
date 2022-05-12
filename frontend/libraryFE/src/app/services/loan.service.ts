import { Injectable } from '@angular/core';
import { Loan } from '../dataModels/loan'
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoanService {

  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) { }

  getLoans(): Observable<Loan[]> {
    return this.http.get<Loan[]>(`${this.apiServerUrl}/loan`);
  }

  addLoans(loan: Loan): Observable<Loan> {
    return this.http.post<Loan>(`${this.apiServerUrl}/loan`, loan);
  }

  updateLoans(loan: Loan): Observable<Loan> {
    return this.http.put<Loan>(`${this.apiServerUrl}/loan`, loan);
  }

  deleteLoans(loanId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/loan/${loanId}`);
  }
}

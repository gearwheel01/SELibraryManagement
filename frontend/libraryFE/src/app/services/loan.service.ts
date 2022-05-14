import { Injectable } from '@angular/core';
import { Loan } from '../dataModels/loan'
import { HttpClient, HttpParams } from '@angular/common/http';
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

  addLoan(loan: Loan): Observable<Loan> {
    let p = new HttpParams();
    p = p.append("productIsbn", loan.productIsbn);
    p = p.append("customerId", loan.customerId);

    return this.http.post<Loan>(`${this.apiServerUrl}/loan`, {received: loan.received}, {params: p});
  }

  updateLoan(loan: Loan): Observable<Loan> {
    return this.http.put<Loan>(`${this.apiServerUrl}/loan`, loan);
  }

  deleteLoan(loanId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/loan/${loanId}`);
  }
}

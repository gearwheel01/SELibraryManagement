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

  getOpenLoans(productIsbn?: string): Observable<Loan[]> {
    let p = new HttpParams();
    if (productIsbn != null) {
      p = p.append("productIsbn", productIsbn);
    }
    return this.http.get<Loan[]>(`${this.apiServerUrl}/loan/open`, {params: p});
  }

  addLoan(loan: Loan): Observable<Loan> {
    let p = new HttpParams();
    p = p.append("productIsbn", loan.productIsbn);
    p = p.append("customerId", loan.customerId);

    return this.http.post<Loan>(`${this.apiServerUrl}/loan`, {received: loan.received}, {params: p});
  }

  updateLoan(loan: Loan): Observable<Loan> {
    let p = new HttpParams();
    p = p.append("returned", loan.returned!.toISOString().substring(0, 10));
    console.log(p);
    return this.http.put<Loan>(`${this.apiServerUrl}/loan/${loan.id}`, {}, {params: p});
  }

  deleteLoan(loanId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/loan/${loanId}`);
  }
}

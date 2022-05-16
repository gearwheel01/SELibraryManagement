import { Injectable } from '@angular/core';
import { LoanSpecs } from '../dataModels/loanSpecs'
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoanSpecsService {

  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) { }

  getLoanSpecs(): Observable<LoanSpecs[]> {
    return this.http.get<LoanSpecs[]>(`${this.apiServerUrl}/loanSpecs`);
  }

  addLoanSpecs(loanSpecs: LoanSpecs): Observable<LoanSpecs> {
    return this.http.post<LoanSpecs>(`${this.apiServerUrl}/loanSpecs`, loanSpecs);
  }

  updateLoanSpecs(loanSpecs: LoanSpecs): Observable<LoanSpecs> {
    return this.http.put<LoanSpecs>(`${this.apiServerUrl}/loanSpecs`, loanSpecs);
  }

  deleteLoanSpecs(loanSpecsId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/loanSpecs/${loanSpecsId}`);
  }
}

import { Injectable } from '@angular/core';
import { Customer } from '../dataModels/customer'
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) { }

  getCustomers(): Observable<Customer[]> {
    return this.http.get<Customer[]>(`${this.apiServerUrl}/customer`);
  }

  addCustomers(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(`${this.apiServerUrl}/customer`, customer);
  }

  updateCustomers(customer: Customer): Observable<Customer> {
    return this.http.put<Customer>(`${this.apiServerUrl}/customer`, customer);
  }

  deleteCustomers(customerId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/customer/${customerId}`);
  }
}

import { Injectable } from '@angular/core';
import { Customer } from '../dataModels/customer'
import { HttpClient, HttpParams } from '@angular/common/http';
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

  addCustomer(customer: Customer): Observable<Customer> {
    let p = new HttpParams();
    p = p.append("firstName", customer.firstName);
    p = p.append("lastName", customer.lastName);
    return this.http.post<Customer>(`${this.apiServerUrl}/customer`, customer, {params: p});
  }

  updateCustomer(customer: Customer): Observable<Customer> {
    return this.http.put<Customer>(`${this.apiServerUrl}/customer`, customer);
  }

  updateCustomerFines(customer: Customer): Observable<Customer> {
    let p = new HttpParams();
    p = p.append("fines", customer.fines!);
    return this.http.put<Customer>(`${this.apiServerUrl}/customer/${customer.id!}`, {}, {params: p});
  }

  deleteCustomer(customerId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/customer/${customerId}`);
  }
}

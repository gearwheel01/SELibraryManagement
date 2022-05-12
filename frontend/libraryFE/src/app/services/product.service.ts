import { Injectable } from '@angular/core';
import { Product } from '../dataModels/product'
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) { }

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiServerUrl}/product`);
  }

  addProducts(product: Product): Observable<Product> {
    return this.http.post<Product>(`${this.apiServerUrl}/product`, product);
  }

  updateProducts(product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.apiServerUrl}/product`, product);
  }

  deleteProducts(productId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/product/${productId}`);
  }
}

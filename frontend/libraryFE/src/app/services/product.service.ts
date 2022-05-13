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

  addProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(`${this.apiServerUrl}/product`, product);
  }

  updateProduct(productIsbn: string, product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.apiServerUrl}/product/${productIsbn}`, product);
  }

  deleteProduct(productIsbn: string): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/product/${productIsbn}`);
  }
}

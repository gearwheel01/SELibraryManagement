import { Injectable } from '@angular/core';
import { Product } from '../dataModels/product'
import { HttpClient, HttpParams } from '@angular/common/http';
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

  getProductsSearch(searchString: string): Observable<Product[]> {
    let p = new HttpParams();
    if (searchString.length > 0) {
      p = p.append("searchTerm", searchString);
    }
    return this.http.get<Product[]>(`${this.apiServerUrl}/product`, {params: p});
  }

  addProduct(product: Product, addGenreIds: number[], addGenreNames: string[],
              addAuthorIds: number[],
              addAuthorFirstNames: string[], addAuthorLastNames: string[], addAuthorBirths: Date[]): Observable<Product> {
    let p = new HttpParams();
    p = p.append("addGenreIds", addGenreIds.join(", "));
    p = p.append("addGenreNames", addGenreNames.join(", "));
    p = p.append("addAuthorIds", addAuthorIds.join(", "));
    p = p.append("addAuthorFirstNames", addAuthorFirstNames.join(", "));
    p = p.append("addAuthorLastNames", addAuthorLastNames.join(", "));
    p = p.append("addAuthorBirths", addAuthorBirths.join(", "));
    console.log(p);

    return this.http.post<Product>(`${this.apiServerUrl}/product`, product, {params: p});
  }

  updateProduct(productIsbn: string, product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.apiServerUrl}/product/${productIsbn}`, product);
  }

  deleteProduct(productIsbn: string): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/product/${productIsbn}`);
  }
}

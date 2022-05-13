import { Component, OnInit } from '@angular/core';
import { Product } from '../dataModels/product';
import { Author } from '../dataModels/author';
import { Genre } from '../dataModels/genre';
import { ProductService } from '../services/product.service';
import { HttpErrorResponse } from '@angular/common/http';


@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {

  public products: Product[] = [];

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.getProducts();
  }


  public getProducts(): void {
    this.productService.getProducts().subscribe(
      (response: Product[]) => {
        this.products = response;
        console.log(this.products);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
     );
  }

    public addProduct(p: Product): void {
      this.productService.addProduct(p).subscribe(
        (response: any) => {
          console.log(`${response} added product`);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
       );
    }

    public deleteProduct(productIsbn: string): void {
      this.productService.deleteProduct(productIsbn).subscribe(
        (response: any) => {
          console.log(`${response} deleted product`);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
       );
    }

}

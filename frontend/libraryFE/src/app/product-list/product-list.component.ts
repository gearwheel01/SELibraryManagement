import { Component, OnInit } from '@angular/core';
import { Product } from '../dataModels/product';
import { Author } from '../dataModels/author';
import { Genre } from '../dataModels/genre';
import { AddCustomerComponent } from '../add-customer/add-customer.component';
import { AddProductComponent } from '../add-product/add-product.component';
import { ProductService } from '../services/product.service';
import { HttpErrorResponse } from '@angular/common/http';
import { MatDialog } from '@angular/material/dialog';


@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {

  public products: Product[] = [];
  public displayedColumns: string[] = ['isbn', 'title', 'authors', 'genres', 'publication', 'publisher', 'copies', 'available', 'add'];

  constructor(private productService: ProductService,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.getProducts();
  }


  public openAddProductView(): void {
    this.dialog.open(AddProductComponent, {
      height: '50%',
      width: '50%',
    }).afterClosed().subscribe(() => {
        this.getProducts();
    });
  }

  public openAddCustomerView(): void {
  console.log("soup");
   this.dialog.open(AddCustomerComponent, {
     height: '50%',
     width: '500px',
   }).afterClosed().subscribe(() => {
       this.getProducts();
       console.log("souped");
   });
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
          this.getProducts();
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
          this.getProducts();
          console.log(`${response} deleted product`);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
       );
    }

}

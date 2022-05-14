import { Component, OnInit } from '@angular/core';
import { Product } from '../dataModels/product';
import { Author } from '../dataModels/author';
import { Genre } from '../dataModels/genre';
import { AddCustomerComponent } from '../add-customer/add-customer.component';
import { AddProductComponent } from '../add-product/add-product.component';
import { ReceiveLoanComponent } from '../receive-loan/receive-loan.component';
import { ProductService } from '../services/product.service';
import { HttpErrorResponse } from '@angular/common/http';
import { MatDialog } from '@angular/material/dialog';
import {FormControl, FormGroup, Validators} from '@angular/forms';


@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {

  public products: Product[] = [];
  public displayedColumns: string[] = ['isbn', 'title', 'authors', 'genres', 'publication', 'publisher', 'copies', 'available', 'add'];
  public selectedProducts: Product[] = [];

  constructor(private productService: ProductService,
              private dialog: MatDialog) { }

  ngOnInit(): void {

    this.getProducts();
  }


  public openAddProductView(): void {
    this.dialog.open(AddProductComponent, {
      height: '80%',
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
   });
  }

  public openAddLoanView(): void {
  console.log("soup");
   this.dialog.open(ReceiveLoanComponent, {
     height: '50%',
     width: '500px',
     data: {
      selectedProducts: this.selectedProducts
     }
   }).afterClosed().subscribe(() => {
       this.getProducts();
       this.selectedProducts = [];
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

    public addLoan(): void {
    }

    public canCreate(): boolean {
      return true;
    }

    public selectProduct(product: Product): void {
      this.selectedProducts.push(product);
    }

    public unselectProduct(product: Product): void {
      this.selectedProducts = this.selectedProducts.filter(p => p != product);
    }

    public productIsSelected(product: Product): boolean {
      return this.selectedProducts.includes(product);
    }
}

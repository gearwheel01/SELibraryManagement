import { Component, OnInit } from '@angular/core';
import { Product } from '../dataModels/product';
import { Author } from '../dataModels/author';
import { Genre } from '../dataModels/genre';
import { AddCustomerComponent } from '../add-customer/add-customer.component';
import { AddProductComponent } from '../add-product/add-product.component';
import { ReceiveLoanComponent } from '../receive-loan/receive-loan.component';
import { ReturnLoanComponent } from '../return-loan/return-loan.component';
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
  productSearchForm: FormGroup;

  constructor(private productService: ProductService,
              private dialog: MatDialog) {
      this.productSearchForm = new FormGroup({
        searchString: new FormControl('', Validators.required)
      });
   }

  ngOnInit(): void {

    this.getProducts("");
  }


  public openAddProductView(): void {
    this.dialog.open(AddProductComponent, {
      height: '80%',
      width: '50%',
    }).afterClosed().subscribe(() => {
        this.getProducts("");
    });
  }

  public openAddCustomerView(): void {
   this.dialog.open(AddCustomerComponent, {
     height: '50%',
     width: '500px',
   }).afterClosed().subscribe(() => {
       this.getProducts("");
   });
  }

  public openAddLoanView(): void {
   this.dialog.open(ReceiveLoanComponent, {
     height: '50%',
     width: '500px',
     data: {
      selectedProducts: this.selectedProducts
     }
   }).afterClosed().subscribe(() => {
       this.getProducts("");
       this.selectedProducts = [];
   });
  }

  public openReturnLoanView(): void {
   this.dialog.open(ReturnLoanComponent, {
     height: '50%',
     width: '500px',
     data: {
      selectedProducts: this.selectedProducts
     }
   }).afterClosed().subscribe(() => {
       this.getProducts("");
       this.selectedProducts = [];
   });
  }

  public searchProducts(): void {
    this.getProducts(this.productSearchForm.get("searchString")!.value);
  }

  public getProducts(searchString: string): void {
    this.productService.getProductsSearch(searchString).subscribe(
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
          this.getProducts("");
          console.log(`${response} deleted product`);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
       );
    }

    public canReceiveLoan(): boolean {
      return ( (this.selectedProducts.length > 0) && ((this.selectedProducts.find(p => p.remainingCopies! <= 0)) == null) );
    }

    public canReturnLoan(): boolean {
      return ( (this.selectedProducts.length > 0) && ((this.selectedProducts.find(p => p.remainingCopies! == p.copies)) == null) );
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

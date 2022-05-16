import { Component, OnInit } from '@angular/core';
import { Customer } from '../dataModels/customer'
import { Product } from '../dataModels/product'
import { Author } from '../dataModels/author'
import { Loan } from '../dataModels/loan'
import { Genre } from '../dataModels/genre'
import { LoanSpecs } from '../dataModels/loanSpecs'
import { CustomerService } from '../services/customer.service'
import { ProductService } from '../services/product.service'
import { AuthorService } from '../services/author.service'
import { LoanService } from '../services/loan.service'
import { GenreService } from '../services/genre.service'
import { LoanSpecsService } from '../services/loan-specs.service'
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-customer-list',
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.scss']
})
export class CustomerListComponent implements OnInit{

  public customers: Customer[] = [];
  public products: Product[] = [];
  public authors: Author[] = [];
  public loans: Loan[] = [];
  public genres: Genre[] = [];
  public loanSpecs: LoanSpecs[] = [];

  constructor(private customerService: CustomerService,
              private productService: ProductService,
              private authorService: AuthorService,
              private loanService: LoanService,
              private genreService: GenreService,
              private loanSpecsService: LoanSpecsService
              ) { }

  ngOnInit() {
    this.getLoanSpecs();
  }

  public getCustomers(): void {
    this.customerService.getCustomers().subscribe(
      (response: Customer[]) => {
        this.customers = response;
        console.log(this.customers);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
     );
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

    public getGenres(): void {
           this.genreService.getGenres().subscribe(
             (response: Genre[]) => {
               this.genres = response;
               console.log(this.genres);
             },
             (error: HttpErrorResponse) => {
               alert(error.message);
             }
            );
         }

    public getLoanSpecs(): void {
      this.loanSpecsService.getLoanSpecs().subscribe(
        (response: LoanSpecs[]) => {
          this.loanSpecs = response;
          console.log(this.loanSpecs);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
       );
    }

}

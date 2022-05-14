import { Component, OnInit, Input, Inject } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { Product } from '../dataModels/product';
import { Loan } from '../dataModels/loan';
import { Customer } from '../dataModels/customer';
import { CustomerService } from '../services/customer.service';
import { LoanService } from '../services/loan.service';
import { HttpErrorResponse } from '@angular/common/http';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-return-loan',
  templateUrl: './return-loan.component.html',
  styleUrls: ['./return-loan.component.scss']
})
export class ReturnLoanComponent implements OnInit {

  public loading: boolean = false;
  public failed: boolean = false;
  public pendingRequests: number = 0;
  public customers: Customer[] = [];
  public loans: Loan[] = [];
  customerForm: FormGroup;
  selectedProducts: Product[];
  filteredOptionsCustomer: Customer[];

  constructor(public dialog: MatDialogRef<ReturnLoanComponent>,
              private customerService: CustomerService,
              private loanService: LoanService,
              @Inject(MAT_DIALOG_DATA) public data: any) {
      this.customerForm = new FormGroup({
        customerFirstName: new FormControl('', Validators.required),
        customerLastName: new FormControl('', Validators.required),
        customerEmail: new FormControl('', Validators.required),
      });

    this.getCustomers();
    this.selectedProducts = data["selectedProducts"];

    this.selectedProducts.forEach(p => {this.getLoans(p.isbn);});
  }

  ngOnInit(): void {
  }

  closeDialog(): void {
    this.dialog.close();
  }

  public updateLoans(): void {
    this.pendingRequests = this.selectedProducts.length;
    this.loading = true;
    let customer: Customer = this.getCustomerFromInput();

    this.selectedProducts.forEach(product => {
      let loan = this.getLoanForProductAndCustomer(customer, product);
      loan!.returned = new Date();
      console.log(loan!);
      this.updateLoanInDb(loan!);
    });

  }

  public updateLoanInDb(l: Loan): void {
    this.loanService.updateLoan(l).subscribe(
      (response: any) => {
        console.log(`${response} updated loan`);
        this.pendingRequests -= 1;
        if (this.pendingRequests <= 0) {
          this.closeDialog();
        }
      },
      (error: HttpErrorResponse) => {
        this.loading = false;
        this.failed = true;
      }
     );
  }

  public canCreate(): boolean {
    let c: Customer = this.getCustomerFromInput();
    if (c.id == null) {
      return false;
    }

    // compiler error when return is in loop
    let ret: boolean = true;
    this.selectedProducts.forEach(p => {
      if (this.getLoanForProductAndCustomer(c, p) == null) {ret = false;}
    });
    return ret;
  }

  public getLoanForProductAndCustomer(customer: Customer, product: Product): Loan | undefined {
    return (this.loans.find(l =>  ( (l.productIsbn == product.isbn) && (l.customerId == customer.id) ) ) );
  }

  public getCustomerFromInput(): Customer {
    let c: Customer = {
      firstName: this.customerForm.get('customerFirstName')!.value,
      lastName: this.customerForm.get('customerLastName')!.value,
      email: this.customerForm.get('customerEmail')!.value,
    }

    this.customers.forEach(customer => {
      if (
            (customer.firstName == c.firstName) &&
            (customer.lastName == c.lastName) &&
            (customer.email == c.email)
            ){
          c.id = customer.id;
          c.birth = customer.birth;
          c.fines = customer.fines;
       }
    });

    return c;
  }

  public updateFilteredCustomers(): void {
    let firstName = this.customerForm.get('customerFirstName')!.value;
    let lastName = this.customerForm.get('customerLastName')!.value;
    let email = this.customerForm.get('customerEmail')!.value;

    this.filteredOptionsCustomer = this.customers.filter(option => (
            (option.firstName.includes(firstName)) &&
            (option.lastName.includes(lastName)) &&
            (option.email.includes(email))
            )
          );
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

  public getLoans(productIsbn: string): void {
    this.loanService.getOpenLoans(productIsbn).subscribe(
      (response: Loan[]) => {
        this.loans = this.loans.concat(response);
        console.log(this.loans);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
     );
  }

  setCustomerData(customer: Customer): void {
    this.customerForm.get('customerFirstName')!.setValue(customer.firstName);
    this.customerForm.get('customerLastName')!.setValue(customer.lastName);
    this.customerForm.get('customerEmail')!.setValue(customer.email);
  }

}

import { Component, OnInit, Input, Inject } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { Product } from '../dataModels/product';
import { Loan } from '../dataModels/loan';
import { Customer } from '../dataModels/customer';
import { LoanSpecs } from '../dataModels/loanSpecs';
import { CustomerService } from '../services/customer.service';
import { LoanService } from '../services/loan.service';
import { LoanSpecsService } from '../services/loan-specs.service';
import { HttpErrorResponse } from '@angular/common/http';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { environment } from 'src/environments/environment';

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
  public loanSpecs: LoanSpecs[] = [];
  customerForm: FormGroup;
  selectedProducts: Product[] = [];
  filteredOptionsCustomer: Customer[];

  public newFines: number = 0;

  constructor(public dialog: MatDialogRef<ReturnLoanComponent>,
              private customerService: CustomerService,
              private loanService: LoanService,
              private loanSpecsService: LoanSpecsService,
              @Inject(MAT_DIALOG_DATA) public data: any) {
      this.customerForm = new FormGroup({
        customerFirstName: new FormControl('', Validators.required),
        customerLastName: new FormControl('', Validators.required),
        customerEmail: new FormControl('', Validators.required),
      });

    this.getLoanSpecs();
    this.getCustomers();

    if (data["selectedProducts"] != null) {
      this.selectedProducts = data["selectedProducts"];
    }

    this.selectedProducts.forEach(p => {this.getLoans(p.isbn)});
  }

  ngOnInit(): void {
  }

  closeDialog(): void {
    this.dialog.close();
  }

  public updateLoans(): void {
    this.pendingRequests = this.selectedProducts.length;
    this.loading = true;
    this.newFines = 0;
    let customer: Customer = this.getCustomerFromInput();
    let loanSpec: LoanSpecs = this.loanSpecs.find(ls => ls.name == environment.loanSpecsDefaultName)!;
    console.log(loanSpec);

    this.selectedProducts.forEach(product => {
      this.updateLoanForProduct(customer, product, loanSpec);
    });

    if (this.newFines > 0) {
      this.applyFineToCustomer(customer, this.newFines);
    }

  }

  public updateLoanForProduct(customer: Customer, product: Product, loanSpec: LoanSpecs): void {
    let loan = this.getLoanForProductAndCustomer(customer, product);
    loan!.returned = new Date();
    console.log(loan!);
    let daysInterval = this.compareDatesToDays(new Date(loan!.received!), new Date(loan!.returned!));
    if (daysInterval > loanSpec.loanPeriodDays) {
      this.newFines += loanSpec.fineAmount;
    }
    this.updateLoanInDb(loan!);
  }

  public applyFineToCustomer(customer: Customer, fineAmount: number) {
    this.pendingRequests += 1;
    customer.fines = customer.fines! + fineAmount;
    console.log("applying " + fineAmount + " fines to customer (new fine amount: " + customer.fines + ")");
    this.updateCustomerFinesInDb(customer);
  }

  public compareDatesToDays(received: Date, returned: Date): number {
    let timeInMilisec: number = returned.getTime() - received.getTime();
    return Math.ceil(timeInMilisec / (1000 * 60 * 60 * 24));
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

  public updateCustomerFinesInDb(customer: Customer): void {
    this.customerService.updateCustomerFines(customer).subscribe(
      (response: any) => {
        console.log(`${response} updated customer`);
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

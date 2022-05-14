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
  selector: 'app-receive-loan',
  templateUrl: './receive-loan.component.html',
  styleUrls: ['./receive-loan.component.scss']
})
export class ReceiveLoanComponent implements OnInit {

  public loading: boolean = false;
  public failed: boolean = false;
  public pendingRequests: number = 0;
  public customers: Customer[] = [];
  customerForm: FormGroup;
  selectedProducts: Product[];
  filteredOptionsCustomer: Customer[];

  constructor(public dialog: MatDialogRef<ReceiveLoanComponent>,
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
  }

  ngOnInit(): void {
  }

  closeDialog(): void {
    this.dialog.close();
  }

  public addLoan(): void {
    let customer: Customer = this.getCustomerFromInput();
    this.pendingRequests = this.selectedProducts.length;
    this.loading = true;
    this.selectedProducts.forEach(product => {
      let loan: Loan = {
        received: new Date(),
        customerId: customer.id!,
        productIsbn: product.isbn!
      }
      console.log(loan);
      this.addLoanInDb(loan);
    });
  }

  public addLoanInDb(l: Loan): void {
    this.loanService.addLoan(l).subscribe(
      (response: any) => {
        console.log(`${response} added loan`);
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
    return c.id != null;
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

  setCustomerData(customer: Customer): void {
    this.customerForm.get('customerFirstName')!.setValue(customer.firstName);
    this.customerForm.get('customerLastName')!.setValue(customer.lastName);
    this.customerForm.get('customerEmail')!.setValue(customer.email);
  }


}

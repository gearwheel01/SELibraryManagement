import { Component, OnInit, Input, Inject } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { Product } from '../dataModels/product';
import { Loan } from '../dataModels/loan';
import { Customer } from '../dataModels/customer';
import { CustomerService } from '../services/customer.service';
import { HttpErrorResponse } from '@angular/common/http';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'app-receive-loan',
  templateUrl: './receive-loan.component.html',
  styleUrls: ['./receive-loan.component.scss']
})
export class ReceiveLoanComponent implements OnInit {

  public loading: boolean = false;
  public customers: Customer[] = [];
  customerForm: FormGroup;
  @Input() selectedProducts: Product[];
  filteredOptionsCustomer: Customer[];

  constructor(private customerService: CustomerService,
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

  public addLoan(): void {
  }




  public canCreate(): boolean {
    let c: Customer = this.getCustomerFromInput();
    console.log(c.id);
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
          console.log(c);
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

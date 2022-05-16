import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { Customer } from '../dataModels/customer';
import { CustomerService } from '../services/customer.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-add-customer',
  templateUrl: './add-customer.component.html',
  styleUrls: ['./add-customer.component.scss']
})
export class AddCustomerComponent implements OnInit {

  customerForm: FormGroup = new FormGroup({});
  loading: boolean = false;
  failed: boolean = false;

  constructor(public dialog: MatDialogRef<AddCustomerComponent>,
                private customerService: CustomerService) {
      this.customerForm = new FormGroup({
        firstName: new FormControl('', Validators.required),
        lastName: new FormControl('', Validators.required),
        email: new FormControl('', Validators.required),
        birth: new FormControl('', Validators.required)
      });
  }

  closeDialog(): void {
    this.dialog.close();
  }

  addCustomer(): void {
    this.loading = true;
    let customer: Customer = {
      firstName: this.customerForm.get('firstName')!.value,
      lastName: this.customerForm.get('lastName')!.value,
      email: this.customerForm.get('email')!.value,
      birth: this.customerForm.get('birth')!.value,
      fines: 0
    }

    console.log(customer);
    this.addCustomerInDb(customer);
  }

  canCreate(): boolean {
    return ( (this.customerForm.get('firstName')!.value.length > 0 ) &&
             (this.customerForm.get('lastName')!.value.length > 0) &&
             (this.customerForm.get('email')!.value.length > 0) &&
             (this.customerForm.get('birth')!.value.length > 0) );
  }

  ngOnInit(): void {
  }

  public addCustomerInDb(c: Customer): void {
    this.customerService.addCustomer(c).subscribe(
      (response: any) => {
        console.log(`${response} added customer`);
        this.closeDialog();
      },
      (error: HttpErrorResponse) => {
        this.loading = false;
        this.failed = true;
      }
     );
  }

}

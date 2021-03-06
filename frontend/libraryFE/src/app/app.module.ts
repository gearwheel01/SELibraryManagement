import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { MatDialogModule } from '@angular/material/dialog';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatInputModule} from '@angular/material/input'
import {MatSelectModule} from '@angular/material/select';
import {MatAutocompleteModule} from '@angular/material/autocomplete';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CustomerListComponent } from './customer-list/customer-list.component';
import { ProductListComponent } from './product-list/product-list.component';
import { AddProductComponent } from './add-product/add-product.component';
import { AddCustomerComponent } from './add-customer/add-customer.component';
import { ReceiveLoanComponent } from './receive-loan/receive-loan.component';
import { ReturnLoanComponent } from './return-loan/return-loan.component';

@NgModule({
  declarations: [
    AppComponent,
    CustomerListComponent,
    ProductListComponent,
    AddProductComponent,
    AddCustomerComponent,
    ReceiveLoanComponent,
    ReturnLoanComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatTableModule,
    MatDialogModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatInputModule,
    MatSelectModule,
    MatAutocompleteModule
  ],
  providers: [HttpClientModule, HttpClient],
  bootstrap: [AppComponent]
})
export class AppModule { }

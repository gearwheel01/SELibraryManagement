import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddProductComponent } from './add-product.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog, MatDialogModule  } from '@angular/material/dialog';
import {MatAutocompleteModule} from '@angular/material/autocomplete';

describe('AddProductComponent', () => {
  let component: AddProductComponent;
  let fixture: ComponentFixture<AddProductComponent>;

  const mockDialogRef = {close: jasmine.createSpy('close')};
  const mockDialogData = jasmine.createSpyObj([1]);

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        {
          provide: MatDialogRef,
          useValue: mockDialogRef
        },
        { provide: MAT_DIALOG_DATA, useValue: mockDialogData }
      ],
      imports: [HttpClientTestingModule, MatDialogModule, MatAutocompleteModule],
      declarations: [ AddProductComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

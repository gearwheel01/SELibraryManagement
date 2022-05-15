import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReturnLoanComponent } from './return-loan.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog, MatDialogModule  } from '@angular/material/dialog';
import {MatAutocompleteModule} from '@angular/material/autocomplete';


describe('ReturnLoanComponent', () => {
  let component: ReturnLoanComponent;
  let fixture: ComponentFixture<ReturnLoanComponent>;

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
      declarations: [ ReturnLoanComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReturnLoanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReceiveLoanComponent } from './receive-loan.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog, MatDialogModule  } from '@angular/material/dialog';
import {MatAutocompleteModule} from '@angular/material/autocomplete';

describe('ReceiveLoanComponent', () => {
  let component: ReceiveLoanComponent;
  let fixture: ComponentFixture<ReceiveLoanComponent>;

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
      declarations: [ ReceiveLoanComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReceiveLoanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReceiveLoanComponent } from './receive-loan.component';

describe('ReceiveLoanComponent', () => {
  let component: ReceiveLoanComponent;
  let fixture: ComponentFixture<ReceiveLoanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
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

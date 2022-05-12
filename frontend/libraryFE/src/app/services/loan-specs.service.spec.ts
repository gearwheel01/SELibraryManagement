import { TestBed } from '@angular/core/testing';

import { LoanSpecsService } from './loan-specs.service';

describe('LoanSpecsService', () => {
  let service: LoanSpecsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoanSpecsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

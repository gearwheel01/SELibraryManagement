import { TestBed } from '@angular/core/testing';

import { LoanSpecsService } from './loan-specs.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('LoanSpecsService', () => {
  let service: LoanSpecsService;

  beforeEach(() => {
    TestBed.configureTestingModule({imports: [ HttpClientTestingModule ]});
    service = TestBed.inject(LoanSpecsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

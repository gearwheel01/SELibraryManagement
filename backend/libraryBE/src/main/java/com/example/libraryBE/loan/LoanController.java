package com.example.libraryBE.loan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/loan")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public List<Loan> getLoans() {
        return loanService.getLoans();
    }

    @GetMapping(path="{loanId}")
    public Loan getLoan(@PathVariable("loanId") Long loanId) {
        return loanService.getLoan(loanId);
    }

    @PostMapping
    public void addLoan(@RequestBody Loan loan,
                        @RequestParam Long customerId,
                        @RequestParam String productIsbn) {
        loanService.addLoan(loan, customerId, productIsbn);
    }

    @DeleteMapping(path="{loanId}")
    public void deleteLoan(@PathVariable("loanId") Long loanId) {
        loanService.deleteLoan(loanId);
    }

    @PutMapping(path="{loanId}")
    public void updateLoan(@PathVariable("loanId") Long loanId,
                           @RequestParam(required = false) LocalDate returned) {
        loanService.updateLoan(loanId, returned);
    }

}

package com.example.libraryBE.loan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "api/loan")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping()
    public List<LoanModel> getLoans(@RequestParam(required = false) Long customerId,
                                        @RequestParam(required = false) String productIsbn) {
        return loanService.getLoans(customerId, productIsbn);
    }


    @GetMapping(path="open")
    public List<LoanModel> getOpenLoans(@RequestParam(required = false) Long customerId,
                               @RequestParam(required = false) String productIsbn) {
        return loanService.getOpenLoans(customerId, productIsbn);
    }

    @GetMapping(path="{loanId}")
    public LoanModel getLoan(@PathVariable("loanId") Long loanId) {
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

    @PutMapping()
    public void setLoansReceived(@RequestParam Long loanIds[],
                           @RequestParam
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returned) {
        loanService.setLoansReceived(loanIds, returned);
    }

}

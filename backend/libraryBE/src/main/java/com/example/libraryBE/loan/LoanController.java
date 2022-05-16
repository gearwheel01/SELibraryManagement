package com.example.libraryBE.loan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
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
    public List<LoanModel> getLoansForProduct(@RequestParam String productIsbn) {
        List<Loan> loans = loanService.getLoansForProduct(productIsbn);
        return loanListToLoanModelList(loans);
    }


    @GetMapping(path="open")
    public List<LoanModel> getOpenLoansForProduct(@RequestParam(required = false) String productIsbn) {
        List<Loan> loans = loanService.getOpenLoansForProduct(productIsbn);
        return loanListToLoanModelList(loans);
    }

    @GetMapping(path="{loanId}")
    public LoanModel getLoan(@PathVariable("loanId") Long loanId) {
        return new LoanModel(loanService.getLoan(loanId));
    }

    @PostMapping
    public void addLoan(@RequestBody Loan loan,
                        @RequestParam Long customerId,
                        @RequestParam String productIsbn) {
        loanService.addLoan(loan, customerId, productIsbn);
    }

    @PutMapping()
    public void setLoansReceived(@RequestParam Long loanIds[],
                           @RequestParam
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returned) {
        loanService.setLoansReceived(loanIds, returned);
    }

    public List<LoanModel> loanListToLoanModelList(List<Loan> loans) {
        LinkedList<LoanModel> models = new LinkedList<>();
        loans.forEach(l -> models.push(new LoanModel(l)));
        return models;
    }

}

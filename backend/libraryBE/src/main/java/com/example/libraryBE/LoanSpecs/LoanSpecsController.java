package com.example.libraryBE.LoanSpecs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/loanSpecs")
public class LoanSpecsController {

    private final LoanSpecsService loanSpecsService;

    @Autowired
    public LoanSpecsController(LoanSpecsService loanSpecsService) {
        this.loanSpecsService = loanSpecsService;
    }

    @GetMapping
    public List<LoanSpecs> getLoanSpecs() {
        return loanSpecsService.getLoanSpecs();
    }

    @PostMapping
    public void addLoanSpecs(@RequestBody LoanSpecs loanSpecs) {
        loanSpecsService.addLoanSpecs(loanSpecs);
    }

    @DeleteMapping(path="{loanSpecsId}")
    public void deleteLoanSpecs(@PathVariable("loanSpecsId") Long loanSpecsId) {
        loanSpecsService.deleteLoanSpecs(loanSpecsId);
    }

    @PutMapping(path="{loanSpecsId}")
    public void updateLoanSpecs(@PathVariable("loanSpecsId") Long loanSpecsId,
                               @RequestParam(required = false) String name) {
        loanSpecsService.updateLoanSpecs(loanSpecsId, name);
    }

}

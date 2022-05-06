package com.example.libraryBE.LoanSpecs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class LoanSpecsService {

    private final LoanSpecsRepository loanSpecsRepository;

    @Autowired
    public LoanSpecsService(LoanSpecsRepository loanSpecsRepository) {
        this.loanSpecsRepository = loanSpecsRepository;
    }

    public List<LoanSpecs> getLoanSpecs() {
        return loanSpecsRepository.findAll();
    }

    public void addLoanSpecs(LoanSpecs loanSpecs) {
        Optional<LoanSpecs> loanSpecsOptional = loanSpecsRepository.findLoanSpecsByName(loanSpecs.getName());
        if (loanSpecsOptional.isPresent()) {
            throw new IllegalStateException("loanSpecs with this name already exists");
        }
        loanSpecsRepository.save(loanSpecs);
    }

    public void deleteLoanSpecs(Long loanSpecsId) {
        boolean exists = loanSpecsRepository.existsById(loanSpecsId);
        if (!exists) {
            throw new IllegalStateException("loanSpecs does not exist");
        }
        loanSpecsRepository.deleteById(loanSpecsId);
    }

    @Transactional
    public void updateLoanSpecs(Long loanSpecsId, String name) {
        LoanSpecs loanSpecs = loanSpecsRepository.findById(loanSpecsId).orElseThrow(() -> new IllegalStateException("loanSpecs does not exist"));

        if ( (name != null) && (name.length() > 0) ) {
            Optional<LoanSpecs> loanSpecsOptional = loanSpecsRepository.findLoanSpecsByName(name);
            if (loanSpecsOptional.isPresent()) {
                throw new IllegalStateException("loanSpecs with this name already exists");
            }
            loanSpecs.setName(name);
        }
    }
}

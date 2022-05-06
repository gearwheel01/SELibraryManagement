package com.example.libraryBE.loan;

import com.example.libraryBE.customer.Customer;
import com.example.libraryBE.customer.CustomerRepository;
import com.example.libraryBE.product.Product;
import com.example.libraryBE.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository,
                       CustomerRepository customerRepository,
                       ProductRepository productRepository) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }

    public void addLoan(Loan loan, Long customerId, String productIsbn) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalStateException("assigned customer does not exist"));
        Product product = productRepository.findById(productIsbn).orElseThrow(
                () -> new IllegalStateException("assigned product does not exist"));

        if (!productHasCopyLeft()) {
            throw new IllegalStateException("no more copies of product left");
        }

        loan.setCustomer(customer);
        loan.setProduct(product);

        loanRepository.save(loan);
    }

    public void deleteLoan(Long loanId) {
        boolean exists = loanRepository.existsById(loanId);
        if (!exists) {
            throw new IllegalStateException("loan does not exist");
        }
        loanRepository.deleteById(loanId);
    }

    @Transactional
    public void updateLoan(Long loanId, LocalDate returned) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new IllegalStateException("loan does not exist"));

        if (returned != null) {
            loan.setReturned(returned);
        }
    }

    public Loan getLoan(Long loanId) {
        return loanRepository.findById(loanId).orElseThrow(() ->
                new IllegalStateException("requested loan does not exist"));
    }

    public boolean productHasCopyLeft() {
        return true;
    }

}

package com.example.libraryBE.loan;

import com.example.libraryBE.customer.Customer;
import com.example.libraryBE.customer.CustomerRepository;
import com.example.libraryBE.product.Product;
import com.example.libraryBE.product.ProductRepository;
import com.example.libraryBE.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    public LoanService(LoanRepository loanRepository,
                       CustomerRepository customerRepository,
                       ProductRepository productRepository,
                       ProductService productService) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    public List<LoanModel> getLoans(Long customerId, String productIsbn) {
        List<Loan> loans = loanRepository.findAll();
        if (customerId != null) {
            loans = loans.stream().filter(l -> l.getCustomer().getId() == customerId).collect(Collectors.toList());
        }
        if (productIsbn != null) {
            loans = loans.stream().filter(l -> l.getProduct().getIsbn().equals(productIsbn)).collect(Collectors.toList());
        }

        LinkedList<LoanModel> loanModels = new LinkedList<>();
        loans.forEach(l -> loanModels.add(new LoanModel(l)));
        return loanModels;
    }

    public List<LoanModel> getOpenLoans(Long customerId, String productIsbn) {
        List<Loan> loans = loanRepository.findAll();
        loans = loans.stream().filter(l -> l.getReturned() == null).collect(Collectors.toList());
        if (customerId != null) {
            loans = loans.stream().filter(l -> l.getCustomer().getId() == customerId).collect(Collectors.toList());
        }
        if (productIsbn != null) {
            loans = loans.stream().filter(l -> l.getProduct().getIsbn().equals(productIsbn)).collect(Collectors.toList());
        }

        LinkedList<LoanModel> loanModels = new LinkedList<>();
        loans.forEach(l -> loanModels.add(new LoanModel(l)));
        return loanModels;
    }

    public void addLoan(Loan loan, Long customerId, String productIsbn) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalStateException("assigned customer does not exist"));
        Product product = productRepository.findById(productIsbn).orElseThrow(
                () -> new IllegalStateException("assigned product does not exist"));

        if (productService.getProductAvailableCopies(productIsbn) <= 0) {
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

    public LoanModel getLoan(Long loanId) {
        Loan loan =  loanRepository.findById(loanId).orElseThrow(() ->
                new IllegalStateException("requested loan does not exist"));
        return new LoanModel(loan);
    }

}

package com.example.libraryBE.loan;

import com.example.libraryBE.LoanSpecs.LoanSpecs;
import com.example.libraryBE.LoanSpecs.LoanSpecsRepository;
import com.example.libraryBE.customer.Customer;
import com.example.libraryBE.customer.CustomerRepository;
import com.example.libraryBE.product.Product;
import com.example.libraryBE.product.ProductRepository;
import com.example.libraryBE.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final LoanSpecsRepository loanSpecsRepository;
    private final ProductService productService;

    private final String LOAN_SPECS_STD_NAME = "standard";

    @Autowired
    public LoanService(LoanRepository loanRepository,
                       CustomerRepository customerRepository,
                       ProductRepository productRepository,
                       LoanSpecsRepository loanSpecsRepository,
                       ProductService productService) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.loanSpecsRepository = loanSpecsRepository;
        this.productService = productService;
    }

    public List<Loan> getLoansForProduct(String productIsbn) {
        List<Loan> loans = loanRepository.getLoansForProduct(productIsbn);
        //loans = loans.stream().filter(l -> l.getProduct().getIsbn().equals(productIsbn)).collect(Collectors.toList());
        return loans;
    }

    public List<Loan> getOpenLoansForProduct(String productIsbn) {
        List<Loan> loans = loanRepository.getOpenLoansForProduct(productIsbn);
        return loans;
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

    @Transactional
    public void setLoansReturned(Long loanIds[], LocalDate returned) {
        LoanSpecs specs = loanSpecsRepository.findLoanSpecsByName(LOAN_SPECS_STD_NAME).orElseThrow(() -> new IllegalStateException("no loan specs available"));
        for (Long loanId: loanIds) {
            setLoanReturned(loanId, returned, specs);
        }
    }

    public void setLoanReturned(Long loanId, LocalDate returned, LoanSpecs specs) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new IllegalStateException("loan does not exist"));

        if (returned != null) {
            loan.setReturned(returned);
            long daysInterval = DAYS.between(loan.getReceived(), loan.getReturned());
            if (daysInterval > specs.getLoanPeriodDays()) {
                float newFineValue = loan.getCustomer().getFines() + specs.getFineAmount();
                System.out.println("update fines: " + loan.getCustomer().getFines() + " to " + newFineValue);
                loan.getCustomer().setFines(newFineValue);
                customerRepository.save(loan.getCustomer());
            }
            loanRepository.save(loan);
        }
    }

    public Loan getLoan(Long loanId) {
        Loan loan =  loanRepository.findById(loanId).orElseThrow(() ->
                new IllegalStateException("requested loan does not exist"));
        return loan;
    }

}

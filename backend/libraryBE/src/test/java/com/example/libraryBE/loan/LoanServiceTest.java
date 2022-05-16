package com.example.libraryBE.loan;

import com.example.libraryBE.LoanSpecs.LoanSpecs;
import com.example.libraryBE.LoanSpecs.LoanSpecsRepository;
import com.example.libraryBE.author.AuthorRepository;
import com.example.libraryBE.customer.Customer;
import com.example.libraryBE.customer.CustomerRepository;
import com.example.libraryBE.customer.CustomerService;
import com.example.libraryBE.genre.GenreRepository;
import com.example.libraryBE.product.Product;
import com.example.libraryBE.product.ProductRepository;
import com.example.libraryBE.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    // dependencies (笑)
    @Mock
    private LoanRepository loanRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private LoanSpecsRepository loanSpecsRepository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private AuthorRepository authorRepository;

    private ProductService productService;

    private LoanService loanServiceTest;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, genreRepository, authorRepository, loanRepository);
        loanServiceTest = new LoanService(loanRepository, customerRepository, productRepository, loanSpecsRepository, productService);
    }

    @Test
    void getLoansForProduct() {
        loanServiceTest.getLoansForProduct("asdf");
        verify(loanRepository).getLoansForProduct("asdf");
    }

    @Test
    void getOpenLoansForProduct() {
        loanServiceTest.getOpenLoansForProduct("asdf");
        verify(loanRepository).getOpenLoansForProduct("asdf");
    }

    @Test
    void addLoan() {
        Customer customer = new Customer(1L, "firstName", "lastName", 0);
        Product product = new Product("isbn1", "title1", 1);
        Loan loan = new Loan(1L, customer, product, LocalDate.now(), null);

        doReturn(Optional.of(customer)).when(customerRepository).findById(customer.getId());
        doReturn(Optional.of(product)).when(productRepository).findById(product.getIsbn());
        loanServiceTest.addLoan(loan, customer.getId(), product.getIsbn());

        ArgumentCaptor<Loan> loanArgumentCaptor = ArgumentCaptor.forClass(Loan.class);
        verify(loanRepository).save(loanArgumentCaptor.capture());

        Loan capturedLoan = loanArgumentCaptor.getValue();
        assertThat(capturedLoan).isEqualTo(loan);
    }

    @Test
    void setLoanReturned() {
        float startFines = 0;
        Customer customer = new Customer(1L, "firstName", "lastName", startFines);
        Product product = new Product("isbn1", "title1", 1);
        LoanSpecs specs = new LoanSpecs("standard", 30, 10);
        Loan loan = new Loan(1L, customer, product, LocalDate.now(), null);
        doReturn(Optional.of(loan)).when(loanRepository).findById(loan.getId());

        LocalDate returned = LocalDate.of(10000, 1, 1);

        loanServiceTest.setLoanReturned(loan.getId(), returned, specs);

        ArgumentCaptor<Loan> loanArgumentCaptor = ArgumentCaptor.forClass(Loan.class);
        verify(loanRepository).save(loanArgumentCaptor.capture());
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerArgumentCaptor.capture());

        Loan capturedLoan = loanArgumentCaptor.getValue();
        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertThat(capturedLoan.getReturned()).isEqualTo(returned);
        assertThat(capturedCustomer.getFines()).isEqualTo(startFines + specs.getFineAmount());
    }

    @Test
    void getLoan() {
        Customer customer = new Customer(1L, "firstName", "lastName", 0);
        Product product = new Product("isbn1", "title1", 1);
        Loan loan = new Loan(1L, customer, product, LocalDate.now(), null);
        doReturn(Optional.of(loan)).when(loanRepository).findById(loan.getId());
        loanServiceTest.getLoan(loan.getId());
        verify(loanRepository).findById(loan.getId());
    }
}
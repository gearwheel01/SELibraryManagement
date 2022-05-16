package com.example.libraryBE.loan;

import com.example.libraryBE.customer.Customer;
import com.example.libraryBE.customer.CustomerRepository;
import com.example.libraryBE.product.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LoanRepositoryTest {

    @Autowired
    private LoanRepository loanRepositoryTest;

    @AfterEach
    void tearDown() {
        loanRepositoryTest.deleteAll();
    }

    @Test
    void getLoansForProduct() {
        Customer c = new Customer("firstName", "lastName", "mail");
        Product p1 = new Product("isbn1", "title1", 1);
        Product p2 = new Product("isbn2", "title2", 1);

        Loan l1 = new Loan(1l, c, p1, LocalDate.now(), null);
        Loan l2 = new Loan(2l, c, p1, LocalDate.now(), null);
        Loan l3 = new Loan(3l, c, p2, LocalDate.now(), null);
        loanRepositoryTest.saveAll(List.of(l1, l2, l3));

        List<Loan> test = loanRepositoryTest.getLoansForProduct(l1.getProduct().getIsbn());
        LinkedList<Long> testLoanIds = new LinkedList<>();
        test.forEach(t -> testLoanIds.push(t.getId()));

        assertThat(test.size()).isEqualTo(2);
        assertThat(testLoanIds.contains(l1.getId())).isTrue();
        assertThat(testLoanIds.contains(l2.getId())).isTrue();
        assertThat(testLoanIds.contains(l3.getId())).isFalse();
    }

    @Test
    void getOpenLoansForProduct() {
        Customer c = new Customer("firstName", "lastName", "mail");

        Product p1 = new Product("isbn1", "title1", 1);
        Product p2 = new Product("isbn2", "title2", 1);

        Loan l1 = new Loan(1l, c, p1, LocalDate.now(), null);
        Loan l2 = new Loan(2l, c, p1, LocalDate.now(), null);
        Loan l3 = new Loan(3l, c, p2, LocalDate.now(), null);
        Loan l4 = new Loan(4l, c, p1, LocalDate.now(), LocalDate.now());
        loanRepositoryTest.saveAll(List.of(l1, l2, l3, l4));

        List<Loan> test = loanRepositoryTest.getOpenLoansForProduct(p1.getIsbn());
        LinkedList<Long> testLoanIds = new LinkedList<>();
        test.forEach(t -> testLoanIds.push(t.getId()));

        assertThat(test.size()).isEqualTo(2);
        assertThat(testLoanIds.contains(l1.getId())).isTrue();
        assertThat(testLoanIds.contains(l2.getId())).isTrue();
        assertThat(testLoanIds.contains(l3.getId())).isFalse();
        assertThat(testLoanIds.contains(l4.getId())).isFalse();
    }
}
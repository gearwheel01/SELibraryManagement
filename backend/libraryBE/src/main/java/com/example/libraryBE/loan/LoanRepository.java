package com.example.libraryBE.loan;

import com.example.libraryBE.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("SELECT l FROM Loan l WHERE l.product.isbn = ?1")
    List<Loan> getLoansForProduct(String productIsbn);

    @Query("SELECT l FROM Loan l WHERE l.returned = null AND l.product.isbn = ?1")
    List<Loan> getOpenLoansForProduct(String productIsbn);

}

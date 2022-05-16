package com.example.libraryBE.product;

import com.example.libraryBE.genre.Genre;
import com.example.libraryBE.loan.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

}

package com.example.libraryBE.LoanSpecs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanSpecsRepository extends JpaRepository<LoanSpecs, Long> {

    @Query("SELECT l FROM LoanSpecs l WHERE l.name = ?1")
    Optional<LoanSpecs> findLoanSpecsByName(String name);
}

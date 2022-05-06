package com.example.libraryBE.LoanSpecs;

import javax.persistence.*;

@Entity
@Table
public class LoanSpecs {

    @Id
    @SequenceGenerator(
            name = "loanSpecs_sequence",
            sequenceName = "loanSpecs_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "loanSpecs_sequence"
    )
    private Long id;

    private String name;
    private int loanPeriodDays;
    private float fineAmount;

    public LoanSpecs() {
    }

    public LoanSpecs(String name, int loanPeriodDays, float fineAmount) {
        this.name = name;
        this.loanPeriodDays = loanPeriodDays;
        this.fineAmount = fineAmount;
    }

    public LoanSpecs(Long id, String name, int loanPeriodDays, float fineAmount) {
        this.id = id;
        this.name = name;
        this.loanPeriodDays = loanPeriodDays;
        this.fineAmount = fineAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLoanPeriodDays() {
        return loanPeriodDays;
    }

    public void setLoanPeriodDays(int loanPeriodDays) {
        this.loanPeriodDays = loanPeriodDays;
    }

    public float getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(float fineAmount) {
        this.fineAmount = fineAmount;
    }
}

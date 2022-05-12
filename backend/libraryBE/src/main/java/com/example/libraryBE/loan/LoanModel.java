package com.example.libraryBE.loan;

import java.time.LocalDate;

public class LoanModel {

    private Long id;
    private LocalDate received;
    private LocalDate returned;
    private Long customerId;
    private String productIsbn;

    public LoanModel(Loan loan) {
        this.id = loan.getId();
        this.received = loan.getReceived();
        this.returned = loan.getReturned();
        this.customerId = loan.getCustomer().getId();
        this.productIsbn = loan.getProduct().getIsbn();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReceived() {
        return received;
    }

    public void setReceived(LocalDate received) {
        this.received = received;
    }

    public LocalDate getReturned() {
        return returned;
    }

    public void setReturned(LocalDate returned) {
        this.returned = returned;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getProductIsbn() {
        return productIsbn;
    }

    public void setProductIsbn(String productIsbn) {
        this.productIsbn = productIsbn;
    }
}

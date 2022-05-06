package com.example.libraryBE.loan;

import com.example.libraryBE.customer.Customer;
import com.example.libraryBE.product.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Loan {

    @Id
    @SequenceGenerator(
            name = "loan_sequence",
            sequenceName = "loan_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "loan_sequence"
    )
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productIsbn")
    private Product product;

    private LocalDate received;
    private LocalDate returned;

    public Loan() {
    }

    public Loan(Long id, Customer customer, Product product, LocalDate received, LocalDate returned) {
        this.id = id;
        this.customer = customer;
        this.product = product;
        this.received = received;
        this.returned = returned;
    }

    public Loan(Customer customer, Product product, LocalDate received, LocalDate returned) {
        this.customer = customer;
        this.product = product;
        this.received = received;
        this.returned = returned;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", customer=" + customer.getId() +
                ", product=" + product.getIsbn() +
                ", received=" + received +
                ", returned=" + returned +
                '}';
    }
}

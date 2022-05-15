package com.example.libraryBE.customer;

import com.example.libraryBE.Name;
import com.example.libraryBE.loan.Loan;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table
public class Customer {

    @Id
    @SequenceGenerator(
            name = "custommer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_sequence"
    )
    private long id;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private Collection<Loan> loans;

    @Embedded
    private Name name;

    private String email;
    private LocalDate birth;
    private float fines;


    public Customer() {
    }

    public Customer(String firstName, String lastName, String email) {
        this.name = new Name(firstName, lastName);
        this.email = email;
    }

    public Customer(long id, String firstName, String lastName, float fines) {
        this.id = id;
        this.name = new Name(firstName, lastName);
        this.fines = fines;
    }

    public Customer(long id, Collection<Loan> loans, String firstName, String lastName, String email, LocalDate birth, float fines) {
        this.id = id;
        this.loans = loans;
        this.name = new Name(firstName, lastName);
        this.email = email;
        this.birth = birth;
        this.fines = fines;
    }

    public Customer(Collection<Loan> loans, String firstName, String lastName, String email, LocalDate birth, float fines) {
        this.loans = loans;
        this.name = new Name(firstName, lastName);
        this.email = email;
        this.birth = birth;
        this.fines = fines;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return name.getFirstName();
    }
    public void setName(String firstName, String lastName) {
        this.name = new Name(firstName, lastName);
    }

    public String getLastName() {
        return name.getLastName();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public float getFines() {
        return fines;
    }

    public void setFines(float fines) {
        this.fines = fines;
    }

    public Collection<Loan> getLoans() {
        return loans;
    }

    public void setLoans(Collection<Loan> loans) {
        this.loans = loans;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + name.getFirstName() + '\'' +
                ", lastName='" + name.getLastName() + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                ", fines=" + fines +
                '}';
    }
}

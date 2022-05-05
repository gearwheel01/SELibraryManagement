package com.example.libraryBE.customer;

import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.time.LocalDate;

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

    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birth;
    private float fines;

    public Customer() {
    }

    public Customer(long id, String firstName, String lastName, String email, LocalDate birth, float fines) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birth = birth;
        this.fines = fines;
    }

    public Customer(String firstName, String lastName, String email, LocalDate birth, float fines) {
        this.firstName = firstName;
        this.lastName = lastName;
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
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                ", fines=" + fines +
                '}';
    }
}

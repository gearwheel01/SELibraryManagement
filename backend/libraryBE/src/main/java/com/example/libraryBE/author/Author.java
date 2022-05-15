package com.example.libraryBE.author;

import com.example.libraryBE.Name;
import com.example.libraryBE.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table
public class Author {

    @Id
    @SequenceGenerator(
            name = "author_sequence",
            sequenceName = "author_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "author_sequence"
    )
    private long id;

    @Embedded
    private Name name;
    private LocalDate birth;

    @JsonIgnore
    @ManyToMany(mappedBy = "authors")
    private Collection<Product> products;

    public Author() {
    }

    public Author(String firstName, String lastName, LocalDate birth, Collection<Product> products) {
        this.name = new Name(firstName, lastName);
        this.birth = birth;
        this.products = products;
    }

    public Author(long id, String firstName, String lastName, LocalDate birth, Collection<Product> products) {
        this.id = id;
        this.name = new Name(firstName, lastName);
        this.birth = birth;
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String firstName, String lastName) {
        this.name = new Name(firstName, lastName);
    }

    public String getFirstName() {
        return name.getFirstName();
    }

    public String getLastName() {
        return name.getLastName();
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + name.getFirstName() + '\'' +
                ", lastName='" + name.getLastName() + '\'' +
                ", birth=" + birth +
                '}';
    }
}

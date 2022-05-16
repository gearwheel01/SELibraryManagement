package com.example.libraryBE.genre;

import com.example.libraryBE.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table
public class Genre {

    @Id
    @SequenceGenerator(
            name = "genre_sequence",
            sequenceName = "genre_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "genre_sequence"
    )
    private Long id;

    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "genres")
    private Collection<Product> products;

    public Genre() {
    }

    public Genre(String name, Collection<Product> products) {
        this.name = name;
        this.products = products;
    }

    public Genre(Long id, String name, Collection<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products;
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

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

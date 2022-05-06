package com.example.libraryBE.product;

import com.example.libraryBE.author.Author;
import com.example.libraryBE.genre.Genre;
import com.example.libraryBE.loan.Loan;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table
public class Product {

    @Id
    private String isbn;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Collection<Loan> loans;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_genres",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Collection<Genre> genres;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_authors",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Collection<Author> authors;


    private String title;
    private LocalDate publication;
    private String publisher;
    private int copies;

    public Product() {
    }

    public Product(String isbn, Collection<Loan> loans, Collection<Genre> genres, Collection<Author> authors, String title, LocalDate publication, String publisher, int copies) {
        this.isbn = isbn;
        this.loans = loans;
        this.genres = genres;
        this.authors = authors;
        this.title = title;
        this.publication = publication;
        this.publisher = publisher;
        this.copies = copies;
    }

    public Product(Collection<Loan> loans, Collection<Genre> genres, Collection<Author> authors, String title, LocalDate publication, String publisher, int copies) {
        this.loans = loans;
        this.genres = genres;
        this.authors = authors;
        this.title = title;
        this.publication = publication;
        this.publisher = publisher;
        this.copies = copies;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }
    
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDate getPublication() {
        return publication;
    }

    public void setPublication(LocalDate publication) {
        this.publication = publication;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public Collection<Loan> getLoans() {
        return loans;
    }

    public void setLoans(Collection<Loan> loans) {
        this.loans = loans;
    }

    public Collection<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Collection<Genre> genres) {
        this.genres = genres;
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Collection<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "Product{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", publication=" + publication +
                ", copies=" + copies +
                '}';
    }
}

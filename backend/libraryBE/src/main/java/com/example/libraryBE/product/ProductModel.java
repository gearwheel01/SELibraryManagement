package com.example.libraryBE.product;

import com.example.libraryBE.author.Author;
import com.example.libraryBE.genre.Genre;

import java.time.LocalDate;
import java.util.Collection;

public class ProductModel {

    private String isbn;
    private Collection<Genre> genres;
    private Collection<Author> authors;
    private String title;
    private LocalDate publication;
    private String publisher;
    private int copies;
    private int remainingCopies;

    public ProductModel(Product product) {
        this.isbn = product.getIsbn();
        this.genres = product.getGenres();
        this.authors = product.getAuthors();
        this.title = product.getTitle();
        this.publication = product.getPublication();
        this.publisher = product.getPublisher();
        this.copies = product.getCopies();
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublication() {
        return publication;
    }

    public void setPublication(LocalDate publication) {
        this.publication = publication;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public int getRemainingCopies() {
        return remainingCopies;
    }

    public void setRemainingCopies(int remainingCopies) {
        this.remainingCopies = remainingCopies;
    }
}

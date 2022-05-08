package com.example.libraryBE.product;

import com.example.libraryBE.author.Author;
import com.example.libraryBE.genre.Genre;
import com.example.libraryBE.loan.Loan;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

public class ProductModel {

    private String isbn;
    private Collection<Genre> genreIds;
    private Collection<Author> authorIds;
    private String title;
    private LocalDate publication;
    private String publisher;
    private int copies;

}

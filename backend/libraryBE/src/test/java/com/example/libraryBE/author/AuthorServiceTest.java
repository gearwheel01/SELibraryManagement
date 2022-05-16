package com.example.libraryBE.author;

import com.example.libraryBE.customer.Customer;
import com.example.libraryBE.customer.CustomerRepository;
import com.example.libraryBE.customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;
    private AuthorService authorServiceTest;

    @BeforeEach
    void setUp() {
        authorServiceTest = new AuthorService(authorRepository);
    }

    @Test
    void getAuthors() {
        authorServiceTest.getAuthors();
        verify(authorRepository).findAll();
    }

    @Test
    void addAuthor() {
        Author author = new Author("Vorname", "Nachname");
        authorServiceTest.addAuthor(author, "Vorname", "Nachname");

        ArgumentCaptor<Author> authorArgumentCaptor = ArgumentCaptor.forClass(Author.class);
        verify(authorRepository).save(authorArgumentCaptor.capture());

        Author capturedAuthor = authorArgumentCaptor.getValue();
        assertThat(capturedAuthor).isEqualTo(author);
    }

    @Test
    void deleteAuthor() {
        Author author = new Author(1, "Vorname", "Nachname", LocalDate.now(), null);
        given(authorRepository.existsById(author.getId())).willReturn(true);

        authorServiceTest.deleteAuthor(author.getId());

        verify(authorRepository).deleteById(author.getId());
    }
}
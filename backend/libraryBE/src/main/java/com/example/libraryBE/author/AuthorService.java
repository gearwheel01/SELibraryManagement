package com.example.libraryBE.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public void addAuthor(Author author) {
        authorRepository.save(author);
    }

    public void deleteAuthor(Long authorId) {
        boolean exists = authorRepository.existsById(authorId);
        if (!exists) {
            throw new IllegalStateException("author does not exist");
        }
        authorRepository.deleteById(authorId);
    }

    @Transactional
    public void updateAuthor(Long authorId, String firstName, String lastName) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new IllegalStateException("author does not exist"));

        if ( (firstName != null) && (firstName.length() > 0) ) {
            author.setFirstName(firstName);
        }
        if ( (lastName != null) && (lastName.length() > 0) ) {
            author.setLastName(lastName);
        }
    }
}

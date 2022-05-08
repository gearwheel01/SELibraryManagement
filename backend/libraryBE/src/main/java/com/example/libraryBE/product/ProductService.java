package com.example.libraryBE.product;

import com.example.libraryBE.author.Author;
import com.example.libraryBE.author.AuthorRepository;
import com.example.libraryBE.genre.Genre;
import com.example.libraryBE.genre.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          GenreRepository genreRepository,
                          AuthorRepository authorRepository) {
        this.productRepository = productRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void addProduct(Product product) {
        Optional<Product> productOptional = productRepository.findById(product.getIsbn());
        if (productOptional.isPresent()) {
            throw new IllegalStateException("product with this isbn already exists");
        }
        productRepository.save(product);
    }

    public void deleteProduct(String productIsbn) {
        boolean exists = productRepository.existsById(productIsbn);
        if (!exists) {
            throw new IllegalStateException("product does not exist");
        }
        productRepository.deleteById(productIsbn);
    }

    @Transactional
    public void updateProduct(String productIsbn, String title, String publisher, LocalDate publication, Integer copies,
                              Long[] addGenre, Long[] removeGenre, Long[] addAuthor, Long[] removeAuthor) {
        Product product = productRepository.findById(productIsbn).orElseThrow(() -> new IllegalStateException("product does not exist"));

        if ( (title != null) && (title.length() > 0) ) {
            product.setTitle(title);
        }
        if ( (publisher != null) && (publisher.length() > 0) ) {
            product.setPublisher(publisher);
        }
        if (publication != null) {
            product.setPublication(publication);
        }
        if ( (copies != null) && (copies > 0) ) {
            product.setCopies(copies);
        }

        if ( (addGenre != null) && (addGenre.length > 0) ) {
            for (Long genreId : addGenre) {
                Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new IllegalStateException("added genre does not exist"));
                if (product.getGenres().contains(genre)) {
                    throw new IllegalStateException("added genre aready assigned");
                }
                product.addGenre(genre);
            }
        }

        if ( (removeGenre != null) && (removeGenre.length > 0) ) {
            for (Long genreId : removeGenre) {
                Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new IllegalStateException("removed genre does not exist"));
                if (!product.getGenres().contains(genre)) {
                    throw new IllegalStateException("removed genre not assigned");
                }
                product.removeGenre(genre);
            }
        }

        if ( (addAuthor != null) && (addAuthor.length > 0) ) {
            for (Long authorId : addAuthor) {
                Author author = authorRepository.findById(authorId).orElseThrow(() -> new IllegalStateException("added author does not exist"));
                if (product.getAuthors().contains(author)) {
                    throw new IllegalStateException("added author aready assigned");
                }
                product.addAuthor(author);
            }
        }

        if ( (removeAuthor != null) && (removeAuthor.length > 0) ) {
            for (Long authorId : removeAuthor) {
                Author author = authorRepository.findById(authorId).orElseThrow(() -> new IllegalStateException("removed author does not exist"));
                if (!product.getAuthors().contains(author)) {
                    throw new IllegalStateException("removed author not assigned");
                }
                product.removeAuthor(author);
            }
        }
    }

    public Product getProduct(String productIsbn) {
        return productRepository.findById(productIsbn).orElseThrow(() ->
                new IllegalStateException("requested product does not exist"));
    }
}

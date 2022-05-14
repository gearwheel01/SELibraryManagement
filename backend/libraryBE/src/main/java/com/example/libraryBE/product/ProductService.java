package com.example.libraryBE.product;

import com.example.libraryBE.author.Author;
import com.example.libraryBE.author.AuthorRepository;
import com.example.libraryBE.genre.Genre;
import com.example.libraryBE.genre.GenreRepository;
import com.example.libraryBE.loan.Loan;
import com.example.libraryBE.loan.LoanModel;
import com.example.libraryBE.loan.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final LoanRepository loanRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          GenreRepository genreRepository,
                          AuthorRepository authorRepository,
                          LoanRepository loanRepository) {
        this.productRepository = productRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.loanRepository = loanRepository;
    }

    public List<ProductModel> getProducts() {
        List<Product> products = productRepository.findAll();
        LinkedList<ProductModel> productModels = new LinkedList<>();
        products.forEach(p -> {
            ProductModel model = new ProductModel(p);
            model.setRemainingCopies(getProductAvailableCopies(p.getIsbn()));
            productModels.add(model);
            });
        return productModels;
    }

    public void addProduct(Product product,
                           Long[] addGenreIds, String[] addGenreNames,
                           Long[] addAuthorIds, String[] addAuthorFirstNames, String[] addAuthorLastNames, LocalDate[] addAuthorBirths) {
        Optional<Product> productOptional = productRepository.findById(product.getIsbn());
        if (productOptional.isPresent()) {
            throw new IllegalStateException("product with this isbn already exists");
        }

        if ( (addGenreIds != null) && (addGenreIds.length > 0) ) {
            addGenreIdsToProduct(product, addGenreIds);
        }
        if ( (addGenreNames != null) && (addGenreNames.length > 0) ) {
            for (String name: addGenreNames) {
                Genre newGenre = new Genre(name, null);
                product.addGenre(newGenre);
            }
        }

        if ( (addAuthorIds != null) && (addAuthorIds.length > 0) ) {
            addAuthorIdsToProduct(product, addAuthorIds);
        }
        if ( (addAuthorFirstNames != null) && (addAuthorFirstNames.length > 0) ) {
            for (int i = 0; i < addAuthorFirstNames.length; i += 1) {
                Author newAuthor = new Author(addAuthorFirstNames[i],
                                                addAuthorLastNames[i],
                                                addAuthorBirths[i],null);
                product.addAuthor(newAuthor);
            }
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
            addGenreIdsToProduct(product, addGenre);
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
            addAuthorIdsToProduct(product, addAuthor);
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

    public void addGenreIdsToProduct(Product p, Long[] gIds) {
        for (Long genreId : gIds) {
            Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new IllegalStateException("added genre does not exist"));
            if ( (p.getGenres() != null) && (p.getGenres().contains(genre)) ) {
                throw new IllegalStateException("added genre aready assigned");
            }
            p.addGenre(genre);
        }
    }

    public void addAuthorIdsToProduct(Product p, Long[] aIds) {
        for (Long authorId : aIds) {
            Author author = authorRepository.findById(authorId).orElseThrow(() -> new IllegalStateException("added author does not exist"));
            if ( (p.getAuthors() != null) && (p.getAuthors().contains(author)) ) {
                throw new IllegalStateException("added author aready assigned");
            }
            p.addAuthor(author);
        }
    }

    public ProductModel getProduct(String productIsbn) {
        Product product = productRepository.findById(productIsbn).orElseThrow(() ->
                new IllegalStateException("requested product does not exist"));
        ProductModel model = new ProductModel(product);
        model.setRemainingCopies(getProductAvailableCopies(productIsbn));
        return model;
    }

    public int getProductAvailableCopies(String productIsbn) {
        Product product = productRepository.findById(productIsbn).orElseThrow(() -> new IllegalStateException("product does not exist"));
        Collection<Loan> currentLoans = loanRepository.findAll();
        currentLoans = currentLoans.stream().filter(l -> ( (l.getProduct().getIsbn().equals(product.getIsbn())) &&
                                            (l.getReturned() == null) )).collect(Collectors.toList());

        return product.getCopies() - currentLoans.size();
    }
}

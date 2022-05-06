package com.example.libraryBE.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
    public void updateProduct(String productIsbn, String title, String publisher, LocalDate publication, Integer copies) {
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
    }

    public Product getProduct(String productIsbn) {
        return productRepository.findById(productIsbn).orElseThrow(() ->
                new IllegalStateException("requested product does not exist"));
    }
}

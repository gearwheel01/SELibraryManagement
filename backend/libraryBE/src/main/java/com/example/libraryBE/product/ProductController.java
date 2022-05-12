package com.example.libraryBE.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductModel> getProducts() {
        return productService.getProducts();
    }

    @GetMapping(path="{productId}")
    public ProductModel getProduct(@PathVariable("productId") String productIsbn) {
        return productService.getProduct(productIsbn);
    }

    @GetMapping(path="{productId}/availableCopies")
    public int getProductAvailableCopies(@PathVariable("productId") String productIsbn) {
        return productService.getProductAvailableCopies(productIsbn);
    }

    @PostMapping
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }

    @DeleteMapping(path="{productId}")
    public void deleteProduct(@PathVariable("productId") String productIsbn) {
        productService.deleteProduct(productIsbn);
    }

    @PutMapping(path="{productId}")
    public void updateProduct(@PathVariable("productId") String productIsbn,
                               @RequestParam(required = false) String title,
                               @RequestParam(required = false) String publisher,
                               @RequestParam(required = false) LocalDate publication,
                               @RequestParam(required = false) Integer copies,

                               @RequestParam(required = false) Long[] addGenre,
                               @RequestParam(required = false) Long[] removeGenre,
                               @RequestParam(required = false) Long[] addAuthor,
                               @RequestParam(required = false) Long[] removeAuthor
                              ) {
        productService.updateProduct(productIsbn, title, publisher, publication, copies, addGenre, removeGenre, addAuthor, removeAuthor);
    }

}

package com.example.libraryBE.product;

import com.example.libraryBE.author.Author;
import com.example.libraryBE.genre.Genre;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class ProductConfig {

    @Bean
    CommandLineRunner commandLineRunnerProduct(ProductRepository repository) {
        return args -> {
            Author a1 = new Author(1, "George", "Orwell", LocalDate.of(1903, 6, 25), null);
            Author a2 = new Author(2, "Junji", "Itō", LocalDate.of(1963, 7, 31), null);

            Genre g1 = new Genre(1L, "Dystopie", null);
            Genre g2 = new Genre(2L, "Horror", null);

            Product p1 = new Product("978-3548234106", null, List.of(g1), List.of(a1), "1984", LocalDate.of(1994,6,1), "Ullstein", 3);
            Product p2 = new Product("978-3551757524", null, List.of(g2), List.of(a2), "Uzumaki", LocalDate.of(2019,6,8), "Carlsen", 27);

            repository.saveAll(List.of(p1,p2));
        };
    }
}

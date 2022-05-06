package com.example.libraryBE.product;

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
            Product p1 = new Product("978-3548234106", null, null, null, "1984", LocalDate.of(1994,6,1), "Ullstein", 3);
            Product p2 = new Product("978-3551757524", null, null, null, "Uzumaki", LocalDate.of(2019,6,8), "Carlsen", 27);

            repository.saveAll(List.of(p1,p2));
        };
    }
}

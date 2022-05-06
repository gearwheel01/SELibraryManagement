package com.example.libraryBE.author;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class AuthorConfig {

    @Bean
    CommandLineRunner commandLineRunnerAuthor(AuthorRepository repository) {
        return args -> {
            Author a1 = new Author(1, "Lars", "Hudalla", LocalDate.now());
            Author a2 = new Author(2, "Gear", "Wheel", LocalDate.now());

            repository.saveAll(List.of(a1,a2));
        };
    }

}

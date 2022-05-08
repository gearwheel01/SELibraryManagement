package com.example.libraryBE.genre;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class GenreConfig {

    @Bean
    CommandLineRunner commandLineRunnerGenre(GenreRepository repository) {
        return args -> {
            Genre g1 = new Genre(1L, "Dystopie", null);
            Genre g2 = new Genre(2L, "Horror", null);

            repository.saveAll(List.of(g1,g2));
        };
    }
}

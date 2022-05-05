package com.example.libraryBE.customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class CustomerConfig {

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository repository) {
        return args -> {
            Customer me = new Customer(1, "Lars", "Hudalla", "lh@asdf.de", LocalDate.now(), 0f);
            Customer notme = new Customer(2, "Gear", "Wheel", "gh@asdf.de", LocalDate.now(), 50f);

            repository.saveAll(List.of(me,notme));
        };
    }

}

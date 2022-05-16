package com.example.libraryBE.customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class CustomerConfig {

    @Bean
    CommandLineRunner commandLineRunnerCustomer(CustomerRepository repository) {
        return args -> {
            Customer c1 = new Customer(1, null,  "Lars", "Hudalla", "lh@asdf.de", LocalDate.now(), 0f);
            Customer c2 = new Customer(2, null,  "Gear", "Wheel", "gh@asdf.de", LocalDate.now(), 50f);

            repository.saveAll(List.of(c1,c2));
        };
    }

}

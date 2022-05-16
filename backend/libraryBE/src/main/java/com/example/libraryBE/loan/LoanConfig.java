package com.example.libraryBE.loan;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LoanConfig {

    @Bean
    CommandLineRunner commandLineRunnerLoan(LoanRepository repository) {
        return args -> {
        };
    }
}

package com.example.libraryBE.LoanSpecs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LoanSpecsConfig {

    @Bean
    CommandLineRunner commandLineRunnerLoanSpecs(LoanSpecsRepository repository) {
        return args -> {
            LoanSpecs ls1 = new LoanSpecs(1L, "standard", 30, 10f);

            repository.saveAll(List.of(ls1));
        };
    }
}

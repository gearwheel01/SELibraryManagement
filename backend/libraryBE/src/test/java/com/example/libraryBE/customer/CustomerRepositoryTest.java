package com.example.libraryBE.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepositoryTest;

    @AfterEach
    void tearDown() {
        customerRepositoryTest.deleteAll();
    }

    @Test
    void findCustomerByEmailExistent() {
        String email = "email@domain.com";
        Customer customer = new Customer("Vorname", "Nachname", email);
        customerRepositoryTest.save(customer);

        Optional<Customer> test = customerRepositoryTest.findCustomerByEmail(email);

        assertThat(test.isPresent()).isTrue();
        assertThat(test.get().getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(test.get().getLastName()).isEqualTo(customer.getLastName());
    }

    @Test
    void findCustomerByEmailNonexistent() {
        String email = "email@domain.com";
        Customer customer = new Customer("Vorname", "Nachname", email);
        customerRepositoryTest.save(customer);

        Optional<Customer> test = customerRepositoryTest.findCustomerByEmail(email + "asdf");

        assertThat(test.isPresent()).isFalse();
    }

    @Test
    void findCustomerByEmailExists() {
        String email = "email@domain.com";
        Customer customer = new Customer("Vorname", "Nachname", email);
        customerRepositoryTest.save(customer);

        boolean test = customerRepositoryTest.customerByEmailExists(email);

        assertThat(test).isTrue();
    }

    @Test
    void findCustomerByEmailDoesNotExist() {
        String email = "email@domain.com";
        Customer customer = new Customer("Vorname", "Nachname", email);
        customerRepositoryTest.save(customer);

        boolean test = customerRepositoryTest.customerByEmailExists(email + "asdf");

        assertThat(test).isFalse();
    }
}

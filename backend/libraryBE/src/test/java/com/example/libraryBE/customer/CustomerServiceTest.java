package com.example.libraryBE.customer;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    private CustomerService customerServiceTest;

    @BeforeEach
    void setUp() {
        customerServiceTest = new CustomerService(customerRepository);
    }


    @Test
    void getCustomers() {
        customerServiceTest.getCustomers();
        verify(customerRepository).findAll();
    }

    @Test
    void addCustomer() {
        Customer customer = new Customer("Vorname", "Nachname", "mail@domain.com");
        customerServiceTest.addCustomer(customer, "Vorname", "Nachname");

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertThat(capturedCustomer).isEqualTo(customer);
    }

    @Test
    void addCustomerThrowWhenDuplicateEmail() {
        String email = "mail@domain.com";
        Customer customer = new Customer("", "", email);

        given(customerRepository.customerByEmailExists(email)).willReturn(true);

        assertThatThrownBy(() -> customerServiceTest.addCustomer(customer, "Vorname", "Nachname"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("email already taken");

        verify(customerRepository,never()).save(any());
    }

    @Test
    void deleteCustomer() {
        Customer customer = new Customer(1, "Vorname", "Nachname", 0f);
        given(customerRepository.existsById(customer.getId())).willReturn(true);

        customerServiceTest.deleteCustomer(customer.getId());

        verify(customerRepository).deleteById(customer.getId());
    }

    @Test
    void deleteCustomerThrowsWhenNotExisting() {
        Customer customer = new Customer(1, "Vorname", "Nachname", 0f);
        given(customerRepository.existsById(customer.getId())).willReturn(false);

        assertThatThrownBy(() -> customerServiceTest.deleteCustomer(customer.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("customer does not exist");
    }

    @Test
    void updateCustomer() {
        Customer customer = new Customer(1, "Vorname", "Nachname", 0f);
        doReturn(Optional.of(customer)).when(customerRepository).findById(customer.getId());

        String newEmail = "neumail@domain.com";
        float newFines = customer.getFines() + 10;

        customerServiceTest.updateCustomer(customer.getId(), newEmail, newFines);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerArgumentCaptor.capture());

        Customer captured = customerArgumentCaptor.getValue();
        assertThat(captured.getEmail()).isEqualTo(newEmail);
        assertThat(captured.getFines()).isEqualTo(newFines);
    }

    @Test
    void updateCustomerFailsWhenDoesntExist() {
        Customer customer = new Customer(1, "Vorname", "Nachname", 0f);

        String newEmail = "neumail@domain.com";
        float newFines = customer.getFines() + 10;

        assertThatThrownBy(() -> customerServiceTest.updateCustomer(customer.getId(), newEmail, newFines))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("customer does not exist");

        verify(customerRepository,never()).save(any());
    }
}
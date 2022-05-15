package com.example.libraryBE.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public void addCustomer(Customer customer) {
        if (customerRepository.customerByEmailExists(customer.getEmail())) {
            throw new IllegalStateException("email already taken");
        }
        customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId) {
        boolean exists = customerRepository.existsById(customerId);
        if (!exists) {
            throw new IllegalStateException("customer does not exist");
        }
        customerRepository.deleteById(customerId);
    }

    @Transactional
    public void updateCustomer(Long customerId, String firstName, String lastName, String email, Float fines) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalStateException("customer does not exist"));

        if ( (firstName != null) && (firstName.length() > 0) ) {
            customer.setFirstName(firstName);
        }
        if ( (lastName != null) && (lastName.length() > 0) ) {
            customer.setLastName(lastName);
        }
        if ( (email != null) && (email.length() > 0) ) {
            Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(email);
            if (customerOptional.isPresent()) {
                throw new IllegalStateException("email already taken");
            }
            customer.setEmail(email);
        }
        if (fines != null) {
            customer.setFines(fines);
        }

        customerRepository.save(customer);
    }
}

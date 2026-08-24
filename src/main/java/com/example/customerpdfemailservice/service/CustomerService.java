package com.example.customerpdfemailservice.service;

import com.example.customerpdfemailservice.entity.Customer;
import com.example.customerpdfemailservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private static final Pattern GMAIL_PATTERN =
            Pattern.compile(
                    "^[A-Za-z0-9._%+-]+@gmail\\.com$"
            );

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> findByCnic(String cnic) {
        return customerRepository.findByCnic(cnic);
    }

    public boolean isValidGmail(String email) {

        if (email == null || email.isBlank()) {
            return false;
        }

        return GMAIL_PATTERN.matcher(email.trim()).matches();
    }

    private static final Pattern CNIC_PATTERN =
            Pattern.compile("^\\d{5}-\\d{7}-\\d{1}$");

    public boolean isValidCnic(String cnic) {

        if (cnic == null || cnic.isBlank()) {
            return false;
        }

        return CNIC_PATTERN.matcher(cnic.trim()).matches();
    }
}
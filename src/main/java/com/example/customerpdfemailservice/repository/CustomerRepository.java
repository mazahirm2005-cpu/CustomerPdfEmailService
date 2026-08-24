package com.example.customerpdfemailservice.repository;

import com.example.customerpdfemailservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository
        extends JpaRepository<Customer, Long> {

    Optional<Customer> findByCnic(String cnic);
}
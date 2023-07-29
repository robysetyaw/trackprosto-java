package com.trackprosto.trackprosto.service;

import com.trackprosto.trackprosto.model.entity.Customer;
import com.trackprosto.trackprosto.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(String id) {
        return customerRepository.findById(id);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteById(String id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> findByFullName(String fullname) {
        return customerRepository.findByFullname(fullname);
    }

    public List<Customer> findByCompanyId(String companyId) {
        return customerRepository.findByCompanyId(companyId);
    }

    // Add more methods as needed
}

package com.trackprosto.trackprosto.service;

import com.trackprosto.trackprosto.model.entity.Company;
import com.trackprosto.trackprosto.model.entity.Customer;
import com.trackprosto.trackprosto.model.request.CustomerRequest;
import com.trackprosto.trackprosto.repository.CompanyRepository;
import com.trackprosto.trackprosto.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    @Autowired
    public CustomerService(CustomerRepository customerRepository, CompanyRepository companyRepository) {
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
    }

    public List<CustomerRequest> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<Customer> findById(String id) {
        return customerRepository.findById(id);
    }

    public Customer save(CustomerRequest customerRequest) {
        Customer existingCustomer = customerRepository.findbyName(customerRequest.getFullname());
        List<Company> existingCompany = companyRepository.findByCompanyName(customerRequest.getCompanyName());
        if (existingCustomer !=null) {
            throw new RuntimeException("name cannot be duplicated");
        }
        if (existingCompany == null){
            throw new RuntimeException("company doesn't exist");
        }
        Customer customer = new Customer();
        customer.setFullname(customerRequest.getFullname());
        customer.setAddress(customerRequest.getAddress());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
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

    private CustomerRequest convertToDto(Customer customer) {
        CustomerRequest dto = new CustomerRequest();
        dto.setId(customer.getId());
        dto.setFullname(customer.getFullname());
        dto.setAddress(customer.getAddress());
        dto.setPhoneNumber(customer.getPhoneNumber());
//        dto.setCompanyName(customer.getCompany() != null ? customer.getCompany().getCompanyName() : null);
        // Set other fields as needed
        return dto;
    }

}

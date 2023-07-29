package com.trackprosto.trackprosto.service;

import com.trackprosto.trackprosto.exception.CustomExceptionHandler;
import com.trackprosto.trackprosto.model.entity.Company;
import com.trackprosto.trackprosto.model.entity.Customer;
import com.trackprosto.trackprosto.model.request.CustomerRequest;
import com.trackprosto.trackprosto.repository.CompanyRepository;
import com.trackprosto.trackprosto.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public CustomerRequest findByFullName(String fullname) {
        return customerRepository.findByFullname(fullname)
                .stream()
                .map(this::convertToDto)
                .findFirst()
                .orElseThrow(() -> new CustomExceptionHandler.CustomException("Customer not found with fullname " + fullname, HttpStatus.NOT_FOUND));
    }

    public List<Customer> findByCompanyId(String companyId) {
        return customerRepository.findByCompanyId(companyId);
    }

    private CustomerRequest convertToDto(Customer customer) {
        CustomerRequest dto = new CustomerRequest();
        Optional<Company> companies = companyRepository.findById(customer.getCompanyId());
        dto.setId(customer.getId());
        dto.setFullname(customer.getFullname());
        dto.setAddress(customer.getAddress());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setCompanyName(companies.get().getCompanyName());
        // Set other fields as needed
        return dto;
    }

}

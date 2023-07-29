package com.trackprosto.trackprosto.controller;


import com.trackprosto.trackprosto.exception.ResourceNotFoundException;
import com.trackprosto.trackprosto.model.entity.Customer;
import com.trackprosto.trackprosto.model.request.CustomerRequest;
import com.trackprosto.trackprosto.model.response.TemplateResponse;
import com.trackprosto.trackprosto.service.CustomerService;
import org.hibernate.sql.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public TemplateResponse<List<CustomerRequest>> findAll() {
        TemplateResponse<List<CustomerRequest>> res = new TemplateResponse<List<CustomerRequest>>();
        res.message = "success";
        res.data = customerService.findAll();
        return res;
    }

    @GetMapping("/{id}")
    public CustomerRequest findById(@PathVariable String id) {
        return customerService.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
    }

    @PostMapping
    public Customer save(@RequestBody CustomerRequest customerRequest) {
        return customerService.save(customerRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        customerService.deleteById(id);
    }

    @GetMapping("/fullname/{fullname}")
    public List<Customer> findByFullName(@PathVariable String fullname) {
        return customerService.findByFullName(fullname);
    }

    @GetMapping("/companyid/{companyid}")
    public List<Customer> findByCompanyId(@PathVariable String companyid) {
        return customerService.findByCompanyId(companyid);
    }

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

    // Add more methods as needed
}

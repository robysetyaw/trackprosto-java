package com.trackprosto.trackprosto.controller;

import com.trackprosto.trackprosto.model.entity.Customer;
import com.trackprosto.trackprosto.model.request.CustomerRequest;
import com.trackprosto.trackprosto.model.response.TemplateResponse;
import com.trackprosto.trackprosto.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    @PostMapping
    public Customer save(@RequestBody CustomerRequest customerRequest) {
        return customerService.save(customerRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        customerService.deleteById(id);
    }

    @GetMapping("/{fullname}")
    public TemplateResponse<CustomerRequest> findByFullName(@PathVariable String fullname) {
        TemplateResponse<CustomerRequest> res = new TemplateResponse<CustomerRequest>();
        res.message = "success";
        res.data = customerService.findByFullName(fullname);
        return res;
    }


}

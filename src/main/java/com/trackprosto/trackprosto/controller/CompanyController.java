package com.trackprosto.trackprosto.controller;



import com.trackprosto.trackprosto.model.entity.Company;
import com.trackprosto.trackprosto.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<Company> findAll() {
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Company> findById(@PathVariable String id) {
        return companyService.findById(id);
    }

    @PostMapping
    public Company save(@RequestBody Company company) {
        return companyService.save(company);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        companyService.deleteById(id);
    }

}


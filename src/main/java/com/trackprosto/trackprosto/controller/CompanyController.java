package com.trackprosto.trackprosto.controller;



import com.trackprosto.trackprosto.model.entity.Company;
import com.trackprosto.trackprosto.model.response.TemplateResponse;
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
    public TemplateResponse<List<Company>> findAll() {
        TemplateResponse<List<Company>> res = new TemplateResponse<List<Company>>();
        res.message = "succes";
        res.data = companyService.findAll();
        return res;
    }

    @PostMapping
    public TemplateResponse<Company> save(@RequestBody Company company) {
        TemplateResponse res = new TemplateResponse<Company>();
        res.message = "succes insert company";
        res.data = companyService.save(company);
        return res;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        companyService.deleteById(id);
    }

}


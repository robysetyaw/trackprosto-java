package com.trackprosto.trackprosto.service;


import com.trackprosto.trackprosto.exception.CustomExceptionHandler;
import com.trackprosto.trackprosto.model.entity.Company;
import com.trackprosto.trackprosto.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Optional<Company> findById(String id) {
        return companyRepository.findById(id);
    }

    public Company save(Company company) {
        List<Company> isExist = companyRepository.findByCompanyName(company.getCompanyName());
        if (!isExist.isEmpty()) {
            throw new CustomExceptionHandler.CustomException("Company name already exist", HttpStatus.BAD_REQUEST);
        }
        return companyRepository.save(company);
    }

    public void deleteById(String id) {
        companyRepository.deleteById(id);
    }

}




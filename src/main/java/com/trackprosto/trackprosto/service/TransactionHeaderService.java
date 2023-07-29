package com.trackprosto.trackprosto.service;


import com.trackprosto.trackprosto.model.dto.TransactionRequest;
import com.trackprosto.trackprosto.model.entity.TransactionHeader;
import com.trackprosto.trackprosto.repository.CompanyRepository;
import com.trackprosto.trackprosto.repository.CustomerRepository;
import com.trackprosto.trackprosto.repository.TransactionHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionHeaderService {

    private final TransactionHeaderRepository transactionHeaderRepository;
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;


    @Autowired
    public TransactionHeaderService(TransactionHeaderRepository transactionHeaderRepository, CustomerRepository customerRepository, CompanyRepository companyRepository) {
        this.transactionHeaderRepository = transactionHeaderRepository;
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
    }

    public List<TransactionHeader> findAll() {
        return transactionHeaderRepository.findAll();
    }

    public Optional<TransactionHeader> findById(String id) {
        return transactionHeaderRepository.findById(id);
    }

    public TransactionHeader save(TransactionRequest transactionRequest) {
        var customers = customerRepository.findbyName(transactionRequest.getName());
        var customerss = customerRepository.findByFullname(transactionRequest.getName());
        var company = companyRepository.findById(customers.getCompanyId());

        TransactionHeader transactionHeader = new TransactionHeader();
        transactionHeader.setDate(Date.from(new Date().toInstant()));
        transactionHeader.setName(customerss.get(0).getFullname());
        transactionHeader.setCustomerId(customers.getId());
        transactionHeader.setAddress(customers.getAddress());
        transactionHeader.setAddress(customers.getAddress());
        transactionHeader.setPhoneNumber(customers.getPhoneNumber());
        transactionHeader.setCompany(company.get().getCompanyName());
        return transactionHeaderRepository.save(transactionHeader);
//        return TransactionHeader.builder()
//                .address(customers.getAddress())
//                .phoneNumber(customers.getPhoneNumber())
//                .company(company.get().getCompanyName())
//                .build();
    }

    public void deleteById(String id) {
        transactionHeaderRepository.deleteById(id);
    }

    // Add more methods as needed
}


package com.trackprosto.trackprosto.controller;


import com.trackprosto.trackprosto.exception.ResourceNotFoundException;
import com.trackprosto.trackprosto.model.dto.TransactionRequest;
import com.trackprosto.trackprosto.model.entity.TransactionHeader;
import com.trackprosto.trackprosto.service.CustomerService;
import com.trackprosto.trackprosto.service.TransactionHeaderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/transactions")
public class TransactionHeaderController {

    private final TransactionHeaderService transactionHeaderService;
    private final CustomerService customerService;


    public TransactionHeaderController(TransactionHeaderService transactionHeaderService, CustomerService customerService) {
        this.transactionHeaderService = transactionHeaderService;
        this.customerService = customerService;

    }

    @GetMapping
    public List<TransactionHeader> findAll() {
        return transactionHeaderService.findAll();
    }

    @GetMapping("/{id}")
    public TransactionHeader findById(@PathVariable String id) {
        return transactionHeaderService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TransactionHeader not found with id " + id));
    }

    @PostMapping
    public TransactionHeader save(@RequestBody TransactionRequest request) {
        TransactionRequest transactionRequest =  TransactionRequest
                .builder()
                .name(request.getName())
                .paymentAmount(request.getPaymentAmount())
                .txType(request.getTxType())
                .transactionDetails(request.getTransactionDetails())
                .build();
       return transactionHeaderService.save(transactionRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        transactionHeaderService.deleteById(id);
    }

    // Implement other methods as needed
}

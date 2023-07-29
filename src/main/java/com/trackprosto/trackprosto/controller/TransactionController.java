package com.trackprosto.trackprosto.controller;


import com.trackprosto.trackprosto.model.entity.Transaction;
import com.trackprosto.trackprosto.model.request.TransactionRequest;
import com.trackprosto.trackprosto.service.TransactionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;


    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> findAll() {
        return transactionService.findAll();
    }

    @GetMapping("/{id}")
    public Transaction findById(@PathVariable String id) {
        return transactionService.findById(id);
    }

    @PostMapping
    public Transaction save(@Validated @RequestBody TransactionRequest request) {
       return transactionService.save(request);
    }

}

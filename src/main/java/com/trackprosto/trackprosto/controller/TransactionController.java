package com.trackprosto.trackprosto.controller;


import com.trackprosto.trackprosto.model.entity.Transaction;
import com.trackprosto.trackprosto.model.request.CustomerRequest;
import com.trackprosto.trackprosto.model.request.TransactionRequest;
import com.trackprosto.trackprosto.model.response.TemplateResponse;
import com.trackprosto.trackprosto.service.TransactionService;
import org.springframework.data.domain.Page;
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
    public TemplateResponse<List<Transaction>> findAll() {
        TemplateResponse<List<Transaction>> res = new TemplateResponse<List<Transaction>>();
        res.message = "success";
        res.data = transactionService.findAll();
        return res;
    }
    @GetMapping("/page")
    public Page<Transaction> findAllWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return transactionService.findAllWithPagination(page, size);
    }


    @GetMapping("/invoice-numbers/{invNumber}")
    public TemplateResponse<Transaction> findByInvNumber(@PathVariable String invNumber) {
        TemplateResponse<Transaction> res = new TemplateResponse<Transaction>();
        res.message = "succes";
        res.data = transactionService.findByInvNumber(invNumber);
        return res;
    }

    @GetMapping("/customers/{customerName}")
    public TemplateResponse<List<Transaction>> findByCustomerName(@PathVariable String customerName) {
        TemplateResponse<List<Transaction>> res = new TemplateResponse<List<Transaction>>();
        res.message = "succes";
        res.data = transactionService.findByCustomerName(customerName);
        return res;
    }

    @PostMapping
    public TemplateResponse<Transaction> save(@Validated @RequestBody TransactionRequest request) {
       TemplateResponse<Transaction> res = new TemplateResponse<Transaction>();
       res.message = "success add transaction";
       res.data = transactionService.save(request);
        return res;
    }

}

package com.trackprosto.trackprosto.controller;

import com.trackprosto.trackprosto.dto.TransactionRequest;
import com.trackprosto.trackprosto.entity.TransactionDetail;
import com.trackprosto.trackprosto.entity.TransactionHeader;
import com.trackprosto.trackprosto.exception.ResourceNotFoundException;
import com.trackprosto.trackprosto.service.TransactionHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
public class TransactionHeaderController {

    private final TransactionHeaderService transactionHeaderService;

    @Autowired
    public TransactionHeaderController(TransactionHeaderService transactionHeaderService) {
        this.transactionHeaderService = transactionHeaderService;
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
        // Create a new TransactionHeader
        TransactionHeader transactionHeader = new TransactionHeader();
        // Fill in the details from the request
        transactionHeader.setName(request.getName());
        transactionHeader.setTxType(request.getTxType());
        transactionHeader.setPaymentAmount(request.getPaymentAmount());

        // Create TransactionDetail objects for each detail in the request
        List<TransactionDetail> details = request.getTransactionDetails().stream().map(detailDto -> {
            TransactionDetail detail = new TransactionDetail();
            detail.setMeatName(detailDto.getMeatName());
            detail.setPrice(detailDto.getPrice());
            detail.setQty(detailDto.getQty());
            detail.setTransactionHeader(transactionHeader); // Link the detail to the header
            return detail;
        }).collect(Collectors.toList());

        // Set the transaction details in the header
        transactionHeader.setTransactionDetails(details);

        // Save the header (this will also save the details because of the CascadeType.ALL setting)
        return transactionHeaderService.save(transactionHeader);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        transactionHeaderService.deleteById(id);
    }

    // Implement other methods as needed
}

package com.trackprosto.trackprosto.controller;

import com.trackprosto.trackprosto.model.entity.CreditPayment;
import com.trackprosto.trackprosto.model.request.CreditPaymentRequest;
import com.trackprosto.trackprosto.service.CreditPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/credit-payments")
public class CreditPaymentController {

    private final CreditPaymentService creditPaymentService;

    @Autowired
    public CreditPaymentController(CreditPaymentService creditPaymentService) {
        this.creditPaymentService = creditPaymentService;
    }

    @PostMapping
    public CreditPayment save(@RequestBody CreditPaymentRequest request) {
        return creditPaymentService.save(request);
    }

    @GetMapping("/invoice/{invNumber}")
    public List<CreditPayment> findByInvNumber(@PathVariable String invNumber) {
        return creditPaymentService.findByInvNumber(invNumber);
    }

    @GetMapping("/date/{date}")
    public List<CreditPayment> findByPaymentDate(@PathVariable LocalDate date) {
        return creditPaymentService.findByPaymentDate(date);
    }

}

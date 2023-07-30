package com.trackprosto.trackprosto.service;

import com.trackprosto.trackprosto.model.entity.CreditPayment;
import com.trackprosto.trackprosto.repository.CreditPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditPaymentService {

    private final CreditPaymentRepository creditPaymentRepository;

    @Autowired
    public CreditPaymentService(CreditPaymentRepository creditPaymentRepository) {
        this.creditPaymentRepository = creditPaymentRepository;
    }

    public CreditPayment save(CreditPayment creditPayment) {
        return creditPaymentRepository.save(creditPayment);
    }

    public List<CreditPayment> findByInvNumber(String invNumber) {
        return creditPaymentRepository.findPaymentsByInvNumber(invNumber);
    }

    // Add other methods as needed
}

package com.trackprosto.trackprosto.service;

import com.trackprosto.trackprosto.model.entity.CreditPayment;
import com.trackprosto.trackprosto.model.request.CreditPaymentRequest;
import com.trackprosto.trackprosto.repository.CreditPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CreditPaymentService {

    private final CreditPaymentRepository creditPaymentRepository;

    @Autowired
    public CreditPaymentService(CreditPaymentRepository creditPaymentRepository) {
        this.creditPaymentRepository = creditPaymentRepository;
    }

    public CreditPayment save(CreditPaymentRequest request) {
        CreditPayment creditPayment = new CreditPayment();
        creditPayment.setInvNumber(request.getInvNumber());
        creditPayment.setAmount(request.getAmount());
        creditPayment.setPaymentDate(LocalDate.now());
        return creditPaymentRepository.save(creditPayment);
    }

    public List<CreditPayment> findByInvNumber(String invNumber) {
        return creditPaymentRepository.findPaymentsByInvNumber(invNumber);
    }

    public List<CreditPayment> findByPaymentDate(LocalDate date) {
        return creditPaymentRepository.findByPaymentDate(date);
    }

}

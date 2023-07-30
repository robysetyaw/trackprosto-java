package com.trackprosto.trackprosto.service;

import com.trackprosto.trackprosto.exception.CustomExceptionHandler;
import com.trackprosto.trackprosto.model.entity.CreditPayment;
import com.trackprosto.trackprosto.model.entity.TransactionHeader;
import com.trackprosto.trackprosto.model.request.CreditPaymentRequest;
import com.trackprosto.trackprosto.repository.CreditPaymentRepository;
import com.trackprosto.trackprosto.repository.TransactionHeaderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class CreditPaymentService {

    private final CreditPaymentRepository creditPaymentRepository;
    private final TransactionHeaderRepository transactionHeaderRepository;

    @Autowired
    public CreditPaymentService(CreditPaymentRepository creditPaymentRepository, TransactionHeaderRepository transactionHeaderRepository) {
        this.creditPaymentRepository = creditPaymentRepository;
        this.transactionHeaderRepository = transactionHeaderRepository;
    }

    @Transactional
    public CreditPayment save(CreditPaymentRequest request) {
        CreditPayment creditPayment = new CreditPayment();
        List<CreditPayment> InvNumberExist = creditPaymentRepository.findPaymentsByInvNumber(request.getInvNumber());
        if (InvNumberExist.isEmpty()) {
            throw new CustomExceptionHandler.CustomException("Invalid Invoice Numebr", HttpStatus.NOT_FOUND);
        }
        creditPayment.setInvNumber(request.getInvNumber());
        creditPayment.setAmount(request.getAmount());
        CreditPayment res = creditPaymentRepository.save(creditPayment);
        Double sumAmount = creditPaymentRepository.sumAmountByInvoiceNumber(request.getInvNumber());

        TransactionHeader tHeader = transactionHeaderRepository.findByInvNumber(request.getInvNumber());

        if(tHeader == null) {
            throw new CustomExceptionHandler.CustomException("TransactionHeader not found with invoice number " + request.getInvNumber(), HttpStatus.NOT_FOUND);
        }
        if (sumAmount>tHeader.getPaymentAmount()){
            throw new CustomExceptionHandler.CustomException("Payment Amount is larger than debt", HttpStatus.BAD_REQUEST);
        }
        tHeader.setPaymentAmount(sumAmount);
        if (Objects.equals(sumAmount, tHeader.getPaymentAmount())){
            tHeader.setPaymentStatus("paid");
        }
        transactionHeaderRepository.save(tHeader);
        return res;
    }

    public List<CreditPayment> findByInvNumber(String invNumber) {
        return creditPaymentRepository.findPaymentsByInvNumber(invNumber);
    }

    public List<CreditPayment> findByPaymentDate(LocalDate date) {
        return creditPaymentRepository.findByPaymentDate(date);
    }

}

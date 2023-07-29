package com.trackprosto.trackprosto.service;

import com.trackprosto.trackprosto.exception.CustomExceptionHandler;
import com.trackprosto.trackprosto.model.entity.Meat;
import com.trackprosto.trackprosto.model.entity.Transaction;
import com.trackprosto.trackprosto.model.entity.TransactionDetail;
import com.trackprosto.trackprosto.model.entity.TransactionHeader;
import com.trackprosto.trackprosto.model.request.TransactionDetailRequest;
import com.trackprosto.trackprosto.model.request.TransactionRequest;
import com.trackprosto.trackprosto.repository.CustomerRepository;
import com.trackprosto.trackprosto.repository.MeatRepository;
import com.trackprosto.trackprosto.repository.TransactionDetailRepository;
import com.trackprosto.trackprosto.repository.TransactionHeaderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionHeaderRepository transactionHeaderRepository;
    private final TransactionDetailRepository transactionDetailRepository;
    private final MeatRepository meatRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public TransactionService(TransactionHeaderRepository transactionHeaderRepository, TransactionDetailRepository transactionDetailRepository, MeatRepository meatRepository, CustomerRepository customerRepository) {
        this.transactionHeaderRepository = transactionHeaderRepository;
        this.transactionDetailRepository = transactionDetailRepository;
        this.meatRepository = meatRepository;
        this.customerRepository = customerRepository;
    }
    public Transaction findById(String headerId) {
        TransactionHeader header = transactionHeaderRepository.findById(headerId)
                .orElseThrow(() -> new CustomExceptionHandler.CustomException("TransactionHeader not found with id " + headerId, HttpStatus.NOT_FOUND));
        List<TransactionDetail> details = transactionDetailRepository.findByTransactionHeader(header);

        Transaction transaction = new Transaction();
        transaction.setHeader(header);

        return transaction;
    }

    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();

        // Get all transaction headers
        List<TransactionHeader> headers = transactionHeaderRepository.findAll();
        for (TransactionHeader header : headers) {
            // For each header, get the details and create a transaction
            List<TransactionDetail> details = transactionDetailRepository.findByTransactionHeader(header);
            Transaction transaction = new Transaction();
            transaction.setHeader(header);

            transactions.add(transaction);
        }

        return transactions;
    }

    @Transactional
    public Transaction save(TransactionRequest request) {
        TransactionHeader newHeader = new TransactionHeader();
        newHeader.setName(request.getName());
        newHeader.setTxType(request.getTxType());
        newHeader.setPaymentAmount(request.getPaymentAmount());
        TransactionHeader savedHeader = transactionHeaderRepository.save(newHeader);
        Double sumTotal = 0.0;
        for (TransactionDetailRequest detailRequest : request.getTransactionDetails()) {
            List<Meat> meat = meatRepository.findByName(detailRequest.getMeatName());
            TransactionDetail detail = new TransactionDetail();
            Double qty = detailRequest.getQty();
            Double price = detailRequest.getPrice();
            Double total = qty * price;
            sumTotal += total;
            detail.setMeatId(meat.get(0).getId());
            detail.setTransactionId(savedHeader.getId());
            detail.setMeatName(detailRequest.getMeatName());
            detail.setPrice(detailRequest.getPrice());
            detail.setQty(detailRequest.getQty());
            detail.setTotal(total);
            detail.setTransactionHeader(savedHeader);
            transactionDetailRepository.save(detail);
        }
        newHeader.setTotal(sumTotal);
        transactionHeaderRepository.save(newHeader);
        Transaction transaction = new Transaction();
        transaction.setHeader(savedHeader);
        return transaction;
    }

}

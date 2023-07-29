package com.trackprosto.trackprosto.service;

import com.trackprosto.trackprosto.exception.CustomExceptionHandler;
import com.trackprosto.trackprosto.exception.CustomExceptionHandler.CustomException;
import com.trackprosto.trackprosto.model.entity.*;
import com.trackprosto.trackprosto.model.request.TransactionDetailRequest;
import com.trackprosto.trackprosto.model.request.TransactionRequest;
import com.trackprosto.trackprosto.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionHeaderRepository transactionHeaderRepository;
    private final TransactionDetailRepository transactionDetailRepository;
    private final MeatRepository meatRepository;
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public TransactionService(TransactionHeaderRepository transactionHeaderRepository, TransactionDetailRepository transactionDetailRepository, MeatRepository meatRepository, CustomerRepository customerRepository, CompanyRepository companyRepository) {
        this.transactionHeaderRepository = transactionHeaderRepository;
        this.transactionDetailRepository = transactionDetailRepository;
        this.meatRepository = meatRepository;
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
    }
    public Transaction findById(String headerId) {
        TransactionHeader header = transactionHeaderRepository.findById(headerId)
                .orElseThrow(() -> new CustomExceptionHandler.CustomException("TransactionHeader not found with id " + headerId, HttpStatus.NOT_FOUND));

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
        Customer customer = customerRepository.findbyName(request.getName());
        if (customer == null){
            throw new CustomException("customer not found", HttpStatus.NOT_FOUND);
        }
        Optional<Company> companies = companyRepository.findById(customer.getCompanyId());
        int count = transactionHeaderRepository.countByDate(LocalDate.now()) + 1;
        String invNumber = generateInvNumber(count);
        newHeader.setInvNumber(invNumber);
        newHeader.setDate(LocalDate.now());
        newHeader.setCustomerId(customer.getId());
        newHeader.setName(request.getName());
        newHeader.setAddress(customer.getAddress());
        newHeader.setPhoneNumber(customer.getPhoneNumber());
        newHeader.setCompany(companies.get().getCompanyName());
        newHeader.setTxType(request.getTxType());
        newHeader.setPaymentAmount(request.getPaymentAmount());
        newHeader.setTransactionDetails(new ArrayList<>());
        TransactionHeader savedHeader = transactionHeaderRepository.save(newHeader);
        Double sumTotal = 0.0;
        for (TransactionDetailRequest detailRequest : request.getTransactionDetails()) {
            List<Meat> meat = meatRepository.findByName(detailRequest.getMeatName());
            if (meat == null){
                throw new CustomException("Meat not found", HttpStatus.NOT_FOUND);
            }
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
            savedHeader.getTransactionDetails().add(detail);
        }
        newHeader.setTotal(sumTotal);
        transactionHeaderRepository.save(newHeader);
        Transaction transaction = new Transaction();
        transaction.setHeader(savedHeader);
        return transaction;
    }

    public String generateInvNumber(int count) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateString = now.format(formatter);
        String countString = String.format("%04d", count);

        return "INV-" + dateString + "-" + countString;
    }

}

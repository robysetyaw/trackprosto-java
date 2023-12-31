package com.trackprosto.trackprosto.service;

import com.trackprosto.trackprosto.exception.CustomExceptionHandler;
import com.trackprosto.trackprosto.exception.CustomExceptionHandler.CustomException;
import com.trackprosto.trackprosto.model.entity.*;
import com.trackprosto.trackprosto.model.request.TransactionDetailRequest;
import com.trackprosto.trackprosto.model.request.TransactionRequest;
import com.trackprosto.trackprosto.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    private final CreditPaymentRepository creditPaymentRepository;

    @Autowired
    public TransactionService(TransactionHeaderRepository transactionHeaderRepository, TransactionDetailRepository transactionDetailRepository, MeatRepository meatRepository, CustomerRepository customerRepository, CompanyRepository companyRepository, CreditPaymentRepository creditPaymentRepository) {
        this.transactionHeaderRepository = transactionHeaderRepository;
        this.transactionDetailRepository = transactionDetailRepository;
        this.meatRepository = meatRepository;
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
        this.creditPaymentRepository = creditPaymentRepository;
    }

    public  Transaction findByInvNumber(String invNumber){
        TransactionHeader header = transactionHeaderRepository.findByInvNumber(invNumber);
        if (header.equals(null)){
            throw new CustomException("No transaction found with invoice number : "+ invNumber,HttpStatus.BAD_REQUEST);
        }
        Transaction transaction = new Transaction();
        transaction.setHeader(header);
        return transaction;
    }

    public List<Transaction> findByCustomerName(String customerName){
        Customer customer = customerRepository.findbyName(customerName);
        if (customer.equals(null)){
            throw new CustomException("No customers found with username :" + customerName, HttpStatus.BAD_REQUEST);
        }
        List<TransactionHeader> headers = transactionHeaderRepository.findByCustomerId(customer.getId());
        List<Transaction> transactions = new ArrayList<>();
        for (TransactionHeader header: headers){
            Transaction transaction = new Transaction();
            transaction.setHeader(header);
            transactions.add(transaction);
        }
        return transactions;
    }

    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();

        // Get all transaction headers
        List<TransactionHeader> headers = transactionHeaderRepository.findAll();
        for (TransactionHeader header : headers) {
            // For each header, get the details and create a transaction
//            List<TransactionDetail> details = transactionDetailRepository.findByTransactionHeader(header);
            Transaction transaction = new Transaction();
            transaction.setHeader(header);

            transactions.add(transaction);
        }

        return transactions;
    }

    public Page<Transaction> findAllWithPagination(int page, int size) {
        int limit = size;
        int offset = page * size;
        List<TransactionHeader> headers = transactionHeaderRepository.findAllWithPagination(limit, offset);

        List<Transaction> transactions = new ArrayList<>();

        for (TransactionHeader header : headers) {
            Transaction transaction = new Transaction();
            transaction.setHeader(header);
            transactions.add(transaction);
        }

        Page<Transaction> transactionPage = new PageImpl<>(transactions, PageRequest.of(page, size), headers.size());

        return transactionPage;
    }


    @Transactional
    public Transaction save(TransactionRequest request) {
        TransactionHeader newHeader = createTransactionHeader(request);
        createCreditPayment(request, newHeader.getInvNumber());
        double sumTotal = createTransactionDetails(request, newHeader);
        updatePaymentStatus(newHeader, sumTotal);
        updateCustomerDebt(newHeader, newHeader.getCustomerId());

        Transaction transaction = new Transaction();
        transaction.setHeader(newHeader);
        return transaction;
    }

    private TransactionHeader createTransactionHeader(TransactionRequest request) {
        TransactionHeader newHeader = new TransactionHeader();
        Customer customer = customerRepository.findbyName(request.getName());
        if (customer == null){
            throw new CustomException("customer not found", HttpStatus.BAD_REQUEST);
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
        return transactionHeaderRepository.save(newHeader);
    }

    private void createCreditPayment(TransactionRequest request, String invNumber) {
        CreditPayment newCreditPaymentRequest = new CreditPayment();
        newCreditPaymentRequest.setInvNumber(invNumber);
        newCreditPaymentRequest.setAmount(request.getPaymentAmount());
        creditPaymentRepository.save(newCreditPaymentRequest);
    }

    private double createTransactionDetails(TransactionRequest request, TransactionHeader newHeader) {
        double sumTotal = 0.0;
        for (TransactionDetailRequest detailRequest : request.getTransactionDetails()) {
            List<Meat> meat = meatRepository.findByName(detailRequest.getMeatName());
            if (meat == null){
                throw new CustomException("Meat not found", HttpStatus.BAD_REQUEST);
            }
            TransactionDetail detail = new TransactionDetail();

            if (meat.get(0).getStock() < detailRequest.getQty()){
                throw new CustomException("Stock not available "+meat.get(0).getName(), HttpStatus.BAD_REQUEST );
            }
            Double qty = detailRequest.getQty();
            Double price = detailRequest.getPrice();
            double total = qty * price;
            sumTotal += total;
            detail.setMeatId(meat.get(0).getId());
            detail.setTransactionId(newHeader.getId());
            detail.setMeatName(detailRequest.getMeatName());
            detail.setPrice(detailRequest.getPrice());
            detail.setQty(detailRequest.getQty());
            detail.setTotal(total);
            detail.setTransactionHeader(newHeader);
            updateMeatStock(meat.get(0).getId(),newHeader.getTxType(),qty);
            transactionDetailRepository.save(detail);
            newHeader.getTransactionDetails().add(detail);
        }
        return sumTotal;
    }

    private void updateMeatStock(String meatId,String txType ,double qty){
        Optional<Meat> meatOptional = meatRepository.findById(meatId);
        if (meatOptional.isPresent()){
            Meat meat = meatOptional.get();
            double newStock = meat.getStock();
            if (txType.equals("out")){
                newStock = newStock - qty;
            } else if (txType.equals("in")) {
                newStock = newStock + qty;
            }
            meat.setStock(newStock);
            meatRepository.save(meat);
        }
    }


    private void updatePaymentStatus(TransactionHeader newHeader, double sumTotal) {
        if (newHeader.getPaymentAmount()>sumTotal){
            throw new CustomException("Payment Amount is bigger than total transaction",HttpStatus.BAD_REQUEST );
        } else if (newHeader.getPaymentAmount()<sumTotal ) {
            newHeader.setPaymentStatus("unpaid");
        } else if (newHeader.getPaymentAmount() == sumTotal) {
            newHeader.setPaymentStatus("paid");
        }
        newHeader.setTotal(sumTotal);
        transactionHeaderRepository.save(newHeader);
    }

    private void updateCustomerDebt(TransactionHeader newHeader, String customerId) {
        Double debt = transactionHeaderRepository.sumDebtByCustomerId(customerId);
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomException("Customer not found", HttpStatus.NOT_FOUND));
        customer.setDebt(debt);
        customerRepository.save(customer);
    }

    public String generateInvNumber(int count) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateString = now.format(formatter);
        String countString = String.format("%04d", count);

        return "INV-" + dateString + "-" + countString;
    }

}

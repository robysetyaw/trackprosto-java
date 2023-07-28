package com.trackprosto.trackprosto.service;

import com.trackprosto.trackprosto.entity.TransactionDetail;
import com.trackprosto.trackprosto.repository.TransactionDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionDetailService {

    private final TransactionDetailRepository transactionDetailRepository;

    @Autowired
    public TransactionDetailService(TransactionDetailRepository transactionDetailRepository) {
        this.transactionDetailRepository = transactionDetailRepository;
    }

    public List<TransactionDetail> findAll() {
        return transactionDetailRepository.findAll();
    }

    public Optional<TransactionDetail> findById(String id) {
        return transactionDetailRepository.findById(id);
    }

    public TransactionDetail save(TransactionDetail transactionDetail) {
        return transactionDetailRepository.save(transactionDetail);
    }

    public void deleteById(String id) {
        transactionDetailRepository.deleteById(id);
    }

    // Add more methods as needed
}

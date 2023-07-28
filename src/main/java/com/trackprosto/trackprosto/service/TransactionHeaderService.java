package com.trackprosto.trackprosto.service;

import com.trackprosto.trackprosto.entity.TransactionHeader;
import com.trackprosto.trackprosto.repository.TransactionHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionHeaderService {

    private final TransactionHeaderRepository transactionHeaderRepository;

    @Autowired
    public TransactionHeaderService(TransactionHeaderRepository transactionHeaderRepository) {
        this.transactionHeaderRepository = transactionHeaderRepository;
    }

    public List<TransactionHeader> findAll() {
        return transactionHeaderRepository.findAll();
    }

    public Optional<TransactionHeader> findById(String id) {
        return transactionHeaderRepository.findById(id);
    }

    public TransactionHeader save(TransactionHeader transactionHeader) {
        return transactionHeaderRepository.save(transactionHeader);
    }

    public void deleteById(String id) {
        transactionHeaderRepository.deleteById(id);
    }

    // Add more methods as needed
}


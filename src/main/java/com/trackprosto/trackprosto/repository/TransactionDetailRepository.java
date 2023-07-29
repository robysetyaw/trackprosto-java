package com.trackprosto.trackprosto.repository;


import com.trackprosto.trackprosto.model.entity.TransactionDetail;
import com.trackprosto.trackprosto.model.entity.TransactionHeader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String> {
    List<TransactionDetail> findByTransactionHeader(TransactionHeader transactionHeader);
}

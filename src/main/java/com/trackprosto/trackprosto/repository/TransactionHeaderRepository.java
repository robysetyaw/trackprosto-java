package com.trackprosto.trackprosto.repository;


import com.trackprosto.trackprosto.model.entity.TransactionHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface TransactionHeaderRepository extends JpaRepository<TransactionHeader, String> {
    @Query("SELECT COUNT(th) FROM TransactionHeader th WHERE th.date = :date")
    int countByDate(@Param("date") LocalDate date);

    @Query(value = "SELECT * FROM transaction_headers WHERE inv_number = ?1", nativeQuery = true)
    TransactionHeader findByInvNumber(String invNumber);

    @Query("SELECT SUM(th.total - th.paymentAmount) FROM TransactionHeader th WHERE th.customerId = :customerId")
    Double sumDebtByCustomerId(@Param("customerId") String customerId);
}

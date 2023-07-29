package com.trackprosto.trackprosto.repository;


import com.trackprosto.trackprosto.model.entity.TransactionHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface TransactionHeaderRepository extends JpaRepository<TransactionHeader, String> {
    @Query("SELECT COUNT(th) FROM TransactionHeader th WHERE th.date = :date")
    int countByDate(@Param("date") LocalDate date);
}

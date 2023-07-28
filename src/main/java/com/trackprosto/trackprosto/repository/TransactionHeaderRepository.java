package com.trackprosto.trackprosto.repository;

import com.trackprosto.trackprosto.entity.TransactionHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHeaderRepository extends JpaRepository<TransactionHeader, String> {
}

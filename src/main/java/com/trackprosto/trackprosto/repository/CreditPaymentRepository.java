package com.trackprosto.trackprosto.repository;

import com.trackprosto.trackprosto.model.entity.CreditPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreditPaymentRepository extends JpaRepository<CreditPayment, String> {
    @Query(value = "SELECT * FROM credit_payments WHERE inv_number = ?1", nativeQuery = true)
    List<CreditPayment> findPaymentsByInvNumber(String invNumber);
}
package com.trackprosto.trackprosto.repository;

import com.trackprosto.trackprosto.model.entity.CreditPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CreditPaymentRepository extends JpaRepository<CreditPayment, String> {
    @Query(value = "SELECT * FROM credit_payments WHERE inv_number = ?1", nativeQuery = true)
    List<CreditPayment> findPaymentsByInvNumber(String invNumber);

    @Query("SELECT c FROM CreditPayment c WHERE c.paymentDate = :date")
    List<CreditPayment> findByPaymentDate(@Param("date") LocalDate date);
    @Query(value = "SELECT SUM(amount) FROM credit_payments WHERE inv_number = ?1", nativeQuery = true)
    Double sumAmountByInvoiceNumber(String invNumber);

}
package com.trackprosto.trackprosto.repository;


import com.trackprosto.trackprosto.model.dto.SumQtyByMeatIdAndTxTypeDTO;
import com.trackprosto.trackprosto.model.entity.TransactionDetail;
import com.trackprosto.trackprosto.model.entity.TransactionHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String> {
    List<TransactionDetail> findByTransactionHeader(TransactionHeader transactionHeader);
    @Query("SELECT new com.trackprosto.trackprosto.model.dto.SumQtyByMeatIdAndTxTypeDTO(SUM(detail.qty), detail.meatId, detail.meatName, header.txType) FROM TransactionDetail detail JOIN detail.transactionHeader header WHERE header.date BETWEEN :startDate AND :endDate AND header.txType = :txType GROUP BY detail.meatId, detail.meatName, header.txType")
    List<SumQtyByMeatIdAndTxTypeDTO> findSumQtyByMeatIdAndTxType(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("txType") String txType);

}

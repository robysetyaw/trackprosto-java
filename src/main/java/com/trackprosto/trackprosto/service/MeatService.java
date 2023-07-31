package com.trackprosto.trackprosto.service;


import com.trackprosto.trackprosto.model.dto.StockMovementReportDTO;
import com.trackprosto.trackprosto.model.dto.SumQtyByMeatIdAndTxTypeDTO;
import com.trackprosto.trackprosto.model.entity.Meat;
import com.trackprosto.trackprosto.repository.MeatRepository;
import com.trackprosto.trackprosto.repository.TransactionDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class MeatService {

    private final MeatRepository meatRepository;
    private final TransactionDetailRepository transactionDetailRepository;

    @Autowired
    public MeatService(MeatRepository meatRepository, TransactionDetailRepository transactionDetailRepository) {
        this.meatRepository = meatRepository;
        this.transactionDetailRepository = transactionDetailRepository;
    }

    public List<Meat> findAll() {
        return meatRepository.findAll();
    }

    public Optional<Meat> findById(String id) {
        return meatRepository.findById(id);
    }

    public Meat save(Meat meat) {
        return meatRepository.save(meat);
    }

    public void deleteById(String id) {
        meatRepository.deleteById(id);
    }

    public List<StockMovementReportDTO> generateStockMovement(LocalDate startDate, LocalDate endDate){
        List<SumQtyByMeatIdAndTxTypeDTO> inMovements = transactionDetailRepository.findSumQtyByMeatIdAndTxType(startDate, endDate, "in");
        List<SumQtyByMeatIdAndTxTypeDTO> outMovements = transactionDetailRepository.findSumQtyByMeatIdAndTxType(startDate, endDate, "out");

        Map<String, StockMovementReportDTO> movementMap = new HashMap<>();

        for (SumQtyByMeatIdAndTxTypeDTO movement : inMovements) {
            StockMovementReportDTO report = new StockMovementReportDTO();
            report.setMeatId(movement.getMeatId());
            report.setMeatName(movement.getMeatName());
            report.setDate(LocalDate.now());
            report.setQtyIn(movement.getSumQty());
            report.setQtyOut(0.0);
            movementMap.put(movement.getMeatId(), report);
        }

        for (SumQtyByMeatIdAndTxTypeDTO movement : outMovements) {
            if (movementMap.containsKey(movement.getMeatId())) {
                movementMap.get(movement.getMeatId()).setQtyOut(movement.getSumQty());
            } else {
                StockMovementReportDTO report = new StockMovementReportDTO();
                report.setMeatId(movement.getMeatId());
                report.setMeatName(movement.getMeatName());
                report.setDate(LocalDate.now()); // or endDate, or any date you want to set
                report.setQtyIn(0.0);
                report.setQtyOut(movement.getSumQty());
                movementMap.put(movement.getMeatId(), report);
            }
        }

        return new ArrayList<>(movementMap.values());
    }

}

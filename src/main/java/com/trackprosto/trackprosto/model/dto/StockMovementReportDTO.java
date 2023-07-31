package com.trackprosto.trackprosto.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockMovementReportDTO {
    private String meatId;
    private Double qtyIn;
    private Double qtyOut;
    private String meatName;
    private LocalDate date;
}

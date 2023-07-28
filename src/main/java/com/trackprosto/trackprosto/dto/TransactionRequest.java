package com.trackprosto.trackprosto.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TransactionRequest {
    private String name;
    private String txType;
    private BigDecimal paymentAmount;
    private List<TransactionDetailDto> transactionDetails;

    @Data
    public static class TransactionDetailDto {
        private String meatName;
        private BigDecimal price;
        private BigDecimal qty;
    }
}

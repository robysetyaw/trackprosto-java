package com.trackprosto.trackprosto.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionRequest {
    private String name;
    private String txType;
    private BigDecimal paymentAmount;
    private List<TransactionDetailRequest> transactionDetails = new ArrayList<>();
}

package com.trackprosto.trackprosto.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Transaction type cannot be blank")
    private String txType;

    @NotNull(message = "Payment amount cannot be null")
    @Positive(message = "Payment amount must be positive")
    private Double paymentAmount;

    @Valid
    @NotNull(message = "Transaction details cannot be null")
    private List<TransactionDetailRequest> transactionDetails = new ArrayList<>();

}

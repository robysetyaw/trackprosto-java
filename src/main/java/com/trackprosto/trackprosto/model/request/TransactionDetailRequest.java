package com.trackprosto.trackprosto.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetailRequest {
    @NotBlank(message = "Insert meat")
    private String meatName;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price amount must be positive")
    private Double price;

    @NotNull(message = "Quantity  cannot be null")
    @Positive(message = "Quantity  must be positive")
    private Double qty;
}

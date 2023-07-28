package com.trackprosto.trackprosto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetailRequest {
    private String meatName;
    private Double price;
    private Double qty;
}

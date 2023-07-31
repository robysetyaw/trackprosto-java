package com.trackprosto.trackprosto.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SumQtyByMeatIdAndTxTypeDTO {
    private Double sumQty;
    private String meatId;
    private String meatName;
    private String txType;
}

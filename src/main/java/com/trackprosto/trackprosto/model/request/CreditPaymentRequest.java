package com.trackprosto.trackprosto.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditPaymentRequest {
    private String invNumber;
    private Double amount;

}

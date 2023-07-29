package com.trackprosto.trackprosto.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerRequest {
    private String id;
    private String fullname;
    private String address;
    private String phoneNumber;
    private String companyName;
}

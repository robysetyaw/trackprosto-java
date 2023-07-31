package com.trackprosto.trackprosto.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    private String address;

    @NotBlank
    @Pattern(regexp = "^\\d+$", message = "Phone number must only contain numbers")
    private String phoneNumber;

    @NotBlank
    private String companyName;

    private Double debt;
}

package com.trackprosto.trackprosto.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDto {
    private String id;
    private String fullname;
    private String address;
    private String phoneNumber;
    private String companyName;
    // Tambahkan bidang lain yang perlu Anda sertakan dalam response

    // Konstruktor, getter dan setter
}


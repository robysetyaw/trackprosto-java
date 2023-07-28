package com.trackprosto.trackprosto.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction_headers")
public class TransactionHeader {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;
    private LocalDate date;
    @Column(name = "inv_number")
    private String invNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @Column(name = "customer_id")
    private String customerId;
    private String name;
    private String address;
    private String company;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "tx_type")
    private String txType;
    private BigDecimal total;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "payment_status")
    private String paymentStatus;
    @Column(name = "payment_amount")
    private BigDecimal paymentAmount;
    @OneToMany(mappedBy = "transactionHeader", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionDetail> transactionDetails;
}


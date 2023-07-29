package com.trackprosto.trackprosto.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction_details")
public class TransactionDetail {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_id", insertable = false, updatable = false)
    private TransactionHeader transactionHeader;
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "meat_id")
    private String meatId;
    @Column(name = "meat_name")
    private String meatName;
    private BigDecimal qty;
    private BigDecimal price;
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
}


package com.payaman.auctms.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "Transaction")
public class TransactionData {

    @Id
    private String transactionId; // You might generate this via UUID

    private String auctionId;
    private String buyerId;
    private String sellerId;
    private BigDecimal finalAmount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
}
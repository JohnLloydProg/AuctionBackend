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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int auctionId;
    private int buyerId;
    private float finalAmount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
}
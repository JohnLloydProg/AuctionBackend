package com.payaman.auctms.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
class BidId implements Serializable {
    private String userId;
    private String auctionId;
}

@Data
@Entity
@Table(name = "Bid")
@IdClass(BidId.class)
public class BidData {

    @Id
    private String userId;

    @Id
    private String auctionId;

    private BigDecimal offeredPrice;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
}
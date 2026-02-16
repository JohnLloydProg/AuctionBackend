package com.payaman.auctms.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BidRequest {
    private String userId;
    private String auctionId;
    private BigDecimal offeredPrice;
}
package com.payaman.auctms.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BidRequest {
    private int id;
    private int userId;
    private float offeredPrice;
}
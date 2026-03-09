package com.payaman.auctms.model;

import lombok.Data;

@Data
public class Transaction {
    private int id;
    private Auction auction;
    private User buyer;
    private float finalAmount;
}

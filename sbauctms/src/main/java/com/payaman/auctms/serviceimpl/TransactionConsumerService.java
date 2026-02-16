package com.payaman.auctms.serviceimpl;

public interface TransactionConsumerService {
    void consumeTransaction(String message);
}
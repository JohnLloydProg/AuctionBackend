package com.payaman.auctms.service;

public interface TransactionConsumerService {
    void consumeTransaction(String message);
}
package com.payaman.auctms.service;

import com.payaman.auctms.model.Transaction;

public interface TransactionService {
    Transaction create(Transaction transaction) throws Exception;
    Transaction[] getAll(Integer userId) throws Exception;
    Transaction update(Transaction transaction) throws Exception;
}

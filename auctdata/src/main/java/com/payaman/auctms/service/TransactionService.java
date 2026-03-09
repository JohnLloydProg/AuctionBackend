package com.payaman.auctms.service;

import com.payaman.auctms.model.Transaction;

public interface TransactionService {
    Transaction create(Transaction transaction) throws Exception;
    Transaction[] getAll() throws Exception;
}

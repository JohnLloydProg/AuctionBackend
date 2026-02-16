package com.payaman.auctms.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payaman.auctms.entity.TransactionData;
import com.payaman.auctms.repository.TransactionDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionConsumerServiceImpl implements TransactionConsumerService {

    private final TransactionDataRepository transactionRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void consumeTransaction(String message) {
        try {
            TransactionData transaction = objectMapper.readValue(message, TransactionData.class);
            transactionRepository.save(transaction);
            log.info("Service: Saved transaction: {}", transaction.getTransactionId());
        } catch (Exception e) {
            log.error("Service Error: {}", e.getMessage());
        }
    }
}
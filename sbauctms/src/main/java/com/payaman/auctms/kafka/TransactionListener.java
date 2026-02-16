package com.payaman.auctms.kafka;

import com.payaman.auctms.service.TransactionConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionListener {

    private final TransactionConsumerService transactionService;

    @KafkaListener(topics = "auction.transactions", groupId = "auction-group")
    public void listen(String message) {
        log.info("Kafka Listener: Received Transaction -> {}", message);
        transactionService.consumeTransaction(message);
    }
}
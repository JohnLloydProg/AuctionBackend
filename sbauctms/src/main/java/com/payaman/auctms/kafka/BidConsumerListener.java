package com.payaman.auctms.kafka;

import com.payaman.auctms.service.BidConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BidConsumerListener {

    private final BidConsumerService bidService;

    @KafkaListener(topics = "auction.bids", groupId = "auction-group")
    public void listen(String message) {
        log.info("Kafka Listener: Received Bid -> {}", message);
        bidService.consumeBid(message);
    }
}
package com.payaman.auctms.service;

import com.payaman.auctms.model.BidRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BidProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String TOPIC_BIDS = "auction.bids";

    public void sendBid(BidRequest bidRequest) {
        try {
            String message = objectMapper.writeValueAsString(bidRequest);

            kafkaTemplate.send(TOPIC_BIDS, bidRequest.getAuctionId(), message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to publish bid to Kafka", e);
        }
    }
}
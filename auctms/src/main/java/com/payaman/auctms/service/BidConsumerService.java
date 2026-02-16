package com.payaman.auctms.service;

import com.payaman.auctms.entity.BidData;       // Import your Entity
import com.payaman.auctms.model.BidRequest;      // Import your DTO
import com.payaman.auctms.repository.BidDataRepository; // Import your Repo
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener; // <--- FIXES ERROR 1 & 2
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BidConsumerService {

    private final BidDataRepository bidRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "auction.bids", groupId = "auction-group")
    @Transactional
    public void consumeBid(String message) {
        try {
            BidRequest request = objectMapper.readValue(message, BidRequest.class);

            // Create Entity
            BidData bid = new BidData();
            bid.setUserId(request.getUserId());
            bid.setAuctionId(request.getAuctionId());
            bid.setOfferedPrice(request.getOfferedPrice());

            // Save to DB (This will Insert or Update based on the PK)
            bidRepository.save(bid);

            System.out.println("Processed bid for auction: " + request.getAuctionId());

        } catch (Exception e) {
            System.err.println("Error processing Kafka message: " + e.getMessage());
            // In a real app, you might send this to a Dead Letter Queue (DLQ)
        }
    }
}
package com.payaman.auctms.service;

import com.payaman.auctms.entity.BidData;
import com.payaman.auctms.model.BidRequest;
import com.payaman.auctms.repository.BidDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
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

            BidData bid = new BidData();
            bid.setUserId(request.getUserId());
            bid.setAuctionId(request.getAuctionId());
            bid.setOfferedPrice(request.getOfferedPrice());

            bidRepository.save(bid);

            System.out.println("Processed bid for auction: " + request.getAuctionId());

        } catch (Exception e) {
            System.err.println("Error processing Kafka message: " + e.getMessage());
        }
    }
}
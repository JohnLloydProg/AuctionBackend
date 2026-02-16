package com.payaman.auctms.kafka;

import com.payaman.auctms.model.Auction;
import com.payaman.auctms.service.AuctionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AuctionListener {
    Logger logger = LoggerFactory.getLogger(AuctionListener.class);

    @Autowired
    AuctionService auctionService;

    @KafkaListener(topics = "Auction", groupId = "Payaman")
    public void consumeAuction(Auction auction) {
        try {
            auctionService.create(auction);
        }catch (Exception e) {
            logger.error("An error occured while creating auction from kafka: " + e.getMessage());
        }

    }

}

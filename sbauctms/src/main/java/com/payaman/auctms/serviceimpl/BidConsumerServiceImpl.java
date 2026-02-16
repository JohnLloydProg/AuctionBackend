package com.payaman.auctms.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payaman.auctms.entity.BidData;
import com.payaman.auctms.model.BidRequest;
import com.payaman.auctms.repository.BidDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BidConsumerServiceImpl implements BidConsumerService {

    private final BidDataRepository bidRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void consumeBid(String message) {
        try {
            BidRequest request = objectMapper.readValue(message, BidRequest.class);

            BidData bid = new BidData();
            bid.setUserId(request.getUserId());
            bid.setAuctionId(request.getAuctionId());
            bid.setOfferedPrice(request.getOfferedPrice());

            bidRepository.save(bid);
            log.info("Service: Saved bid for Auction: {}", request.getAuctionId());
        } catch (Exception e) {
            log.error("Service Error: {}", e.getMessage());
        }
    }
}
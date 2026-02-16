package com.payaman.auctms.repository;

import com.payaman.auctms.entity.TransactionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDataRepository extends JpaRepository<TransactionData, String> {
    // Custom query to find existing bid for composite key logic if needed
    // BidData findByUserIdAndAuctionId(String userId, String auctionId);
}
package com.payaman.auctms.repository;

import com.payaman.auctms.entity.BidData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidDataRepository extends JpaRepository<BidData, String> {
}
package com.payaman.auctms.service;


import com.payaman.auctms.model.Auction;
import com.payaman.auctms.model.BidRequest;

public interface BidService {
    BidRequest get(Integer id) throws Exception;
    BidRequest create(BidRequest bidRequest) throws Exception;
}

package com.payaman.auctms.service;
import com.payaman.auctms.model.Auction;
public interface AuctionService {
	Auction[] getAll() throws Exception;
	Auction get(Integer id) throws Exception;
	Auction create(Auction auction) throws Exception;
	Auction update(Auction auction) throws Exception;
	void delete(Integer id) throws Exception;
}

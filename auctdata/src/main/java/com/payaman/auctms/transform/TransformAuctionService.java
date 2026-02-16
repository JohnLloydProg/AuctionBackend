package com.payaman.auctms.transform;
import com.payaman.auctms.entity.AuctionData;
import com.payaman.auctms.model.Auction;
public interface TransformAuctionService {
	AuctionData transform(Auction auction);
	Auction transform(AuctionData auctionData);
}

package com.payaman.auctms.transform;
import com.payaman.auctms.entity.AuctionData;
import com.payaman.auctms.model.Auction;
import org.springframework.stereotype.Service;
@Service
public class TransformAuctionServiceImpl implements TransformAuctionService {
	@Override
	public AuctionData transform(Auction auction){
		AuctionData auctionData = new AuctionData();
		auctionData.setId(auction.getId());
		auctionData.setAuctionId(auction.getAuctionId());
		auctionData.setItemId(auction.getItemId());
		auctionData.setStartingPrice(auction.getStartingPrice());
		auctionData.setCurrentPrice(auction.getCurrentPrice());
		auctionData.setStartTime(auction.getStartTime());
		auctionData.setEndTime(auction.getEndTime());
		auctionData.setStatus(auction.getStatus());
		auctionData.setCreatedAt(auction.getCreatedAt());
		return auctionData;
	}
	@Override

	public Auction transform(AuctionData auctionData){;
		Auction auction = new Auction();
		auction.setId(auctionData.getId());
		auction.setAuctionId(auctionData.getAuctionId());
		auction.setItemId(auctionData.getItemId());
		auction.setStartingPrice(auctionData.getStartingPrice());
		auction.setCurrentPrice(auctionData.getCurrentPrice());
		auction.setStartTime(auctionData.getStartTime());
		auction.setEndTime(auctionData.getEndTime());
		auction.setStatus(auctionData.getStatus());
		auction.setCreatedAt(auctionData.getCreatedAt());
		auction.setCreated(auctionData.getCreated());
		auction.setLastUpdated(auctionData.getLastUpdated());
		return auction;
	}
}

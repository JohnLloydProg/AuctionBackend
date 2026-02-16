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
		return auctionData;
	}
	@Override

	public Auction transform(AuctionData auctionData){;
		Auction auction = new Auction();
		auction.setId(auctionData.getId());
		auction.setCreated(auctionData.getCreated());
		auction.setLastUpdated(auctionData.getLastUpdated());
		return auction;
	}
}

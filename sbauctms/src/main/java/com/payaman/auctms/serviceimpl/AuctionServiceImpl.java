package com.payaman.auctms.serviceimpl;
import com.payaman.auctms.entity.AuctionData;
import com.payaman.auctms.entity.CategoryData;
import com.payaman.auctms.entity.ItemData;
import com.payaman.auctms.model.Auction;
import com.payaman.auctms.model.Category;
import com.payaman.auctms.model.Item;
import com.payaman.auctms.repository.AuctionDataRepository;
import com.payaman.auctms.repository.CategoryDataRepository;
import com.payaman.auctms.repository.ItemDataRepository;
import com.payaman.auctms.service.AuctionService;
import com.payaman.auctms.transform.TransformAuctionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class AuctionServiceImpl implements AuctionService {
	Logger logger = LoggerFactory.getLogger(AuctionServiceImpl.class);

	@Autowired
	AuctionDataRepository auctionDataRepository;

	@Autowired
	ItemDataRepository itemDataRepository;

	@Autowired
	CategoryDataRepository categoryDataRepository;

	public Item transform(ItemData itemData) {
		Item item = new Item();
		item.setId(itemData.getId());
		item.setName(itemData.getName());
		item.setDescription(itemData.getDescription());
		item.setSellerId(itemData.getSellerId());
		Optional<CategoryData> optional = categoryDataRepository.findById(itemData.getCategoryId());
		if (optional.isPresent()) {
			Category category = new Category();
			item.setCategory(category);
		}
		return item;
	}

	public AuctionData transform(Auction auction){
		AuctionData auctionData = new AuctionData();
		auctionData.setId(auction.getId());
		auctionData.setStartingPrice(auction.getStartingPrice());
		auctionData.setCurrentPrice(auction.getCurrentPrice());
		auctionData.setStartTime(auction.getStartTime());
		auctionData.setEndTime(auction.getEndTime());
		auctionData.setStatus(auction.getStatus());
		auctionData.setItemId(auction.getItem().getId());
		return auctionData;
	}

	public Auction transform(AuctionData auctionData){;
		Auction auction = new Auction();
		auction.setId(auctionData.getId());
		auction.setStartingPrice(auctionData.getStartingPrice());
		auction.setCurrentPrice(auctionData.getCurrentPrice());
		auction.setStartTime(auctionData.getStartTime());
		auction.setEndTime(auctionData.getEndTime());
		auction.setStatus(auctionData.getStatus());
		Optional<ItemData> optional = itemDataRepository.findById(auctionData.getItemId());
		if (optional.isPresent()) {
			auction.setItem(transform(optional.get()));
		}
		return auction;
	}

	@Override
	public Auction[] getAll() {
		List<AuctionData> auctionsData = new ArrayList<>();
		List<Auction> auctions = new ArrayList<>();
		auctionDataRepository.findAll().forEach(auctionsData::add);
		Iterator<AuctionData> it = auctionsData.iterator();
		while(it.hasNext()) {
			AuctionData auctionData = it.next();
			Auction auction = this.transform(auctionData);
			auctions.add(auction);
		}
		Auction[] array = new Auction[auctions.size()];
		for  (int i=0; i<auctions.size(); i++){
			array[i] = auctions.get(i);
		}
		return array;
	}
	@Override
	public Auction create(Auction auction) {
		logger.info(" add:Input " + auction.toString());
		AuctionData auctionData = this.transform(auction);
		auctionData = auctionDataRepository.save(auctionData);
		logger.info(" add:Input " + auctionData.toString());
			Auction newAuction = this.transform(auctionData);
		return newAuction;
	}

	@Override
	public Auction update(Auction auction) {
		Auction updatedAuction = null;
		int id = auction.getId();
		Optional<AuctionData> optional  = auctionDataRepository.findById(auction.getId());
		if(optional.isPresent()){
			AuctionData originalAuctionData = this.transform(auction);
			originalAuctionData.setCreated(optional.get().getCreated());
			AuctionData auctionData = auctionDataRepository.save(originalAuctionData);
			updatedAuction = this.transform(auctionData);
		}
		else {
			logger.error("Auction record with id: " + Integer.toString(id) + " do not exist ");

		}
		return updatedAuction;
	}

	@Override
	public Auction get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		Auction auction = null;
		Optional<AuctionData> optional = auctionDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			auction = this.transform(optional.get());
		}
		else {
			logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		}
		return auction;
	}
	@Override
	public void delete(Integer id) {
		Auction auction = null;
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<AuctionData> optional = auctionDataRepository.findById(id);
		if( optional.isPresent()) {
			AuctionData auctionDatum = optional.get();
			auctionDataRepository.delete(optional.get());
			logger.info(" Successfully deleted Auction record with id: " + Integer.toString(id));
		}
		else {
			logger.error(" Unable to locate auction with id:" +  Integer.toString(id));
		}
	}
}

package com.payaman.auctms.serviceimpl;

import com.payaman.auctms.entity.AuctionData;
import com.payaman.auctms.entity.BidData;
import com.payaman.auctms.entity.ItemData;
import com.payaman.auctms.entity.UserData;
import com.payaman.auctms.model.Auction;
import com.payaman.auctms.model.BidRequest;
import com.payaman.auctms.repository.AuctionDataRepository;
import com.payaman.auctms.repository.BidDataRepository;
import com.payaman.auctms.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class BidServiceImpl implements BidService {
    Logger logger = Logger.getLogger("Bid Service");

    @Autowired
    BidDataRepository bidDataRepository;

    @Autowired
    AuctionDataRepository auctionDataRepository;

    public BidRequest transform(BidData bidData) {
        BidRequest bidRequest = new BidRequest();
        bidRequest.setUserId(bidData.getUserId());
        bidRequest.setOfferedPrice(bidData.getOfferedPrice());
        bidRequest.setId(bidData.getId());
        return bidRequest;
    }

    public BidData transform(BidRequest bidRequest) {
        BidData bidData = new BidData();
        bidData.setUserId(bidRequest.getUserId());
        bidData.setOfferedPrice(bidRequest.getOfferedPrice());
        bidData.setId(bidRequest.getId());
        return bidData;
    }

    @Override
    public BidRequest get(Integer id) throws Exception {
        logger.info(" Input id >> "+  Integer.toString(id) );
        BidRequest bidRequest = null;
        Optional<BidData> optional = bidDataRepository.findById(id);
        if(optional.isPresent()) {
            logger.info(" Is present >> ");
            bidRequest = this.transform(optional.get());
        }
        else {
            logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
        }
        return bidRequest;
    }

    @Override
    public BidRequest create(BidRequest bidRequest) throws Exception {
        logger.info(" add:Input " + bidRequest.toString());
        BidData bidData = this.transform(bidRequest);
        bidData = bidDataRepository.save(bidData);
        logger.info(" add:Input " + bidData.toString());
        return this.transform(bidData);
    }
}

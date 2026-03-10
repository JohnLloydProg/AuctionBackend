package com.payaman.auctms.serviceimpl;

import com.payaman.auctms.entity.*;
import com.payaman.auctms.model.*;
import com.payaman.auctms.repository.*;
import com.payaman.auctms.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionDataRepository transactionDataRepository;

    @Autowired
    AuctionDataRepository auctionDataRepository;

    @Autowired
    ItemDataRepository itemDataRepository;

    @Autowired
    UserDataRepository userDataRepository;

    @Autowired
    BidDataRepository bidDataRepository;

    public User transform(UserData userData) {
        User user = new User();
        user.setUserId(userData.getUserId());
        user.setEmail(userData.getEmail());
        user.setStatus(userData.getStatus());
        user.setRole(userData.getRole());
        user.setPasswordHash(userData.getPasswordHash());
        user.setUsername(userData.getUsername());
        return user;
    }

    public Item transform(ItemData itemData) {
        Item item = new Item();
        item.setId(itemData.getId());
        item.setName(itemData.getName());
        item.setDescription(itemData.getDescription());
        Optional<UserData> optional = userDataRepository.findById(itemData.getSellerId());
        if (optional.isPresent()) {
            UserData userData = optional.get();
            item.setSeller(transform(userData));
        }
        return item;
    }

    public BidRequest transform(BidData bidData) {
        BidRequest bidRequest = new BidRequest();
        bidRequest.setUserId(bidData.getUserId());
        bidRequest.setOfferedPrice(bidData.getOfferedPrice());
        bidRequest.setId(bidData.getId());
        return bidRequest;
    }

    public Auction transform(AuctionData auctionData){;
        Auction auction = new Auction();
        auction.setId(auctionData.getId());
        auction.setStartingPrice(auctionData.getStartingPrice());
        Optional<BidData> optionalBidData = bidDataRepository.findById(auctionData.getCurrentBidId());
        if (optionalBidData.isPresent()) {
            auction.setCurrentBid(transform(optionalBidData.get()));
        }
        auction.setStartTime(auctionData.getStartTime());
        auction.setEndTime(auctionData.getEndTime());
        auction.setStatus(auctionData.getStatus());
        Optional<ItemData> optional = itemDataRepository.findById(auctionData.getItemId());
        if (optional.isPresent()) {
            auction.setItem(transform(optional.get()));
        }
        return auction;
    }

    public TransactionData transform(Transaction transaction) {
        TransactionData transactionData = new TransactionData();
        transactionData.setId(transaction.getId());
        transactionData.setAuctionId(transaction.getAuction().getId());
        transactionData.setBuyerId(transaction.getBuyer().getUserId());
        transactionData.setFinalAmount(transaction.getFinalAmount());
        return transactionData;
    }

    public Transaction transform(TransactionData transactionData) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionData.getId());
        Optional<AuctionData> optionalAuctionData = auctionDataRepository.findById(transactionData.getAuctionId());
        if (optionalAuctionData.isPresent()) {
            AuctionData auctionData = optionalAuctionData.get();
            transaction.setAuction(transform(auctionData));
        }
        Optional<UserData> optionalUserData = userDataRepository.findById(transactionData.getBuyerId());
        if (optionalUserData.isPresent()) {
            UserData userData = optionalUserData.get();
            transaction.setBuyer(transform(userData));
        }
        transaction.setFinalAmount(transactionData.getFinalAmount());
        return transaction;
    }

    @Override
    public Transaction create(Transaction transaction) throws Exception {
        TransactionData transactionData = transactionDataRepository.save(transform(transaction));
        return transform(transactionData);
    }

    @Override
    public Transaction[] getAll(Integer userId) throws Exception {
        List<TransactionData> transactionDataList = new ArrayList<>();
        List<Transaction> transactionList = new ArrayList<>();
        transactionDataRepository.findAll().forEach(transactionDataList::add);
        Iterator<TransactionData> it = transactionDataList.iterator();
        while(it.hasNext()) {
            TransactionData transactionData = it.next();
            if (transactionData.getBuyerId() == userId) {
                transactionList.add(this.transform(transactionData));
            }
        }
        Transaction[] array = new Transaction[transactionList.size()];
        for  (int i = 0; i< transactionList.size(); i++){
            array[i] = transactionList.get(i);
        }
        return array;
    }

    @Override
    public Transaction update(Transaction transaction) throws Exception {
        Optional<TransactionData> optionalTransactionData = transactionDataRepository.findById(transaction.getId());
        if (optionalTransactionData.isPresent()) {
            TransactionData newTransaction = transactionDataRepository.save(transform(transaction));
            return transform(newTransaction);
        }
        return transaction;
    }
}

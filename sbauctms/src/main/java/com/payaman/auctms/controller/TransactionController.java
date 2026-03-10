package com.payaman.auctms.controller;

import com.payaman.auctms.model.Item;
import com.payaman.auctms.model.Transaction;
import com.payaman.auctms.service.AuctionService;
import com.payaman.auctms.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    AuctionService  auctionService;


    @GetMapping("/{userId}")
    public ResponseEntity<?> getAll(@PathVariable final Integer userId) {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Transaction[] transactions = transactionService.getAll(userId);
            response =  ResponseEntity.ok().headers(headers).body(transactions);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Transaction transaction) {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Transaction newTransaction = transactionService.create(transaction);
            newTransaction.getAuction().setStatus("Done");
            newTransaction.setFinalAmount(newTransaction.getAuction().getCurrentBid().getOfferedPrice());
            auctionService.update(newTransaction.getAuction());
            transactionService.update(newTransaction);
            response = ResponseEntity.ok().headers(headers).body(newTransaction);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

}

package com.payaman.auctms;
import com.payaman.auctms.model.BidRequest;
import com.payaman.auctms.service.BidProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bids")
@RequiredArgsConstructor
public class BidController {

    private final BidProducerService bidProducerService;

    @PostMapping
    public ResponseEntity<String> placeBid(@RequestBody BidRequest request) {

        bidProducerService.sendBid(request);

        return ResponseEntity.accepted().body("Bid accepted for processing");
    }
}
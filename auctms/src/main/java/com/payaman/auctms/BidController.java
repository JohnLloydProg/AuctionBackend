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
        // Validation logic can go here (e.g., check if price > 0)

        // Async processing: Send to Kafka
        bidProducerService.sendBid(request);

        return ResponseEntity.accepted().body("Bid accepted for processing");
    }
}
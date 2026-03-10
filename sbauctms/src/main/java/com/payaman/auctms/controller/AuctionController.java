package com.payaman.auctms.controller;
import com.payaman.auctms.model.Auction;
import com.payaman.auctms.model.BidRequest;
import com.payaman.auctms.service.AuctionService;
import com.payaman.auctms.service.BidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auction")
public class AuctionController {
	Logger logger = LoggerFactory.getLogger( AuctionController.class);
	@Autowired
	private AuctionService auctionService;

	@Autowired
	private BidService bidService;

	@GetMapping
	public ResponseEntity<?> listAuction()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Auction[] auction = auctionService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(auction);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping
	public ResponseEntity<?> add(@RequestBody Auction auction){
		logger.info("Input >> " + auction.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Auction newAuction = auctionService.create(auction);
			logger.info("created auction >> " + newAuction.toString() );
			response = ResponseEntity.ok(newAuction);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve auction with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@PostMapping("/{id}/bid")
	public ResponseEntity<?> bid(@PathVariable final Integer id, @RequestBody BidRequest bidRequest) {
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			BidRequest newBidRequest = bidService.create(bidRequest);
			Auction auction = auctionService.get(id);
			if (auction.getCurrentBid() == null) {
				auction.setCurrentBid(newBidRequest);
				auctionService.update(auction);
				response = ResponseEntity.ok().headers(headers).body("Successful bid");
			}else {
				if (bidRequest.getOfferedPrice() > auction.getCurrentBid().getOfferedPrice()) {
					auction.setCurrentBid(newBidRequest);
					auctionService.update(auction);
					response = ResponseEntity.ok().headers(headers).body("Successful bid");
				} else {
					response = ResponseEntity.status(HttpStatus.CONFLICT).body("Offered price is not high enough");
				}
			}
		}catch (Exception ex) {
			logger.error("Failed to retrieve auction with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody Auction auction){
		logger.info("Update Input >> auction.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Auction newAuction = auctionService.update(auction);
			response = ResponseEntity.ok(auction);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve auction with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input auction id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Auction auction = auctionService.get(id);
			response = ResponseEntity.ok(auction);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			auctionService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}

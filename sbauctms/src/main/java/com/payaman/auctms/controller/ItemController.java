package com.payaman.auctms.controller;
import com.payaman.auctms.model.Item;
import com.payaman.auctms.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class ItemController {
	Logger logger = LoggerFactory.getLogger( ItemController.class);
	@Autowired
	private ItemService itemService;
	@GetMapping("/api/item")
	public ResponseEntity<?> listItem()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Item[] item = itemService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(item);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/item")
	public ResponseEntity<?> add(@RequestBody Item item){
		logger.info("Input >> " + item.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Item newItem = itemService.create(item);
			logger.info("created item >> " + newItem.toString() );
			response = ResponseEntity.ok(newItem);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve item with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/item")
	public ResponseEntity<?> update(@RequestBody Item item){
		logger.info("Update Input >> item.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Item newItem = itemService.update(item);
			response = ResponseEntity.ok(item);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve item with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/item/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input item id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Item item = itemService.get(id);
			response = ResponseEntity.ok(item);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/item/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			itemService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}

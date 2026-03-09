package com.payaman.auctms.controller;

import com.payaman.auctms.model.Item;
import com.payaman.auctms.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/item")
public class ItemController {
    Logger logger = Logger.getLogger("Item Controller");

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<?> listItem() {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Item[] items = itemService.getAll();
            response =  ResponseEntity.ok().headers(headers).body(items);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Item item) {
        logger.info("Input >> " + item.toString() );
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Item newItem = itemService.create(item);
            logger.info("created auction >> " + newItem.toString() );
            response = ResponseEntity.ok().headers(headers).body(newItem);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Item item) {
        logger.info("Update Input >> auction.toString() ");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Item newItem = itemService.update(item);
            response = ResponseEntity.ok().headers(headers).body(newItem);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable final Integer id) {
        logger.info("Input auction id >> " + Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Item item = itemService.get(id);
            response = ResponseEntity.ok().headers(headers).body(item);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id) {
        logger.info("Input >> " + Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            itemService.delete(id);
            response = ResponseEntity.ok().headers(headers).body(null);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

}

package com.payaman.auctms.kafka;

import com.payaman.auctms.entity.ItemData;
import com.payaman.auctms.model.Item;
import com.payaman.auctms.repository.ItemDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ItemListener {
    Logger logger = LoggerFactory.getLogger(ItemListener.class);

    @Autowired
    ItemDataRepository itemDataRepository;

    public ItemData transform(Item item) {
        ItemData itemData = new ItemData();
        itemData.setId(item.getId());
        itemData.setName(item.getName());
        itemData.setDescription(item.getDescription());
        itemData.setSellerId(item.getSellerId());
        itemData.setCategoryId(item.getCategory().getId());
        return itemData;
    }

    @KafkaListener(topics = "Item", groupId = "Payaman")
    public void consumeItem(Item item) {
        itemDataRepository.save(transform(item));
        logger.info("Recieved Item from kafka with id: " + item.getId());
    }

}

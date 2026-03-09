package com.payaman.auctms.serviceimpl;

import com.payaman.auctms.entity.AuctionData;
import com.payaman.auctms.entity.ItemData;
import com.payaman.auctms.entity.UserData;
import com.payaman.auctms.model.Auction;
import com.payaman.auctms.model.Item;
import com.payaman.auctms.model.User;
import com.payaman.auctms.repository.ItemDataRepository;
import com.payaman.auctms.repository.UserDataRepository;
import com.payaman.auctms.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {
    Logger logger = Logger.getLogger("Item Service");

    @Autowired
    ItemDataRepository itemDataRepository;

    @Autowired
    UserDataRepository userDataRepository;

    public Item transform(ItemData itemData) {
        Item item = new Item();
        item.setId(itemData.getId());
        item.setName(itemData.getName());
        item.setDescription(itemData.getDescription());
        Optional<UserData> optional = userDataRepository.findById(itemData.getSellerId());
        if (optional.isPresent()) {
            UserData userData = optional.get();
            User user = new User();
            user.setUserId(userData.getUserId());
            user.setEmail(userData.getEmail());
            user.setStatus(userData.getStatus());
            user.setRole(userData.getRole());
            user.setPasswordHash(userData.getPasswordHash());
            user.setUsername(userData.getUsername());
            item.setSeller(user);
        }
        return item;
    }

    public ItemData transform(Item item) {
        ItemData itemData = new ItemData();
        itemData.setId(item.getId());
        itemData.setName(item.getName());
        itemData.setDescription(item.getDescription());
        itemData.setSellerId(item.getSeller().getUserId());
        return itemData;
    }

    @Override
    public Item[] getAll() throws Exception {
        List<ItemData> itemDataList = new ArrayList<>();
        List<Item> itemList = new ArrayList<>();
        itemDataRepository.findAll().forEach(itemDataList::add);
        Iterator<ItemData> iterator = itemDataList.iterator();
        while (iterator.hasNext()) {
            ItemData itemData = iterator.next();
            itemList.add(transform(itemData));
        }
        Item[] array = new Item[itemList.size()];
        for (int i = 0; i < itemList.size(); i++) {
            array[i] = itemList.get(i);
        }
        return array;
    }

    @Override
    public Item get(Integer id) throws Exception {
        logger.info(" Input id >> "+  Integer.toString(id) );
        Item item = null;
        Optional<ItemData> optional = itemDataRepository.findById(id);
        if(optional.isPresent()) {
            logger.info(" Is present >> ");
            item = this.transform(optional.get());
        }
        else {
            logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
        }
        return item;
    }

    @Override
    public Item create(Item item) throws Exception {
        logger.info(" add:Input " + item.toString());
        ItemData itemData = this.transform(item);
        itemData = itemDataRepository.save(itemData);
        logger.info(" add:Input " + itemData.toString());
        return this.transform(itemData);
    }

    @Override
    public Item update(Item item) throws Exception {
        Item newItem = null;
        Optional<ItemData> optional = itemDataRepository.findById(item.getId());
        if (optional.isPresent()) {
            ItemData itemData = itemDataRepository.save(transform(item));
            newItem = transform(itemData);
        }else {
            logger.info("Item record with id: " + Integer.toString(item.getId()) + " do not exist ");
        }
        return newItem;
    }

    @Override
    public void delete(Integer id) throws Exception {
        Optional<ItemData> optional = itemDataRepository.findById(id);
        if (optional.isPresent()) {
            itemDataRepository.delete(optional.get());
            logger.info("Successfully deleted Auction record with id:" + Integer.toString(id));
        }else {
            logger.info("Unable to locate item with id:" +  Integer.toString(id));
        }
    }
}

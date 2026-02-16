package com.payaman.auctms.serviceimpl;
import com.payaman.auctms.entity.ItemData;
import com.payaman.auctms.model.Item;
import com.payaman.auctms.repository.ItemDataRepository;
import com.payaman.auctms.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class ItemServiceImpl implements ItemService {
	Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
	@Autowired
	ItemDataRepository itemDataRepository;
	@Autowired
	@Override
	public Item[] getAll() {
		List<ItemData> itemsData = new ArrayList<>();
		List<Item> items = new ArrayList<>();
		itemDataRepository.findAll().forEach(itemsData::add);
		Iterator<ItemData> it = itemsData.iterator();
		while(it.hasNext()) {
			ItemData itemData = it.next();
			Item item = new Item();
			item.setId(itemData.getId());
			item.setName(itemData.getName());
			items.add(item);
		}
		Item[] array = new Item[items.size()];
		for  (int i=0; i<items.size(); i++){
			array[i] = items.get(i);
		}
		return array;
	}
	@Override
	public Item create(Item item) {
		logger.info(" add:Input " + item.toString());
		ItemData itemData = new ItemData();
		itemData.setName(item.getName());
		itemData = itemDataRepository.save(itemData);
		logger.info(" add:Input " + itemData.toString());
			Item newItem = new Item();
			newItem.setId(itemData.getId());
			newItem.setName(itemData.getName());
		return newItem;
	}

	@Override
	public Item update(Item item) {
		Item updatedItem = null;
		int id = item.getId();
		Optional<ItemData> optional  = itemDataRepository.findById(item.getId());
		if(optional.isPresent()){
			ItemData originalItemData = new ItemData();
			originalItemData.setId(item.getId());
			originalItemData.setName(item.getName());
			originalItemData.setCreated(optional.get().getCreated());
			ItemData itemData = itemDataRepository.save(originalItemData);
			updatedItem = new Item();
			updatedItem.setId(itemData.getId());
			updatedItem.setName(itemData.getName());
			updatedItem.setCreated(itemData.getCreated());
			updatedItem.setLastUpdated(itemData.getLastUpdated());
		}
		else {
			logger.error("Item record with id: " + Integer.toString(id) + " do not exist ");

		}
		return updatedItem;
	}

	@Override
	public Item get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		Item item = null;
		Optional<ItemData> optional = itemDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			item = new Item();
			item.setId(optional.get().getId());
			item.setName(optional.get().getName());
			item.setCreated(optional.get().getCreated());
			item.setLastUpdated(optional.get().getLastUpdated());
		}
		else {
			logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		}
		return item;
	}
	@Override
	public void delete(Integer id) {
		Item item = null;
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<ItemData> optional = itemDataRepository.findById(id);
		if( optional.isPresent()) {
			ItemData itemDatum = optional.get();
			itemDataRepository.delete(optional.get());
			logger.info(" Successfully deleted Item record with id: " + Integer.toString(id));
			item = new Item();
			item.setId(optional.get().getId());
			item.setName(optional.get().getName());
			item.setCreated(optional.get().getCreated());
			item.setLastUpdated(optional.get().getLastUpdated());
		}
		else {
			logger.error(" Unable to locate item with id:" +  Integer.toString(id));
		}
	}
}

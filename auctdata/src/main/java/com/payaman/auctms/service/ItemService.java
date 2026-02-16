package com.payaman.auctms.service;
import com.payaman.auctms.model.Item;
public interface ItemService {
	Item[] getAll() throws Exception;
	Item get(Integer id) throws Exception;
	Item create(Item item) throws Exception;
	Item update(Item item) throws Exception;
	void delete(Integer id) throws Exception;
}

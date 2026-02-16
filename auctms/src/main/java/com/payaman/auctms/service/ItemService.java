package com.payaman.auctms.service;
import com.payaman.auctms.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ItemService {
	Logger logger = LoggerFactory.getLogger(ItemService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/item";

	protected static ItemService service= null;
	public static ItemService getService(){
		if(service == null){
			service = new ItemService();
		}
		return service;
	}

	RestTemplate restTemplate = null;
	public RestTemplate getRestTemplate() {
		if(restTemplate == null) {
		restTemplate = new RestTemplate();
			List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
			MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
			converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
			messageConverters.add(converter);
			restTemplate.setMessageConverters(messageConverters);
		}
		return restTemplate;
	}

	public Item get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Item> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Item.class);
		return response.getBody();
	}

	public Item[] getAll() {
		String url = endpointUrl;
		logger.info("getItems: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Item[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Item[].class);
		Item[] items = response.getBody();
		return items;
	}

	public Item create(Item item) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Item> request = new HttpEntity<>(item, headers);
		final ResponseEntity<Item> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, Item.class);
		return response.getBody();
	}
	public Item update(Item item) {
		logger.info("update: " + item.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Item> request = new HttpEntity<>(item, headers);
		final ResponseEntity<Item> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, Item.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Item> request = new HttpEntity<>(null, headers);
		final ResponseEntity<Item> response =
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, Item.class);
	}
}

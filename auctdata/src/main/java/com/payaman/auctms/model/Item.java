package com.payaman.auctms.model;
import lombok.Data;
import java.util.Date;

@Data
public class Item{
	private int id;
	private String name;
	private String description;
	private User seller;
	private Category category;
	private Date lastUpdated;
	private Date created;
}

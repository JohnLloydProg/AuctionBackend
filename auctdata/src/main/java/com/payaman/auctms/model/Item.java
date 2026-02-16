package com.payaman.auctms.model;
import lombok.Data;
import java.util.Date;

@Data
public class Item{
	private int id;
	private String name;
	private Date lastUpdated;
	private Date created;
}

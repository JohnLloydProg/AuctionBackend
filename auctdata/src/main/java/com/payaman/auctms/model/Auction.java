package com.payaman.auctms.model;
import lombok.Data;
import java.util.Date;

@Data
public class Auction{
	private int id;
	private int itemId;
	private float startingPrice;
	private float currentPrice;
	private Date startTime;
	private Date endTime;
	private String status;
	private Date createdAt;
	private Date lastUpdated;
	private Date created;
}

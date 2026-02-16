package com.payaman.auctms.model;
import lombok.Data;
import java.util.Date;

@Data
public class Auction{
	private int id;
	private Item item;
	private float startingPrice;
	private float currentPrice;
	private Date startTime;
	private Date endTime;
	private String status;
}

package com.payaman.auctms.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "Auction_data")
public class AuctionData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int itemId;

	private float startingPrice;
	private float currentPrice;
	private Date startTime;
	private Date endTime;
	private String status;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date lastUpdated;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date created;
}

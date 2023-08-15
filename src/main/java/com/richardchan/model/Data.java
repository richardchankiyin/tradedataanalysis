package com.richardchan.model;

import java.util.Date;

public class Data {
	private String id;
	private Integer sequence;
	private Double bidPrice;
	private Double askPrice;
	private Double tradePrice;
	private Integer bidVolume;
	private Integer askVolume;
	private Integer tradeVolume;
	private Integer updateType;
	private Date date;
	private Long seconds;
	private String conditionCodes;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Double getBidPrice() {
		return bidPrice;
	}
	public void setBidPrice(Double bidPrice) {
		this.bidPrice = bidPrice;
	}
	public Double getAskPrice() {
		return askPrice;
	}
	public void setAskPrice(Double askPrice) {
		this.askPrice = askPrice;
	}
	public Double getTradePrice() {
		return tradePrice;
	}
	public void setTradePrice(Double tradePrice) {
		this.tradePrice = tradePrice;
	}
	public Integer getBidVolume() {
		return bidVolume;
	}
	public void setBidVolume(Integer bidVolume) {
		this.bidVolume = bidVolume;
	}
	public Integer getAskVolume() {
		return askVolume;
	}
	public void setAskVolume(Integer askVolume) {
		this.askVolume = askVolume;
	}
	public Integer getTradeVolume() {
		return tradeVolume;
	}
	public void setTradeVolume(Integer tradeVolume) {
		this.tradeVolume = tradeVolume;
	}
	public Integer getUpdateType() {
		return updateType;
	}
	public void setUpdateType(Integer updateType) {
		this.updateType = updateType;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getSeconds() {
		return seconds;
	}
	public void setSeconds(Long seconds) {
		this.seconds = seconds;
	}
	public String getConditionCodes() {
		return conditionCodes;
	}
	public void setConditionCodes(String conditionCodes) {
		this.conditionCodes = conditionCodes;
	}
	
	
}

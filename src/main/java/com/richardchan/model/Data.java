package com.richardchan.model;

import java.util.Date;

public class Data {
	private String id;
	private Integer sequence;
	private Double bidPrice;
	private Character bidPriceLastDigit;
	private Double askPrice;
	private Character askPriceLastDigit;
	private Double tradePrice;
	private Character tradePriceLastDigit;
	private Integer bidVolume;
	private Character bidVolumeLastDigit;
	private Integer askVolume;
	private Character askVolumeLastDigit;
	private Integer tradeVolume;
	private Character tradeVolumeLastDigit;
	private Integer updateType;
	private Date date;
	private Long seconds;
	private String conditionCodes;
	public String getId() {
		return id;
	}
	public Character getBidPriceLastDigit() {
		return bidPriceLastDigit;
	}
	public void setBidPriceLastDigit(Character bidPriceLastDigit) {
		this.bidPriceLastDigit = bidPriceLastDigit;
	}
	public Character getAskPriceLastDigit() {
		return askPriceLastDigit;
	}
	public void setAskPriceLastDigit(Character askPriceLastDigit) {
		this.askPriceLastDigit = askPriceLastDigit;
	}
	public Character getTradePriceLastDigit() {
		return tradePriceLastDigit;
	}
	public void setTradePriceLastDigit(Character tradePriceLastDigit) {
		this.tradePriceLastDigit = tradePriceLastDigit;
	}
	public Character getBidVolumeLastDigit() {
		return bidVolumeLastDigit;
	}
	public void setBidVolumeLastDigit(Character bidVolumeLastDigit) {
		this.bidVolumeLastDigit = bidVolumeLastDigit;
	}
	public Character getAskVolumeLastDigit() {
		return askVolumeLastDigit;
	}
	public void setAskVolumeLastDigit(Character askVolumeLastDigit) {
		this.askVolumeLastDigit = askVolumeLastDigit;
	}
	public Character getTradeVolumeLastDigit() {
		return tradeVolumeLastDigit;
	}
	public void setTradeVolumeLastDigit(Character tradeVolumeLastDigit) {
		this.tradeVolumeLastDigit = tradeVolumeLastDigit;
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

package com.richardchan.model;

import java.util.logging.Logger;

import com.richardchan.NumericUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class InstrumentTradeQuoteData {
	private static final Logger LOGGER = Logger.getLogger(InstrumentTradeQuoteData.class.getName());
	private static final SimpleDateFormat DATEFMT = new SimpleDateFormat("yyyyMMdd"); 
	private String id;
	private List<Data> tradeData;
	private List<Data> quoteData;
	private List<Long> timeBtwTrades;
	private List<Long> timeBtwQuotes;
	private long totalTimeBtwTrades;
	private long totalTimeBtwQuotes;
	private long maxTimeBtwTrades;
	private long maxTimeBtwQuotes;
	
	public InstrumentTradeQuoteData(String id) {
		this.id = id;
		this.tradeData = new ArrayList<>(1000);
		this.quoteData = new ArrayList<>(1000);
		this.timeBtwTrades = new ArrayList<>(999);
		this.timeBtwQuotes = new ArrayList<>(999);
		this.totalTimeBtwTrades = 0l;
		this.totalTimeBtwQuotes = 0l;
		this.maxTimeBtwTrades = -1l;
		this.maxTimeBtwQuotes = -1l;
	}
	
	/**
	 * 0 = Bloomberg Code/Stock identifier
	 * 2 = Bid Price
	 * 3 = Ask Price
	 * 4 = Trade Price
	 * 5 = Bid Volume
	 * 6 = Ask Volume
	 * 7 = Trade Volume
	 * 8 = Update type => 1=Trade; 2= Change to Bid (Px or Vol); 3=Change to Ask (Px or Vol)
	 * 10 = Date
	 * 11 = Time in seconds past midnight
	 * 14 = Condition codes
	 * 
	 * only include the XT condition code (along with no condition code).
	 * 
	 * @param items
	 */
	public void processValues(String[] items) {
		if (items.length >= 15) {
			if ("XT".equals(items[14])) {
				try {
					Data d = new Data();
					Integer updateType = Integer.valueOf(items[8]);
					int updateTypeInt = updateType.intValue();
					d.setId(this.id);
					d.setSequence(Integer.valueOf(items[1]));
					d.setBidPrice(Double.valueOf(items[2]));
					d.setAskPrice(Double.valueOf(items[3]));
					d.setTradePrice(Double.valueOf(items[4]));
					d.setBidVolume(Integer.valueOf(items[5]));
					d.setAskVolume(Integer.valueOf(items[6]));
					d.setTradeVolume(Integer.valueOf(items[7]));
					d.setUpdateType(updateType);
					d.setDate(DATEFMT.parse(items[10]));
					d.setSeconds(Math.round(Double.valueOf(items[11])));
					d.setConditionCodes(items[14]);
					
					if (1 == updateTypeInt) {
						processTradeData(d);
					} else if (2 == updateTypeInt || 3 == updateTypeInt) {
						processQuoteData(d);
					} else {
						// ignore
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.warning("issue handling data: " + String.join(",", items));
				}
			} else {
				// ignore
			}
		} else {
			// ignore
		}
	}
	
	private void processTradeData(Data trade) {
		if (!tradeData.isEmpty()) {
			// get the last trade data item
			Data lastTradeData = tradeData.get(tradeData.size() - 1);
			long lastTradeTime = lastTradeData.getDate().getTime() / 1000 + lastTradeData.getSeconds();
			long thisTradeTime = trade.getDate().getTime() / 1000 + trade.getSeconds();
			long diff = thisTradeTime - lastTradeTime;
			timeBtwTrades.add(diff);
			totalTimeBtwTrades += diff;
			if (diff > maxTimeBtwTrades) {
				maxTimeBtwTrades = diff;
			}
		}
		// finally include this trade data in the list
		this.tradeData.add(trade);
	}
	
	//TODO to be implemented
	private void processQuoteData(Data quote) {
		
	}
	
	public double getMeanTimeBtwTrade() {
		return timeBtwTrades.isEmpty() ? Double.NaN : totalTimeBtwTrades / timeBtwTrades.size();
	}
	
	public double getMaxTimeBtwTrade() {
		return maxTimeBtwTrades == -1l ? Double.NaN : maxTimeBtwTrades;
	}
	
	public double getMedianTimeBtwTrade() {
		return NumericUtil.calculateMedian(timeBtwTrades);
	}
	
	public String summarize() {
		return "id:" + id + "|meantimebtwtrade:" + this.getMeanTimeBtwTrade() + "|medianbtwtrade:" + this.getMedianTimeBtwTrade() + "|longesttimebtwtrade:" + this.getMaxTimeBtwTrade();
	}
}
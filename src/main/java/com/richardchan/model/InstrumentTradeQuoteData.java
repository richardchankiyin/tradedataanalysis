package com.richardchan.model;

import java.util.logging.Logger;

import com.richardchan.NumericUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstrumentTradeQuoteData {
	private static final Logger LOGGER = Logger.getLogger(InstrumentTradeQuoteData.class.getName());
	private static final SimpleDateFormat DATEFMT = new SimpleDateFormat("yyyyMMdd");
	private String id;
	private List<Data> tradeData;
	private List<Data> quoteData;
	private List<Long> timeBtwTrades;
	private List<Long> timeBtwQuotes;
	private Map<Character, Long> tradePriceLastDigitOccurance;
	private Map<Character, Long> tradeVolumeLastDigitOccurance;
	private long totalTimeBtwTrades;
	private long totalTimeBtwQuotes;
	private long maxTimeBtwTrades;
	private long maxTimeBtwQuotes;
	private List<Double> bidAskSpreads;
	private double totalBidAskSpread;

	public InstrumentTradeQuoteData(String id) {
		this.id = id;
		this.tradeData = new ArrayList<>(1000);
		this.quoteData = new ArrayList<>(1000);
		this.timeBtwTrades = new ArrayList<>(999);
		this.timeBtwQuotes = new ArrayList<>(999);
		this.tradePriceLastDigitOccurance = initLastDigitOcurranceMap();
		this.tradeVolumeLastDigitOccurance = initLastDigitOcurranceMap();
		this.totalTimeBtwTrades = 0l;
		this.totalTimeBtwQuotes = 0l;
		this.maxTimeBtwTrades = -1l;
		this.maxTimeBtwQuotes = -1l;
		this.bidAskSpreads = new ArrayList<>(1000);
		this.totalBidAskSpread = 0d;
	}

	private static Map<Character, Long> initLastDigitOcurranceMap() {
		HashMap<Character, Long> m = new HashMap<>(10);
		m.put('0', Long.valueOf(0l));
		m.put('1', Long.valueOf(0l));
		m.put('2', Long.valueOf(0l));
		m.put('3', Long.valueOf(0l));
		m.put('4', Long.valueOf(0l));
		m.put('5', Long.valueOf(0l));
		m.put('6', Long.valueOf(0l));
		m.put('7', Long.valueOf(0l));
		m.put('8', Long.valueOf(0l));
		m.put('9', Long.valueOf(0l));
		return m;
	}

	/**
	 * 0 = Bloomberg Code/Stock identifier 2 = Bid Price 3 = Ask Price 4 = Trade
	 * Price 5 = Bid Volume 6 = Ask Volume 7 = Trade Volume 8 = Update type =>
	 * 1=Trade; 2= Change to Bid (Px or Vol); 3=Change to Ask (Px or Vol) 10 = Date
	 * 11 = Time in seconds past midnight 14 = Condition codes
	 * 
	 * only include the XT condition code (along with no condition code).
	 * 
	 * @param items
	 */
	public void processValues(String[] items) {
		if (items.length >= 15) {

			try {
				Data d = new Data();
				Integer updateType = Integer.valueOf(items[8]);
				int updateTypeInt = updateType.intValue();
				String conditionCode = items[14];
				d.setId(this.id);
				d.setSequence(Integer.valueOf(items[1]));
				d.setBidPrice(Double.valueOf(items[2]));
				d.setAskPrice(Double.valueOf(items[3]));
				d.setTradePrice(Double.valueOf(items[4]));
				d.setTradePriceLastDigit(Character.valueOf(items[4].charAt(items[4].length() - 1)));
				d.setBidVolume(Integer.valueOf(items[5]));
				d.setAskVolume(Integer.valueOf(items[6]));
				d.setTradeVolume(Integer.valueOf(items[7]));
				d.setTradeVolumeLastDigit(Character.valueOf(items[7].charAt(items[7].length() - 1)));
				d.setUpdateType(updateType);
				d.setDate(DATEFMT.parse(items[10]));
				d.setSeconds(Math.round(Double.valueOf(items[11])));
				d.setConditionCodes(conditionCode);

				if (1 == updateTypeInt) {
					if ("".equals(conditionCode) || "XT".equals(conditionCode)) {
						processTradeData(d);	
					}
				} else if (2 == updateTypeInt || 3 == updateTypeInt) {
					if ("".equals(conditionCode) || "XT".equals(conditionCode)) {
						processQuoteData(d);
					}
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
		tradePriceLastDigitOccurance.compute(trade.getTradePriceLastDigit(), (k, v) -> v + 1l);
		tradeVolumeLastDigitOccurance.compute(trade.getTradeVolumeLastDigit(), (k, v) -> v + 1l);

		// finally include this trade data in the list
		this.tradeData.add(trade);
	}

	private void processQuoteData(Data quote) {
		if (!quoteData.isEmpty()) {
			Data lastQuoteData = quoteData.get(quoteData.size() - 1);
			long lastQuoteTime = lastQuoteData.getDate().getTime() / 1000 + lastQuoteData.getSeconds();
			long thisQuoteTime = quote.getDate().getTime() / 1000 + quote.getSeconds();
			long diff = thisQuoteTime - lastQuoteTime;
			timeBtwQuotes.add(diff);
			totalTimeBtwQuotes += diff;
			if (diff > maxTimeBtwQuotes) {
				maxTimeBtwQuotes = diff;
			}
			
			double spread = quote.getAskPrice() - quote.getBidPrice();
			if (spread > 0) {
				totalBidAskSpread += spread;
				bidAskSpreads.add(spread);
			}
			
		}

		// finally include this quote data in the list
		this.quoteData.add(quote);
	}

	public double getMeanTimeBtwTrade() {
		return timeBtwTrades.isEmpty() ? Double.NaN : totalTimeBtwTrades / timeBtwTrades.size();
	}

	public double getMeanTimeBtwQuote() {
		return timeBtwQuotes.isEmpty() ? Double.NaN : totalTimeBtwQuotes / timeBtwQuotes.size();
	}

	public double getMaxTimeBtwTrade() {
		return maxTimeBtwTrades == -1l ? Double.NaN : maxTimeBtwTrades;
	}

	public double getMaxTimeBtwQuote() {
		return maxTimeBtwQuotes == -1l ? Double.NaN : maxTimeBtwQuotes;
	}

	public double getMedianTimeBtwTrade() {
		return NumericUtil.calculateMedian(timeBtwTrades);
	}

	public double getMedianTimeBtwQuote() {
		return NumericUtil.calculateMedian(timeBtwQuotes);
	}

	public double getPercentageOfOccuranceAsZeroAtTradePrice() {
		return tradeData.isEmpty() ? Double.NaN : 100 * tradePriceLastDigitOccurance.get('0') / (double) tradeData.size();
	}
	
	public double getPercentageOfOccuranceAsZeroAtTradeVolume() {
		return tradeData.isEmpty() ? Double.NaN : 100 * tradeVolumeLastDigitOccurance.get('0') / (double) tradeData.size();
	}
	
	public double getMeanBidAskSpread() {
		return bidAskSpreads.isEmpty() ? Double.NaN : totalBidAskSpread / bidAskSpreads.size();
	}
	
	public double getMedianBidAskSpread() {
		return NumericUtil.calculateMedianDouble(bidAskSpreads);
	}

	public String summarize() {
		return "id:" + id + "|meantimebtwtrades:" + this.getMeanTimeBtwTrade() + "|medianbtwtrades:"
				+ this.getMedianTimeBtwTrade() + "|longesttimebtwtrades:" + this.getMaxTimeBtwTrade()
				+ "|meantimebtwtickchanges:" + this.getMeanTimeBtwQuote() + "|medianbtwtickchanges:" + this.getMedianTimeBtwQuote()
				+ "|longesttimebtwtickchanges:" + this.getMaxTimeBtwQuote() + "|meanbidaskspread:" + this.getMeanBidAskSpread()
				+ "|medianbidaskspread:" + this.getMedianBidAskSpread()
				+ "|percentzeroaslastdigitattradeprice:" + this.getPercentageOfOccuranceAsZeroAtTradePrice()
				+ "|percentzeroaslastdigitattradevol:" + this.getPercentageOfOccuranceAsZeroAtTradeVolume()
				;
	}
}

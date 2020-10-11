package com.my.binance.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name="BinSymbolsDataEntity")
@Table(name="DB_BIN_SYMBOLS_DATA",indexes=
{@Index(columnList="symbol,bininterval,opentime"),@Index(columnList="symbol,bininterval,closetime")})
@XmlRootElement
public class BinSymbolsDataModel
{
	@Id
	@GeneratedValue
	private long id;
	
	@JsonProperty("symbol")
	String symbol;
	
	@JsonProperty("bininterval")
	String bininterval;
	
	@JsonProperty("opentime")
	long openTime;
	
	@JsonProperty("closetime")
	double closeTime;
	
	@JsonProperty("openvalue")
	double openValue;
	
	@JsonProperty("closevalue")
	double closeValue;
	
	@JsonProperty("highvalue")
	double highValue;
	
	@JsonProperty("lowvalue")
	double lowValue;
	
	@JsonProperty("quoteassetvolume")
	double quoteAssetVolume;
	
	@JsonProperty("numberoftrades")
	long numberOfTrades;
	
	@JsonProperty("takerbuybaseassetvolume")
	double takerBuyBaseAssetVolume;
	
	@JsonProperty("takerbuyquoteassetvolume")
	double takerBuyQuoteAssetVolume;
	
	@JsonProperty("ignore")
	double ignore;
	
	public long getId()
	{
		return id;
	}
	
	public void setId(long id)
	{
		this.id=id;
	}
	
	public String getSymbol()
	{
		return symbol;
	}
	
	public void setSymbol(String symbol)
	{
		this.symbol=symbol;
	}
	
	public String getBininterval()
	{
		return bininterval;
	}
	
	public void setBininterval(String bininterval)
	{
		this.bininterval=bininterval;
	}
	
	public long getOpenTime()
	{
		return openTime;
	}
	
	public void setOpenTime(long openTime)
	{
		this.openTime=openTime;
	}
	
	public double getCloseTime()
	{
		return closeTime;
	}
	
	public void setCloseTime(double closeTime)
	{
		this.closeTime=closeTime;
	}
	
	public double getOpenValue()
	{
		return openValue;
	}
	
	public void setOpenValue(double openValue)
	{
		this.openValue=openValue;
	}
	
	public double getCloseValue()
	{
		return closeValue;
	}
	
	public void setCloseValue(double closeValue)
	{
		this.closeValue=closeValue;
	}
	
	public double getHighValue()
	{
		return highValue;
	}
	
	public void setHighValue(double highValue)
	{
		this.highValue=highValue;
	}
	
	public double getLowValue()
	{
		return lowValue;
	}
	
	public void setLowValue(double lowValue)
	{
		this.lowValue=lowValue;
	}
	
	public double getQuoteAssetVolume()
	{
		return quoteAssetVolume;
	}
	
	public void setQuoteAssetVolume(double quoteAssetVolume)
	{
		this.quoteAssetVolume=quoteAssetVolume;
	}
	
	public long getNumberOfTrades()
	{
		return numberOfTrades;
	}
	
	public void setNumberOfTrades(long numberOfTrades)
	{
		this.numberOfTrades=numberOfTrades;
	}
	
	public double getTakerBuyBaseAssetVolume()
	{
		return takerBuyBaseAssetVolume;
	}
	
	public void setTakerBuyBaseAssetVolume(double takerBuyBaseAssetVolume)
	{
		this.takerBuyBaseAssetVolume=takerBuyBaseAssetVolume;
	}
	
	public double getTakerBuyQuoteAssetVolume()
	{
		return takerBuyQuoteAssetVolume;
	}
	
	public void setTakerBuyQuoteAssetVolume(double takerBuyQuoteAssetVolume)
	{
		this.takerBuyQuoteAssetVolume=takerBuyQuoteAssetVolume;
	}
	
	public double getIgnore()
	{
		return ignore;
	}
	
	public void setIgnore(double ignore)
	{
		this.ignore=ignore;
	}
	
	@Override
	public String toString()
	{
		return "BinSymbolsDataModel [id="+id+", symbol="+symbol+", bininterval="+bininterval+", openTime="+openTime+", closeTime="+closeTime+", openValue="+openValue+", closeValue="+closeValue+", highValue="+highValue+", lowValue="+lowValue+", quoteAssetVolume="+quoteAssetVolume
		+", numberOfTrades="+numberOfTrades+", takerBuyBaseAssetVolume="+takerBuyBaseAssetVolume+", takerBuyQuoteAssetVolume="+takerBuyQuoteAssetVolume+", ignore="+ignore+"]";
	}
	
}

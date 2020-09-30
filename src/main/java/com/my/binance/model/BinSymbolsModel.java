package com.my.binance.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name = "BinSymbolsEntity")
@Table(name="DB_BIN_SYMBOLS")
@XmlRootElement
public class BinSymbolsModel
{
	@Id
	@GeneratedValue
	private int id;
	
	@JsonProperty("symbol")
	private String symbol;
	
	@JsonProperty("baseAsset")
	private String baseAsset;
	
	@JsonProperty("quoteAsset")
	private String quoteAsset;
	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
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
	
	public String getBaseAsset()
	{
		return baseAsset;
	}
	
	public void setBaseAsset(String baseAsset)
	{
		this.baseAsset=baseAsset;
	}
	
	public String getQuoteAsset()
	{
		return quoteAsset;
	}
	
	public void setQuoteAsset(String quoteAsset)
	{
		this.quoteAsset=quoteAsset;
	}

	@Override
	public String toString()
	{
		return "BinSymbols [id="+id+", symbol="+symbol+", baseAsset="+baseAsset+", quoteAsset="+quoteAsset+"]";
	}
	
	
	
}

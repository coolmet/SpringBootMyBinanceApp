package com.my.binance.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name="BinSymbolsEntity")
@Table(name="DB_BIN_SYMBOLS")
@XmlRootElement
public class BinSymbolsModel
{
	@Id
	@GeneratedValue
	private long id;
	
	@JsonProperty("favid")
	@Column(columnDefinition="integer default 0")
	private Integer favId;
	
	@JsonProperty("symbol")
	private String symbol;
	
	@JsonProperty("baseAsset")
	private String baseAsset;
	
	@JsonProperty("quoteAsset")
	private String quoteAsset;
	
	public long getId()
	{
		return id;
	}
	
	public void setId(long id)
	{
		this.id=id;
	}
	
	public Integer getFavId()
	{
		return favId;
	}
	
	public void setFavId(Integer favId)
	{
		this.favId=favId;
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
		return "BinSymbolsModel [id="+id+", favId="+favId+", symbol="+symbol+", baseAsset="+baseAsset+", quoteAsset="+quoteAsset+"]";
	}
	
}

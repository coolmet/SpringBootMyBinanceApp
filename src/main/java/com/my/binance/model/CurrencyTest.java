package com.my.binance.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="DB_CURRENCY_TEST")
@XmlRootElement
public class CurrencyTest
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@NotEmpty
	@Column(name="currency")
	@JsonProperty("currency")
	private String currency;
	@NotEmpty
	@Column(name="content")
	@JsonProperty("content")
	private String content;
	
	public long getId()
	{
		return id;
	}
	
	public void setId(long id)
	{
		this.id=id;
	}
	
	public String getCurrency()
	{
		return currency;
	}
	
	public void setCurrency(String currency)
	{
		this.currency=currency;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public void setContent(String content)
	{
		this.content=content;
	}
	
}

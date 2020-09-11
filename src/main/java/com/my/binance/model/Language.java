package com.my.binance.model;

import java.util.Locale;

public class Language
{
	private int id;
	private String imagePath;
	private String displayName;
	private String languageCode;
	private String countryCode;
	private String localeLongName;
	private Locale locale;
	private String edplang;
	
	public Language()
	{
	}
	
	public Language(int id,String imagePath,String displayName,String languageCode,String countryCode,String localeLongName,Locale locale,String edplang)
	{
		super();
		this.id=id;
		this.imagePath=imagePath;
		this.displayName=displayName;
		this.languageCode=languageCode;
		this.countryCode=countryCode;
		this.localeLongName=localeLongName;
		this.locale=locale;
		this.edplang=edplang;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id=id;
	}
	
	public String getImagePath()
	{
		return imagePath;
	}
	
	public void setImagePath(String imagePath)
	{
		this.imagePath=imagePath;
	}
	
	public String getDisplayName()
	{
		return displayName;
	}
	
	public void setDisplayName(String displayName)
	{
		this.displayName=displayName;
	}
	
	public String getLanguageCode()
	{
		return languageCode;
	}
	
	public void setLanguageCode(String languageCode)
	{
		this.languageCode=languageCode;
	}
	
	public String getCountryCode()
	{
		return countryCode;
	}
	
	public void setCountryCode(String countryCode)
	{
		this.countryCode=countryCode;
	}
	
	public String getLocaleLongName()
	{
		return localeLongName;
	}
	
	public void setLocaleLongName(String localeLongName)
	{
		this.localeLongName=localeLongName;
	}
	
	public Locale getLocale()
	{
		return locale;
	}
	
	public void setLocale(Locale locale)
	{
		this.locale=locale;
	}
	
	public String getEdplang()
	{
		return edplang;
	}
	
	public void setEdplang(String edplang)
	{
		this.edplang=edplang;
	}
	
}

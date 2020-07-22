package com.my.binance.model;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClsLine
{
	@JsonProperty("NwinsiparisDetID")
	public long nwinsiparisDetID;
	
	@JsonProperty("BirimCinsi")
	public String birimCinsi;
	
	@JsonProperty("BirimFiyat")
	public double birimFiyat;
	
	@JsonProperty("HBirim")
	public String hBirim;
	
	@JsonProperty("IlkTeslimTarihi")
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="Europe/Istanbul")
	public Date ilkTeslimTarihi;
	
	@JsonProperty("LotTanim")
	public String lotTanim;
	
	@JsonProperty("AnaKumasOzelNot")
	public String anaKumasOzelNot;
	
	@JsonProperty("IsEmriNo")
	public String isEmriNo;
	
	@JsonProperty("HModelAdi")
	public String hModelAdi;
	
	@JsonProperty("HModelKodu")
	String hModelKodu;
	
	@JsonProperty("Miktar")
	public double miktar;
	
	@Override
	public String toString()
	{
		return "TestClsLine [nwinsiparisDetID="+nwinsiparisDetID+", birimCinsi="+birimCinsi+", birimFiyat="+birimFiyat+", hBirim="+hBirim+", ilkTeslimTarihi="+ilkTeslimTarihi+", lotTanim="+lotTanim+", anaKumasOzelNot="+anaKumasOzelNot+", isEmriNo="+isEmriNo+", hModelAdi="+hModelAdi+", hModelKodu="
		+hModelKodu+", miktar="+miktar+"]";
	}
	
}

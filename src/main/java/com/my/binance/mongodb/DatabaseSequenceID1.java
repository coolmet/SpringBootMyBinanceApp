package com.my.binance.mongodb;

import javax.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="database_sequences_id1")
public class DatabaseSequenceID1
{
	
	@Id
	private String id;
	
	private long seq;
	
	public DatabaseSequenceID1()
	{
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id=id;
	}
	
	public long getSeq()
	{
		return seq;
	}
	
	public void setSeq(long seq)
	{
		this.seq=seq;
	}
}

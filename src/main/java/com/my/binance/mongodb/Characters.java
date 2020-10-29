package com.my.binance.mongodb;

import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonIgnore;

// @Document(collection="Characters")
@Document(collection="#{T(com.my.binance.mongodb.CustomMongoCollection).getCharacterColName()}")
public class Characters
{
	@Transient
	public static final String SEQUENCE_NAME="database_sequences_id1";
	
	@Id
	@Field("_id")
	@JsonIgnore
	private String id;
	
	@Field("my_object_id")
	private Long myObjectId;
	
	@Indexed(unique=true)
	private String name;
	
	public Characters()
	{
		super();
	}
	
	public Characters(String name)
	{
		super();
		this.name=name;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id=id;
	}
	
	public Long getMyObjectId()
	{
		return myObjectId;
	}
	
	public void setMyObjectId(Long myObjectId)
	{
		this.myObjectId=myObjectId;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	
	@Override
	public String toString()
	{
		return "Characters [id="+id+", myObjectId="+myObjectId+", name="+name+"]";
	}
	
}

package com.my.binance.mongodb;

import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import com.my.binance.ConfigPropertiesSpring.Mvc;

@Component
public class CustomMongoCollection
{
	private static String characterColName;
	
	public static String getCharacterColName()
	{
		return characterColName;
	}
	
	@Value("${mongodb.collection.characters}")
	public void setCharacterColName(String characterColName)
	{
		CustomMongoCollection.characterColName=characterColName;
	}
	
}

package com.my.binance.mongodb;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfiguration
{
	String DATABASE="mydatabase";
	
	public @Bean MongoClient myMongoClient()
	{
		ConnectionString connectionString=new ConnectionString("mongodb://localhost:27017/"+DATABASE);
		MongoClientSettings mongoClientSettings=MongoClientSettings.builder()
		                                                           .applyConnectionString(connectionString)
		                                                           .build();
		// return MongoClients.create("mongodb://localhost:27017");
		return MongoClients.create(mongoClientSettings);
	}
	
	public @Bean MongoClientFactoryBean myMongo()
	{
		MongoClientFactoryBean mongo=new MongoClientFactoryBean();
		mongo.setHost("localhost");
		return mongo;
	}
	
	public @Bean MongoDatabaseFactory myMongoDatabaseFactory()
	{
		// MongoClient mongoClient = MongoClients.create(new ConnectionString("mongodb://subjectName@host1/?authMechanism=MONGODB-X509&streamType=netty&ssl=true"));
		return new SimpleMongoClientDatabaseFactory(MongoClients.create(),DATABASE+"2");
	}
	
	@Bean
	public MongoTemplate mongoTemplate()
	{
		return new MongoTemplate(myMongoClient(),DATABASE);
	}
	
}

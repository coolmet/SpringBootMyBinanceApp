package spring.my.binance.test;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.my.binance.SpringBootAppStarter;
import com.my.binance.mongodb.Characters;
import com.my.binance.mongodb.CharactersService;
import com.my.binance.mongodb.SequenceGeneratorService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.MongoOperations;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SpringBootAppStarter.class)
@ActiveProfiles(profiles="test")
@EnableJpaRepositories(basePackages="com.my.binance.repository")
@EnableMongoRepositories("com.my.binance.mongodb")
@TestPropertySource("classpath:/config/appconfig.properties")
@TestPropertySource("classpath:/config/webserverconfig.properties")
@TestPropertySource("classpath:/config/userlistconfig.yml")
@TestPropertySource("classpath:/config/logconfig.yml")
@EnableTransactionManagement
@ServletComponentScan
@EnableAutoConfiguration
@EnableScheduling
@Transactional
public class MongoTest
{
	@Autowired
	private CharactersService characterService;
	
	private @Autowired MongoDatabaseFactory mongoDatabaseFactory;
	private @Autowired MongoClient myMongoClient;
	private @Autowired SequenceGeneratorService sequenceGeneratorService;
	
	@Test
	public void mongoDbCreateCharacter()
	{
		Characters c=new Characters();
		c.setName("Name"+System.currentTimeMillis());
		c.setMyObjectId(sequenceGeneratorService.generateSequence(Characters.SEQUENCE_NAME));
		characterService.saveCharacter(c);
	}
	
	@Test
	public void mongoDbFindCharacter()
	{
		mongoDbCreateCharacter();
		List<Characters> characters=characterService.findAllCharacters();
		characters.stream().forEach(System.out::println);
	}
	
	@Test
	public void mongoDbCreateDB()
	{
		// mongoDatabaseFactory.getMongoDatabase().createCollection("aaa");
		// MongoOperations mongoOps=new MongoTemplate(MongoClients.create(),"demoDB");
		// MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new Mongo(), "database"));
		MongoOperations mongoOps=new MongoTemplate(myMongoClient,"demoDB");
		mongoOps.insert(new Characters("Joe"+System.currentTimeMillis()));
		System.out.println(mongoOps.findOne(new Query(Criteria.where("name").in("Joe")),Characters.class));
		// mongoOps.dropCollection("Characters");
	}
	
}

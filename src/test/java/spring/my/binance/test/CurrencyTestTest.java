package spring.my.binance.test;

import java.util.List;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import com.my.binance.ConfigPropertiesReloadeble;
import com.my.binance.SpringBootAppStarter;
import com.my.binance.model.CurrencyTest;
import com.my.binance.service.CurrencyTestService;
import com.my.binance.service.ReloadablePropertiesService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SpringBootAppStarter.class)
@ActiveProfiles(profiles="test")
@EnableJpaRepositories(basePackages="com.my.binance.repository")
@TestPropertySource("classpath:/config/appconfig.properties")
@TestPropertySource("classpath:/config/webserverconfig.properties")
@TestPropertySource("classpath:/config/userlistconfig.yml")
@TestPropertySource("classpath:/config/logconfig.yml")
@EnableTransactionManagement
@ServletComponentScan
@EnableAutoConfiguration
@EnableScheduling
@Transactional
public class CurrencyTestTest
{
	
	
	@Autowired
	private CurrencyTestService currencyTestService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Test
	public void generateEncodedPasswords()
	{
		BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
		System.out.println("{bcrypt}"+passwordEncoder.encode("secret"));
		System.out.println("{bcrypt}"+passwordEncoder.encode("secret"));
		System.out.println("{bcrypt}"+passwordEncoder.encode("secret"));
		
	}
	
	@Test
	public void testSizeOfCurrencyTests()
	{
		List<CurrencyTest> currencyTests=currencyTestService.findAll();
		MatcherAssert.assertThat(currencyTests.size(),Matchers.equalTo(2));
	}
	
	@Test
	public void testAddCurrencies()
	{
		CurrencyTest currencyTest=new CurrencyTest();
		currencyTest.setCurrency("XXX1");
		currencyTest.setContent("XXX1 content");
		currencyTestService.create(currencyTest);
		System.out.println("Test:1#"+currencyTestService.findByCurrency("XXX1").getId()+"\t"+currencyTestService.findByCurrency("XXX1").getContent());
		//
		currencyTest=new CurrencyTest();
		currencyTest.setCurrency("XXX2");
		currencyTest.setContent("XXX2 content");
		currencyTestService.create(currencyTest);
		System.out.println("Test:1#"+currencyTestService.findByCurrency("XXX2").getId()+"\t"+currencyTestService.findByCurrency("XXX2").getContent());
		
	}
	
	@Test
	public void testFindCurrencies()
	{
		List<CurrencyTest> currencyTests=currencyTestService.findAll();
		System.out.println("Test:1#"+currencyTests.size());
		System.out.println("Test:2#"+currencyTests.toString());
		System.out.println("Test:3#"+currencyTestService.findByCurrency("XXX2").getContent());
		MatcherAssert.assertThat(currencyTests.size(),Matchers.equalTo(4));
	}
}

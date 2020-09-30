package spring.my.binance.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.io.IOUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import com.my.binance.model.BinSymbolsModel;
import com.my.binance.model.CurrencyTest;
import com.my.binance.service.BinSymbolsService;
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
	private BinSymbolsService binSymbolsService;
	
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
	public void testJsonData()
	{
		BinSymbolsModel binSymbolsModel;
		String XURL="https://www.binance.com/api/v1/exchangeInfo";
		try
		{
			JSONObject jo=new JSONObject(IOUtils.toString(new URL(XURL).openStream(),StandardCharsets.UTF_8));
			JSONArray ja=jo.getJSONArray("symbols");
			for(int i=0;i<ja.length();i++)
			{
				jo=ja.getJSONObject(i);
				try
				{
					binSymbolsModel=new BinSymbolsModel();
					binSymbolsModel.setSymbol(jo.getString("symbol"));
					binSymbolsModel.setBaseAsset(jo.getString("baseAsset"));
					binSymbolsModel.setQuoteAsset(jo.getString("quoteAsset"));
					if(binSymbolsService.findAllBySymbol(jo.getString("symbol")).isEmpty())
					{
						binSymbolsService.create(binSymbolsModel);
						System.out.println("+++\t"+binSymbolsModel.toString()+"\t\t"+binSymbolsService.findBySymbol(jo.getString("symbol")).getId());

					}
					else
					{
						System.out.println("&&&\t"+binSymbolsModel.toString());
					}
				}
				catch(Exception rt)
				{
					System.out.println("Hata-1:"+rt.getLocalizedMessage());
					
				}
			}
		}
		catch(Exception rt)
		{
			System.out.println("Hata-2:"+rt.getLocalizedMessage());
			
		}
	}
	
	@Test
	public void testUrlData()
	{
		String XURL="https://www.binance.com/api/v1/exchangeInfo";
		String result="";
		try
		{
			URLConnection conn=new URL(XURL).openConnection();
			try(BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream(),StandardCharsets.UTF_8)))
			{
				result=reader.lines().collect(Collectors.joining("\n"));
			}
			System.out.println("1::"+result.length());
			System.out.println("1::"+result);
		}
		catch(Exception e)
		{
			System.out.println("1__"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		// **************************
		try
		{
			HttpsURLConnection con=null;
			try
			{
				URL u=new URL(XURL);
				con=(HttpsURLConnection)u.openConnection();
				
				con.connect();
				
				BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuilder sb=new StringBuilder();
				String line;
				while((line=br.readLine())!=null)
				{
					sb.append(line+"\n");
				}
				br.close();
				result=sb.toString();
				
			}
			catch(Exception ex)
			{
				System.out.println(ex.getLocalizedMessage());
				ex.printStackTrace();
			}
			
			finally
			{
				if(con!=null)
				{
					try
					{
						con.disconnect();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
			System.out.println("2::"+result.length());
			System.out.println("2::"+result);
		}
		catch(Exception e)
		{
			System.out.println("2__"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		// **************************
		try
		{
			try(Scanner scanner=new Scanner(new URL(XURL).openStream(),
			                                StandardCharsets.UTF_8.toString()))
			{
				scanner.useDelimiter("\\A");
				result=scanner.hasNext()?scanner.next():"";
			}
			System.out.println("3::"+result.length());
			System.out.println("3::"+result);
		}
		catch(Exception e)
		{
			System.out.println("3__"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		// **************************
		try
		{
			result=IOUtils.toString(new URL(XURL).openStream(),StandardCharsets.UTF_8);
			System.out.println("4::"+result.length());
			System.out.println("4::"+result);
		}
		catch(Exception e)
		{
			System.out.println("4__"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		
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

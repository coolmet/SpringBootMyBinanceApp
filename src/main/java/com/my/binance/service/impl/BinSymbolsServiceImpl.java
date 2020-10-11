package com.my.binance.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.my.binance.SprinBootAppConfiguration;
import com.my.binance.model.BinLinks;
import com.my.binance.model.BinSymbolsModel;
import com.my.binance.repository.BinSymbolsRepository;
import com.my.binance.service.BinSymbolsService;

@Service
@Transactional(rollbackFor=Exception.class)
public class BinSymbolsServiceImpl implements BinSymbolsService
{
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	private BinSymbolsRepository binSymbolsRepository;
	
	public BinSymbolsRepository getBinSymbolsRepository()
	{
		return binSymbolsRepository;
	}
	
	@Autowired
	public void setBinSymbolsRepository(BinSymbolsRepository binSymbolsRepository)
	{
		this.binSymbolsRepository=binSymbolsRepository;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<BinSymbolsModel> findAll()
	{
		return binSymbolsRepository.findAll();
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public BinSymbolsModel findById(Long id)
	{
		return binSymbolsRepository.findById(id);
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public BinSymbolsModel findByFavId(int favId)
	{
		return binSymbolsRepository.findByFavId(favId);
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public BinSymbolsModel findBySymbol(String symbol)
	{
		return binSymbolsRepository.findBySymbol(symbol);
		
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public BinSymbolsModel findByBaseAsset(String baseAsset)
	{
		return binSymbolsRepository.findByBaseAsset(baseAsset);
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public BinSymbolsModel findByQuoteAsset(String quoteAsset)
	{
		return binSymbolsRepository.findByQuoteAsset(quoteAsset);
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<BinSymbolsModel> findAllByFavId(int favId)
	{
		return binSymbolsRepository.findAllByFavId(favId);
		
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<BinSymbolsModel> findAllBySymbol(String symbol)
	{
		return binSymbolsRepository.findAllBySymbol(symbol);
		
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<BinSymbolsModel> findAllByBaseAsset(String baseAsset)
	{
		return binSymbolsRepository.findAllByBaseAsset(baseAsset);
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<BinSymbolsModel> findAllByQuoteAsset(String quoteAsset)
	{
		return binSymbolsRepository.findAllByQuoteAsset(quoteAsset);
	}
	
	@Override
	public void create(BinSymbolsModel binSymbolsModel)
	{
		binSymbolsRepository.create(binSymbolsModel);
	}
	
	@Override
	public BinSymbolsModel update(BinSymbolsModel binSymbolsModel)
	{
		return binSymbolsRepository.update(binSymbolsModel);
	}
	
	@Override
	public void delete(Long id)
	{
		binSymbolsRepository.delete(id);
	}
	
	@Override
	public void deleteByBaseAsset(String baseAsset)
	{
		binSymbolsRepository.deleteByBaseAsset(baseAsset);
	}
	
	@Override
	public void deleteByQuoteAsset(String quoteAsset)
	{
		binSymbolsRepository.deleteByQuoteAsset(quoteAsset);
	}
	
	@Override
	public void updateAllFromJson()
	{
		List<BinSymbolsModel> vecBinSymbolsModelJson=new ArrayList<BinSymbolsModel>();
		List<BinSymbolsModel> vecBinSymbolsModelDB=this.findAll();
		//
		BinSymbolsModel binSymbolsModelJson;
		BinSymbolsModel binSymbolsModelDB;
		try
		{
			JSONObject jo=new JSONObject(IOUtils.toString(new URL(BinLinks.URL_SYMBOLS).openStream(),StandardCharsets.UTF_8));
			JSONArray ja=jo.getJSONArray("symbols");
			for(int i=0;i<ja.length();i++)
			{
				jo=ja.getJSONObject(i);
				try
				{
					binSymbolsModelJson=new BinSymbolsModel();
					binSymbolsModelJson.setSymbol(jo.getString("symbol"));
					binSymbolsModelJson.setFavId(0);
					binSymbolsModelJson.setBaseAsset(jo.getString("baseAsset"));
					binSymbolsModelJson.setQuoteAsset(jo.getString("quoteAsset"));
					if(jo.getString("symbol").equals("BTCUSDT")||jo.getString("symbol").equals("ETHUSDT"))
					{
						binSymbolsModelJson.setFavId(1);
					}
					//
					binSymbolsModelDB=this.findBySymbol(jo.getString("symbol"));
					vecBinSymbolsModelJson.add(binSymbolsModelDB);
					if(binSymbolsModelDB.getId()==-1)
					{
						this.create(binSymbolsModelJson);
						LOGGER.debug("Creating\t"+binSymbolsModelJson.toString());
					}
					else
					{
						binSymbolsModelJson.setId(binSymbolsModelDB.getId());
						binSymbolsModelJson.setFavId(binSymbolsModelDB.getFavId());
						this.update(binSymbolsModelJson);
						LOGGER.debug("Updating\t"+binSymbolsModelJson.toString());
					}
				}
				catch(Exception rt)
				{
					StringWriter sw=new StringWriter();
					rt.printStackTrace(new PrintWriter(sw));
					LOGGER.error("updateAllFromJson-1:"+sw.toString());
					
				}
			}
			//
			List<BinSymbolsModel> result=vecBinSymbolsModelDB.stream()
			                                                 .filter(el->vecBinSymbolsModelJson.stream().noneMatch(el::equals))
			                                                 .collect(Collectors.toList());
			result.stream().forEach(f->this.delete(f.getId()));
		}
		catch(Exception rt)
		{
			StringWriter sw=new StringWriter();
			rt.printStackTrace(new PrintWriter(sw));
			LOGGER.error("updateAllFromJson-1:"+sw.toString());
		}
		
	}

	@Override
	public void changeFavStatus(long id)
	{
		BinSymbolsModel bsm=binSymbolsRepository.findById(id);
		bsm.setFavId(bsm.getFavId()==0?1:0);
		binSymbolsRepository.update(bsm);
	}
	
}

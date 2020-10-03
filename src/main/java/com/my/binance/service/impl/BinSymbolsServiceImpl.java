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
		List<BinSymbolsModel> vecBinSymbolsModel=new ArrayList<BinSymbolsModel>();
		List<BinSymbolsModel> vecBinSymbolsModel2=this.findAll();
		//
		BinSymbolsModel binSymbolsModel;
		BinSymbolsModel binSymbolsModel2;
		try
		{
			JSONObject jo=new JSONObject(IOUtils.toString(new URL(BinLinks.URL_SYMBOLS).openStream(),StandardCharsets.UTF_8));
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
					//
					binSymbolsModel2=this.findBySymbol(jo.getString("symbol"));
					vecBinSymbolsModel.add(binSymbolsModel2);
					if(binSymbolsModel2.getId()==-1)
					{
						this.create(binSymbolsModel);
						LOGGER.debug("Creating\t"+binSymbolsModel.toString());
					}
					else
					{
						binSymbolsModel.setId(binSymbolsModel2.getId());
						this.update(binSymbolsModel);
						LOGGER.debug("Updating\t"+binSymbolsModel.toString());
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
			List<BinSymbolsModel> result = vecBinSymbolsModel2.stream()
			    .filter(el -> vecBinSymbolsModel.stream().noneMatch(el::equals))
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
	
}

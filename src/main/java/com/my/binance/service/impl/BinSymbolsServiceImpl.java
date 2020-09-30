package com.my.binance.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.my.binance.model.BinSymbolsModel;
import com.my.binance.repository.BinSymbolsRepository;
import com.my.binance.service.BinSymbolsService;

@Service
@Transactional(rollbackFor=Exception.class)
public class BinSymbolsServiceImpl implements BinSymbolsService
{
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
	
}

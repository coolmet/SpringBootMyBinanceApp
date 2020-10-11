package com.my.binance.repository;

import java.util.List;
import com.my.binance.model.BinSymbolsDataModel;

public interface BinSymbolsDataRepository
{
	List<BinSymbolsDataModel> findAll();
	
	List<BinSymbolsDataModel> findBySymbol(String symbol);
	
	List<BinSymbolsDataModel> findByBinInterval(String symbol,String binInterval);
	
	List<BinSymbolsDataModel> findByTime(String symbol,String binInterval,long openTime,long closeTime);
	
	BinSymbolsDataModel findById(Long id);
	
	void create(BinSymbolsDataModel binSymbolsDataModel);
	
	BinSymbolsDataModel update(BinSymbolsDataModel binSymbolsDataModel);
	
	void delete(Long id);
	
	void deleteBySymbol(String symbol);
	
	void deleteByBinInterval(String symbol,String binInterval);
	
	void deleteByTime(String symbol,String binInterval,long openTime,long closeTime);
	
}

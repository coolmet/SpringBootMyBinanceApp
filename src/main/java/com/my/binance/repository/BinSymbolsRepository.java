package com.my.binance.repository;

import java.util.List;
import com.my.binance.model.BinSymbolsModel;

public interface BinSymbolsRepository
{
	List<BinSymbolsModel> findAll();
	
	BinSymbolsModel findById(Long id);
	
	BinSymbolsModel findByFavId(int favId);
	
	BinSymbolsModel findBySymbol(String symbol);
	
	BinSymbolsModel findByBaseAsset(String baseAsset);
	
	BinSymbolsModel findByQuoteAsset(String quoteAsset);
	
	List<BinSymbolsModel> findAllByFavId(int favId);
	
	List<BinSymbolsModel> findAllBySymbol(String symbol);
	
	List<BinSymbolsModel> findAllByBaseAsset(String baseAsset);
	
	List<BinSymbolsModel> findAllByQuoteAsset(String quoteAsset);
	
	void create(BinSymbolsModel binSymbolsModel);
	
	BinSymbolsModel update(BinSymbolsModel binSymbolsModel);
	
	void delete(Long id);
	
	void deleteByBaseAsset(String baseAsset);
	
	void deleteByQuoteAsset(String quoteAsset);
	
}

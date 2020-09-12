package com.my.binance.repository;

import java.util.List;
import com.my.binance.model.CurrencyTest;

public interface CurrencyTestRepository
{
	List<CurrencyTest> findAll();
	
	CurrencyTest findRandomNative();
	
	CurrencyTest findRandomFunction();
	
	List<CurrencyTest> findRandomFunctionAll();
	
	CurrencyTest findById(Long id);
	
	CurrencyTest findByCurrency(String currency);
	
	List<CurrencyTest> findAllByCurrency(String currency);
	
	List<CurrencyTest> findAllByContent(String content);
	
	void create(CurrencyTest currencyTest);
	
	CurrencyTest update(CurrencyTest currencyTest);
	
	void delete(Long id);
	
	void deleteByCurrency(String currency);
	
	void deleteByContent(String content);
	
}

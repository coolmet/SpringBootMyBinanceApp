package com.my.binance.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.my.binance.repository.CurrencyTestRepository;
import com.my.binance.model.CurrencyTest;
import com.my.binance.service.CurrencyTestService;

@Service
@Transactional(rollbackFor=Exception.class)
public class CurrencyTestServiceImpl implements CurrencyTestService
{
	private CurrencyTestRepository currencyTestRepository;
	
	public CurrencyTestRepository getCurrencyTestRepository()
	{
		return currencyTestRepository;
	}
	
	@Autowired
	public void setCurrencyTestRepository(CurrencyTestRepository currencyTestRepository)
	{
		this.currencyTestRepository=currencyTestRepository;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<CurrencyTest> findAll()
	{
		return currencyTestRepository.findAll();
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public CurrencyTest findRandomNative()
	{
		return currencyTestRepository.findRandomNative();
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public CurrencyTest findRandomFunction()
	{
		return currencyTestRepository.findRandomFunction();
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<CurrencyTest> findRandomFunctionAll()
	{
		return currencyTestRepository.findRandomFunctionAll();
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public CurrencyTest findById(Long id)
	{
		return currencyTestRepository.findById(id);
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public CurrencyTest findByCurrency(String currency)
	{
		return currencyTestRepository.findByCurrency(currency);
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<CurrencyTest> findAllByCurrency(String currency)
	{
		return currencyTestRepository.findAllByCurrency(currency);
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<CurrencyTest> findAllByContent(String content)
	{
		return currencyTestRepository.findAllByContent(content);
	}
	
	@Override
	public void create(CurrencyTest currencyTest)
	{
		currencyTestRepository.create(currencyTest);
	}
	
	@Override
	public CurrencyTest update(CurrencyTest currencyTest)
	{
		return currencyTestRepository.update(currencyTest);
	}
	
	@Override
	@Secured(value=
	{"ROLE_ADMIN"})
	public void delete(Long id)
	{
		currencyTestRepository.delete(id);
	}
	
	@Override
	@Secured(value=
	{"ROLE_ADMIN"})
	public void deleteByCurrency(String currency)
	{
		currencyTestRepository.deleteByCurrency(currency);
	}
	
	@Override
	@Secured(value=
	{"ROLE_ADMIN"})
	public void deleteByContent(String content)
	{
		currencyTestRepository.deleteByContent(content);
	}
	
}

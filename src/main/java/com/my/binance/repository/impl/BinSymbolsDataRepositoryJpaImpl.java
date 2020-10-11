package com.my.binance.repository.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.my.binance.model.BinSymbolsDataModel;
import com.my.binance.model.BinSymbolsModel;
import com.my.binance.repository.BinSymbolsDataRepository;

@Repository("binSymbolsDataRepository")
public class BinSymbolsDataRepositoryJpaImpl implements BinSymbolsDataRepository
{
	@PersistenceContext
	private EntityManager entityManager;
	
	private BinSymbolsDataModel emptyBinSymbolsData()
	{
		BinSymbolsDataModel binSymbolsDataModel=new BinSymbolsDataModel();
		binSymbolsDataModel.setId(-1);
		binSymbolsDataModel.setBininterval("");
		binSymbolsDataModel.setCloseTime(0);
		binSymbolsDataModel.setHighValue(0);
		binSymbolsDataModel.setIgnore(0);
		binSymbolsDataModel.setLowValue(0);
		binSymbolsDataModel.setNumberOfTrades(0);
		binSymbolsDataModel.setOpenTime(0);
		binSymbolsDataModel.setOpenValue(0);
		binSymbolsDataModel.setQuoteAssetVolume(0);
		binSymbolsDataModel.setSymbol("");
		binSymbolsDataModel.setTakerBuyBaseAssetVolume(0);
		binSymbolsDataModel.setTakerBuyQuoteAssetVolume(0);
		return binSymbolsDataModel;
	}
	
	@Override
	public List<BinSymbolsDataModel> findAll()
	{
		return entityManager.createQuery("from BinSymbolsDataEntity order by OPEN_TIME desc",BinSymbolsDataModel.class).getResultList();
		
	}
	
	@Override
	public List<BinSymbolsDataModel> findBySymbol(String symbol)
	{
		return entityManager.createQuery("from BinSymbolsDataEntity where symbol = :psymbol",BinSymbolsDataModel.class)
		                    .setParameter("psymbol",symbol)
		                    .getResultList();
	}
	
	@Override
	public List<BinSymbolsDataModel> findByBinInterval(String symbol,String binInterval)
	{
		return entityManager.createQuery("from BinSymbolsDataEntity where symbol = :psymbol and binInterval = :pbinInterval",BinSymbolsDataModel.class)
		                    .setParameter("psymbol",symbol)
		                    .setParameter("pbinInterval",binInterval)
		                    .getResultList();
	}
	
	@Override
	public List<BinSymbolsDataModel> findByTime(String symbol,String binInterval,long openTime,long closeTime)
	{
		return entityManager.createQuery("from BinSymbolsDataEntity where symbol = :psymbol and binInterval = :pbinInterval and opentime between :popenTime and :pcloseTime",BinSymbolsDataModel.class)
		                    .setParameter("psymbol",symbol)
		                    .setParameter("pbinInterval",binInterval)
		                    .setParameter("popenTime",openTime)
		                    .setParameter("pcloseTime",closeTime)
		                    .getResultList();
	}
	
	@Override
	public BinSymbolsDataModel findById(Long id)
	{
		try
		{
			return entityManager.find(BinSymbolsDataModel.class,id);
		}
		catch(Exception rt)
		{
			return emptyBinSymbolsData();
		}
	}
	
	@Override
	public void create(BinSymbolsDataModel binSymbolsDataModel)
	{
		entityManager.persist(binSymbolsDataModel);
		
	}
	
	@Override
	public BinSymbolsDataModel update(BinSymbolsDataModel binSymbolsDataModel)
	{
		return entityManager.merge(binSymbolsDataModel);
		
	}
	
	@Override
	public void delete(Long id)
	{
		entityManager.remove(entityManager.getReference(BinSymbolsDataModel.class,id));
	}
	
	@Override
	public void deleteBySymbol(String symbol)
	{
		entityManager.createQuery("delete from BinSymbolsDataEntity where where symbol = :psymbol")
		             .setParameter("psymbol",symbol)
		             .executeUpdate();
		
	}
	
	@Override
	public void deleteByBinInterval(String symbol,String binInterval)
	{
		entityManager.createQuery("delete from BinSymbolsDataEntity where symbol = :psymbol and binInterval = :pbinInterval")
		             .setParameter("psymbol",symbol)
		             .setParameter("pbinInterval",binInterval)
		             .executeUpdate();
		
	}
	
	@Override
	public void deleteByTime(String symbol,String binInterval,long openTime,long closeTime)
	{
		entityManager.createQuery("delete from BinSymbolsDataEntity where symbol = :psymbol and binInterval = :pbinInterval and opentime between :popenTime and :pcloseTime")
		             .setParameter("psymbol",symbol)
		             .setParameter("pbinInterval",binInterval)
		             .setParameter("popenTime",openTime)
		             .setParameter("pcloseTime",closeTime)
		             .executeUpdate();
		
	}
	
}

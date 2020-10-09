package com.my.binance.repository.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.my.binance.repository.BinSymbolsRepository;
import com.my.binance.repository.CurrencyTestRepository;
import com.my.binance.model.BinSymbolsModel;
import com.my.binance.model.CurrencyTest;

@Repository("binSymbolsRepository")
public class BinSymbolsRepositoryJpaImpl implements BinSymbolsRepository
{
	@PersistenceContext
	private EntityManager entityManager;
	
	private BinSymbolsModel emptyBinSymbols()
	{
		BinSymbolsModel binSymbolsModel=new BinSymbolsModel();
		binSymbolsModel.setId(-1);
		binSymbolsModel.setFavId(0);
		binSymbolsModel.setSymbol("");
		binSymbolsModel.setBaseAsset("");
		binSymbolsModel.setQuoteAsset("");
		return binSymbolsModel;
	}
	
	@Override
	public List<BinSymbolsModel> findAll()
	{
		return entityManager.createQuery("from BinSymbolsEntity order by FAV_ID desc",BinSymbolsModel.class).getResultList();
	}
	
	@Override
	public BinSymbolsModel findById(Long id)
	{
		try
		{
			return entityManager.find(BinSymbolsModel.class,id);
		}
		catch(Exception rt)
		{
			return emptyBinSymbols();
		}
	}
	
	@Override
	public BinSymbolsModel findByFavId(int favId)
	{
		try
		{
			return entityManager.find(BinSymbolsModel.class,favId);
		}
		catch(Exception rt)
		{
			return emptyBinSymbols();
		}
	}
	
	@Override
	public BinSymbolsModel findBySymbol(String symbol)
	{
		try
		{
			return entityManager.createQuery("from BinSymbolsEntity where symbol = :psymbol",BinSymbolsModel.class).setParameter("psymbol",symbol).setMaxResults(1).getSingleResult();
		}
		catch(Exception e)
		{
			return emptyBinSymbols();
		}
	}
	
	@Override
	public BinSymbolsModel findByBaseAsset(String baseAsset)
	{
		try
		{
			return entityManager.createQuery("from BinSymbolsEntity where baseAsset = :pbaseAsset",BinSymbolsModel.class).setParameter("pbaseAsset",baseAsset).setMaxResults(1).getSingleResult();
		}
		catch(Exception e)
		{
			return emptyBinSymbols();
		}
	}
	
	@Override
	public BinSymbolsModel findByQuoteAsset(String quoteAsset)
	{
		try
		{
			return entityManager.createQuery("from BinSymbolsEntity where quoteAsset = :pquoteAsset",BinSymbolsModel.class).setParameter("quoteAsset",quoteAsset).setMaxResults(1).getSingleResult();
		}
		catch(Exception e)
		{
			return emptyBinSymbols();
		}
	}
	
	@Override
	public List<BinSymbolsModel> findAllByFavId(int favId)
	{
		return entityManager.createQuery("from BinSymbolsEntity where favid = :pfavid",BinSymbolsModel.class).setParameter("pfavid",favId).getResultList();
		
	}
	
	@Override
	public List<BinSymbolsModel> findAllBySymbol(String symbol)
	{
		return entityManager.createQuery("from BinSymbolsEntity where symbol = :psymbol",BinSymbolsModel.class).setParameter("psymbol",symbol).getResultList();
		
	}
	
	@Override
	public List<BinSymbolsModel> findAllByBaseAsset(String baseAsset)
	{
		return entityManager.createQuery("from BinSymbolsEntity where baseAsset = :pbaseAsset",BinSymbolsModel.class).setParameter("pbaseAsset",baseAsset).getResultList();
		
	}
	
	@Override
	public List<BinSymbolsModel> findAllByQuoteAsset(String quoteAsset)
	{
		return entityManager.createQuery("from BinSymbolsEntity where quoteAsset = :pquoteAsset",BinSymbolsModel.class).setParameter("quoteAsset",quoteAsset).getResultList();
		
	}
	
	@Override
	public void create(BinSymbolsModel binSymbolsModel)
	{
		entityManager.persist(binSymbolsModel);
		
	}
	
	@Override
	public BinSymbolsModel update(BinSymbolsModel binSymbolsModel)
	{
		return entityManager.merge(binSymbolsModel);
	}
	
	@Override
	public void delete(Long id)
	{
		entityManager.remove(entityManager.getReference(BinSymbolsModel.class,id));
		
	}
	
	@Override
	public void deleteByBaseAsset(String baseAsset)
	{
		entityManager.createQuery("delete from BinSymbolsEntity where baseAsset = :pbaseAsset").setParameter("pbaseAsset",baseAsset).executeUpdate();
		
	}
	
	@Override
	public void deleteByQuoteAsset(String quoteAsset)
	{
		entityManager.createQuery("delete from BinSymbolsEntity where quoteAsset = :pquoteAsset").setParameter("pquoteAsset",quoteAsset).executeUpdate();
		
	}
	
}

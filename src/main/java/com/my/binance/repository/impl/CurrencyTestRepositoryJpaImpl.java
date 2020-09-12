package com.my.binance.repository.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.my.binance.repository.CurrencyTestRepository;
import com.my.binance.model.CurrencyTest;

@Repository("currencyTestRepository")
public class CurrencyTestRepositoryJpaImpl implements CurrencyTestRepository
{
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<CurrencyTest> findAll()
	{
		return entityManager.createQuery("from CurrencyTest",CurrencyTest.class).getResultList();
	}
	
	@Override
	public CurrencyTest findRandomNative()
	{
		return entityManager.createQuery("from CurrencyTest ORDER BY RAND()",CurrencyTest.class).setMaxResults(1).getSingleResult();
		
	}
	
	@Override
	public CurrencyTest findRandomFunction()
	{
		return entityManager.createQuery("from CurrencyTest order by function('RAND')",CurrencyTest.class).setMaxResults(1).getSingleResult();
	}
	
	@Override
	public List<CurrencyTest> findRandomFunctionAll()
	{
		return entityManager.createQuery("from CurrencyTest order by function('RAND')",CurrencyTest.class).getResultList();
	}
	
	@Override
	public CurrencyTest findById(Long id)
	{
		try
		{
			return entityManager.find(CurrencyTest.class,id);
		}
		catch(Exception rt)
		{
			return emptyCurrencyTest();
		}
	}
	
	@Override
	public List<CurrencyTest> findAllByCurrency(String currency)
	{
		return entityManager.createQuery("from CurrencyTest where currency = :pCurrency",CurrencyTest.class).setParameter("pCurrency",currency).getResultList();
	}
	
	@Override
	public CurrencyTest findByCurrency(String currency)
	{
		return entityManager.createQuery("from CurrencyTest where currency = :pCurrency",CurrencyTest.class).setParameter("pCurrency",currency).setMaxResults(1).getSingleResult();
	}
	
	@Override
	public List<CurrencyTest> findAllByContent(String content)
	{
		return entityManager.createQuery("from CurrencyTest where content = :pContent",CurrencyTest.class).setParameter("pContent",content).getResultList();
		
	}
	
	@Override
	public void create(CurrencyTest images)
	{
		entityManager.persist(images);
	}
	
	@Override
	public CurrencyTest update(CurrencyTest images)
	{
		return entityManager.merge(images);
	}
	
	@Override
	public void delete(Long id)
	{
		entityManager.remove(entityManager.getReference(CurrencyTest.class,id));
	}
	
	@Override
	public void deleteByCurrency(String currency)
	{
		entityManager.createQuery("delete from CurrencyTest where currency = :pCurrency").setParameter("pCurrency",currency).executeUpdate();
	}
	
	@Override
	public void deleteByContent(String content)
	{
		entityManager.createQuery("delete from CurrencyTest where content = :pContent").setParameter("pContent",content).executeUpdate();
	}
	
	private CurrencyTest emptyCurrencyTest()
	{
		CurrencyTest currencyTest=new CurrencyTest();
		currencyTest.setCurrency("");
		currencyTest.setContent("");
		return currencyTest;
	}
}

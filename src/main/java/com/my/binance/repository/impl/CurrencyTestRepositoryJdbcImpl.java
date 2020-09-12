package com.my.binance.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.my.binance.repository.CurrencyTestRepository;
import com.my.binance.model.CurrencyTest;


public class CurrencyTestRepositoryJdbcImpl implements CurrencyTestRepository
{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<CurrencyTest> rowMapper=new RowMapper<CurrencyTest>()
	{
		@Override
		public CurrencyTest mapRow(ResultSet rs,int rowNum) throws SQLException
		{
			CurrencyTest currencyTest=new CurrencyTest();
			currencyTest.setId(rs.getLong("id"));
			currencyTest.setCurrency(rs.getString("currency"));
			currencyTest.setContent(rs.getString("content"));
			return currencyTest;
		}
	};
	
	@Override
	public List<CurrencyTest> findAll()
	{
		return jdbcTemplate.query("select * from DB_CURRENCY_TEST ",rowMapper);
	}
	
	@Override
	public CurrencyTest findById(Long id)
	{
		return DataAccessUtils.singleResult(jdbcTemplate.query("select * from DB_CURRENCY_TEST where id = ?",rowMapper,id));
	}
	
	@Override
	public List<CurrencyTest> findAllByCurrency(String currency)
	{
		return jdbcTemplate.query("select * from DB_CURRENCY_TEST where currency like ?",rowMapper,"%"+currency+"%");
	}
	
	@Override
	public CurrencyTest findByCurrency(String currency)
	{
		return DataAccessUtils.singleResult(jdbcTemplate.query("select * from DB_CURRENCY_TEST where currency like ?",rowMapper,"%"+currency+"%"));
	}
	
	@Override
	public List<CurrencyTest> findAllByContent(String content)
	{
		return jdbcTemplate.query("select * from DB_CURRENCY_TEST where content like ?",rowMapper,"%"+content+"%");
	}
	
	@Override
	public void create(CurrencyTest currencyTest)
	{
		KeyHolder keyHolder=new GeneratedKeyHolder();
		PreparedStatementCreator psc=new PreparedStatementCreator()
		{
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException
			{
				PreparedStatement stmt=con.prepareStatement(
				                                            "insert into DB_CURRENCY_TEST(id,currency,content) "
				                                            +" values(default,?,?)");
				stmt.setString(2,currencyTest.getCurrency());
				stmt.setString(3,currencyTest.getContent());
				return stmt;
			}
		};
		int count=jdbcTemplate.update(psc,keyHolder);
		if(count!=1)
		{
			throw new RuntimeException("Unable to create currencyTest :"+currencyTest);
		}
		currencyTest.setId((Long)keyHolder.getKey());
	}
	
	@Override
	public CurrencyTest update(CurrencyTest currencyTest)
	{
		int count=jdbcTemplate.update("update DB_CURRENCY_TEST "
		+"set currency = ?, content = ? "
		+"where id = ?",currencyTest.getCurrency(),currencyTest.getContent());
		if(count!=1)
		{
			throw new RuntimeException("Unable to update currencyTest :"+currencyTest);
		}
		return currencyTest;
	}
	
	@Override
	public void delete(Long id)
	{
		jdbcTemplate.update("delete from DB_CURRENCY_TEST where id = ?",id);
	}
	
	@Override
	public void deleteByCurrency(String currency)
	{
		jdbcTemplate.update("delete from DB_CURRENCY_TEST where currency like ?","%"+currency+"%");
	}
	
	@Override
	public void deleteByContent(String content)
	{
		jdbcTemplate.update("delete from DB_CURRENCY_TEST where content like ?","%"+content+"%");
	}
	
	@Override
	public CurrencyTest findRandomNative()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CurrencyTest findRandomFunction()
	{
		return DataAccessUtils.singleResult(jdbcTemplate.query("select * from DB_CURRENCY_TEST order by function('RAND')",rowMapper));
	}
	
	@Override
	public List<CurrencyTest> findRandomFunctionAll()
	{
		return jdbcTemplate.query("select * from DB_CURRENCY_TEST order by function('RAND')",rowMapper);
		
	}
	
}

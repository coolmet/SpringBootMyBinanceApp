package com.my.binance.model.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import com.my.binance.model.BinSymbolsModel;

public interface BinSymbolsModelImpl extends JpaRepository<BinSymbolsModel,Long>
{
	BinSymbolsModel findBySymbol(String symbol);
}

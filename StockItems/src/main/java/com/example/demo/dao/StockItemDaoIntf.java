package com.example.demo.dao;

import java.util.List;

import com.example.demo.pojo.StockItems;

public interface StockItemDaoIntf {
	public List<StockItems> getStockItems(long id);
}

package com.example.demo.service;

import java.util.List;

import com.example.demo.pojo.StockItems;

public interface StockItemServiceIntf {
	public List<StockItems> getStockItems(long id);
}

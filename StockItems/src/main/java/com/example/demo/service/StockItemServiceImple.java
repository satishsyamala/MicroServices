package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.StockItemDaoIntf;
import com.example.demo.pojo.StockItems;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
public class StockItemServiceImple implements StockItemServiceIntf {

	@Autowired
	private StockItemDaoIntf stockItemDaoIntf;

	@Override
	public List<StockItems> getStockItems(long id) {
		// TODO Auto-generated method stub
		return stockItemDaoIntf.getStockItems(id);
	}

}

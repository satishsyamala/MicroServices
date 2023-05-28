package com.example.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.pojo.StockItems;

@Repository
public class StockItemDaoImpl implements StockItemDaoIntf {
	
	@Autowired
	private EntityManager entityManager;
	
	public List<StockItems> getStockItems(long id)
	{
		List<StockItems> result=null;
		try {
			String sql="From StockItems a";
			if(id>0)
				sql+=" where a.stockItemId="+id;
			result=entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

}

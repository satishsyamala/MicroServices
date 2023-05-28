package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;


//@FeignClient(name="stock-items",url="http://192.168.29.238:8766")
@FeignClient("stock-items")
public interface StockItems {

	@GetMapping(value = "/stocks/items/{stockId}")
	public ResponseEntity<String> getStockItems(@PathVariable long stockId);
	
	@GetMapping(value = "/stocks/test")
	public ResponseEntity<String> ribbonTest();
	
	
}

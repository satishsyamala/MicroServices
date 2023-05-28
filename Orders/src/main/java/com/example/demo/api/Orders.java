package com.example.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.Configurations;
import com.example.demo.feign.StockItems;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
@RequestMapping("/orders")
public class Orders {

	@Autowired
	private Configurations configurations;

	@Autowired
	private StockItems stockItems;

	@Value("${busvalue}")
	private String busvalue;
	
	@Value("${server.port}")
	private String serverPort;

	@GetMapping(value = "/orders/{stockId}")
	public ResponseEntity<String> getStockItems(@PathVariable long stockId) {
		return ResponseEntity.ok().body(stockItems.getStockItems(stockId).getBody());
	}

	@GetMapping(value = "/test")
	@HystrixCommand(fallbackMethod = "defaultToCachedData",commandProperties = {
		      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
	   })
	public ResponseEntity<String> getTest() {
		try {
			//Thread.sleep(3000);
			return ResponseEntity.ok().body(
					stockItems.ribbonTest().getBody() +"  Loc " + configurations.getBusvalue() + " class " + busvalue);
		} catch (Exception e) {
			System.out.println("Server Call");
			return defaultToCachedData();
		}
		
	}

	public ResponseEntity<String> defaultToCachedData() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Server calling failed not responding");
	}

	@GetMapping(value = "/all-orderst")
	public ResponseEntity<String> getAllOrders() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok().body(serverPort+"  All Orders");
	}

}

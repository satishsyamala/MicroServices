package com.example.demo.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.Configurations;
import com.example.demo.pojo.StockItems;
import com.example.demo.service.StockItemServiceIntf;

@RestController
@RequestMapping("/stocks")
public class StockItemsApi {

	@Value("${server.port}")
	private String port;

	@Value("${busvalue}")
	private String busvalue;

	@Autowired
	private Configurations configuration;

	@Autowired
	private StockItemServiceIntf stockItemServiceIntf;

	@GetMapping(value = "/items/{stockId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StockItems>> getStockItems(@PathVariable long stockId) {
		try {

			System.out.print(port + " : " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
			return ResponseEntity.ok().body(stockItemServiceIntf.getStockItems(stockId));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@GetMapping(value = "/test")
	public ResponseEntity<String> ribbonTest() {
		try {
			//Thread.sleep(4000);
			return ResponseEntity.ok().body(port + " : " + configuration.getBusvalue() + " " + busvalue);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}

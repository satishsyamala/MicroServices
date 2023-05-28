package com.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {

	@Autowired
	private SpringCloudFilter cloudFilter;

	@Bean
	public RouteLocator getRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("items",
						r -> r.path("/stocks/**").filters(f -> f.filter(cloudFilter)).uri("lb://stock-items/"))
				.route("orders",
						r -> r.path(""
								+ "/**").filters(f -> f.filter(cloudFilter)).uri("lb://orders/"))
				.build();

	}
	


}

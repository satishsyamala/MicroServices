package com.gateway.config;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
public class SpringCloudFilter implements GatewayFilter {
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		List<String> excludeApis = List.of("/auth/login","/orders/test");
		Predicate<ServerHttpRequest> p1 = (r) -> excludeApis.stream()
				.noneMatch(uri -> r.getURI().getPath().contains(uri));
		if (false && p1.test(request)) {
			System.out.println("Token ");
			if (!request.getHeaders().containsKey("Authorization")) {
				ServerHttpResponse response = (ServerHttpResponse) exchange.getResponse();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
				return response.setComplete();
			}
			final String token = request.getHeaders().getOrEmpty("Authorization").get(0);
			try {
				System.out.println("token "+token);
				jwtUtil.validateToken(token);
			} catch (Exception e) {
				e.printStackTrace();
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.BAD_REQUEST);
				return response.setComplete();
			}

			Claims claims = jwtUtil.getClaims(token);
			exchange.getRequest().mutate().header("id", String.valueOf(claims.get("id"))).build();
		}

		return chain.filter(exchange);
	}

}

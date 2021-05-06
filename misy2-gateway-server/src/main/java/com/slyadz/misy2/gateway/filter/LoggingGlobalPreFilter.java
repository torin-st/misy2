package com.slyadz.misy2.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class LoggingGlobalPreFilter implements GlobalFilter {
	private static final Logger logger = LoggerFactory.getLogger(LoggingGlobalPreFilter.class);
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("Global Pre Filter executed");
		return chain.filter(exchange);
	}

}

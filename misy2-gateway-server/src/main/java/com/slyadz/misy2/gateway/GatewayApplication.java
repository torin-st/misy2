package com.slyadz.misy2.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {
	private final String USERS_SERVICE_NAME = "users-service";
	private final DiscoveryClient discoveryClient;

	public GatewayApplication(DiscoveryClient discoveryClient) {
		this.discoveryClient = discoveryClient;
	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/get")
						.filters(f -> f.addRequestHeader("Hello", "World"))
						.uri("http://httpbin.org:80"))
				.route(p -> p
						.path("/users")
						.filters(f -> f.prefixPath("/api"))
						.uri("lb://" + USERS_SERVICE_NAME))
				.build();
	}
}

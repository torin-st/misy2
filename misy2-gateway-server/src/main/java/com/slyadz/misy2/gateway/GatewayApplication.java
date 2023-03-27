package com.slyadz.misy2.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

@SpringBootApplication
public class GatewayApplication {
	private final String USERS_SERVICE_NAME = "users-service";
	private final String ORDERS_SERVICE_NAME = "orders-service";

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(r -> r
					.path("/get")
					.filters(f -> f.addRequestHeader("Hello", "World") )
					.uri("http://httpbin.org:80")
				)
				.route(r -> r
					.path("/users") // map to /users
					.filters(f -> f
						.prefixPath("/api") // add /api to path = /api/users
						.circuitBreaker(c -> c.setName("resilience4j").setFallbackUri("/get")) // if service is not available - redirect to /get
					)
					.uri("lb://" + USERS_SERVICE_NAME) // redirect ot uri, lb: - to using load balancing
				)
				.route(r -> r
					.path("/headers") // map to /headers
					.filters(f -> f.prefixPath("/api") ) // add /api to path = /api/headers
					.uri("lb://" + USERS_SERVICE_NAME)  // redirect ot uri, lb: - to using load balancing
				)
				.route(r -> r
					.path("/orders") // map to /orders
					.and()
					.method(HttpMethod.POST)
					.filters(f -> f.prefixPath("/api")) // add /api to path = /api/orders
					.uri("lb://" + ORDERS_SERVICE_NAME) // redirect ot uri, lb: - to using load balancing
				)
				.build();
	}
}

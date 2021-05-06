package com.slyadz.misy2.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {
	private final String USERS_SERVICE_NAME = "users-service";

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
						.path("/users")			//map to /users
						.filters(f -> f.prefixPath("/api"))	//add /api to path = /api/users
						.uri("lb://" + USERS_SERVICE_NAME))	//redirect ot uri, lb: - to using load balancing
				.route(p -> p
						.path("/headers")			//map to /headers
						.filters(f -> f.prefixPath("/api"))	//add /api to path = /api/headers
						.uri("lb://" + USERS_SERVICE_NAME))	//redirect ot uri, lb: - to using load balancing
				.build();
	}
}

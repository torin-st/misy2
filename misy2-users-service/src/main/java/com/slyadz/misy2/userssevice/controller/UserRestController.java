package com.slyadz.misy2.userssevice.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.annotation.JsonView;
import com.slyadz.misy2.userssevice.kafka.KafkaSenderService;
import com.slyadz.misy2.userssevice.model.User;
import com.slyadz.misy2.userssevice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
public final class UserRestController {
	private static Logger logger = LoggerFactory.getLogger(UserRestController.class);
	private UserRepository userRepository;
	private DiscoveryClient discoveryClient;
	private WebClient webClient;
	private KafkaSenderService kafkaSenderService;
	@Value("${kafka.topics.users.name}")
	private String usersTopic;

	public UserRestController(
			UserRepository userRepository,
			DiscoveryClient discoveryClient,
			WebClient webClient,
			KafkaSenderService kafkaSenderService
	) {
		this.userRepository = userRepository;
		this.discoveryClient = discoveryClient;
		this.webClient = webClient;
		this.kafkaSenderService = kafkaSenderService;
	}

	@GetMapping("users")
	@JsonView(User.Views.idName.class)
	public Iterable<User> getAll()	{
		return userRepository.findAll();
	}

	@PostMapping("users")
	public ResponseEntity<?> save(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) {
		User savedUser = userRepository.save(user);
		kafkaSenderService.sendMessage("User is created: " + savedUser, usersTopic);
		return ResponseEntity.created
				(uriComponentsBuilder
						.path("/api/users/{userId}")
						.buildAndExpand(savedUser.getId())
						.toUri()).build();
	}

	@GetMapping("headers")
	public String getSleuthInfo(HttpServletRequest httpServletRequest)	{
		logger.info("header [x-b3-traceid]: " + httpServletRequest.getHeader("x-b3-traceid"));
		logger.info("header [x-b3-spanid]: " + httpServletRequest.getHeader("x-b3-spanid"));
		logger.info("header [x-b3-parentspanid]: " + httpServletRequest.getHeader("x-b3-parentspanid"));
		logger.info("header [x-b3-sampled]: " + httpServletRequest.getHeader("x-b3-sampled"));


		WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = webClient.method(HttpMethod.GET);
		WebClient.RequestBodySpec requestBodySpec = uriSpec.uri("/headers");
		Mono<String> result = requestBodySpec.exchangeToMono(response -> {
			if (response.statusCode().equals(HttpStatus.OK)) {
				return response.bodyToMono(String.class);
			} else if (response.statusCode().is4xxClientError()) {
				return Mono.just("Error response");
			} else {
				return response.createException().flatMap(Mono::error);
			}
		});
		return result.block();
	}

	@GetMapping("instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(
			@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}

}

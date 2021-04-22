package com.slyadz.misy2.userssevice.controller;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.slyadz.misy2.userssevice.model.User;
import com.slyadz.misy2.userssevice.repository.UserRepository;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public final class UserRestController {
	private UserRepository userRepository;
	private DiscoveryClient discoveryClient;

	public UserRestController(UserRepository userRepository, DiscoveryClient discoveryClient) {
		this.userRepository = userRepository;
		this.discoveryClient = discoveryClient;
	}

	@GetMapping("users")
	@JsonView(User.Views.idName.class)
	public Iterable<User> getAll() {
		return userRepository.findAll();
	}

	@GetMapping("instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(
			@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}

}

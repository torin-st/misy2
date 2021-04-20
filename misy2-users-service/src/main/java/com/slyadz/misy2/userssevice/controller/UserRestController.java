package com.slyadz.misy2.userssevice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.slyadz.misy2.userssevice.model.User;
import com.slyadz.misy2.userssevice.repository.UserRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserRestController {
	UserRepository userRepository;

	public UserRestController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("users")
	@JsonView(User.Views.idName.class)
	public Iterable<User> getAll() {
		return userRepository.findAll();
	}
}

package com.slyadz.misy2.userssevice;

import com.slyadz.misy2.userssevice.model.User;
import com.slyadz.misy2.userssevice.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class UsersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersServiceApplication.class, args);
	}

	@Bean
	public WebClient getWebClient() {
		return WebClient.builder().baseUrl("http://httpbin.org:80").build();
	}

	@Bean
	public CommandLineRunner demoData(UserRepository userRepository) {
		return args -> {
			userRepository.save(new User("Jake"));
			userRepository.save(new User("Jane"));
			userRepository.save(new User("John"));
			userRepository.save(new User("Jennifer"));
		};
	}

}


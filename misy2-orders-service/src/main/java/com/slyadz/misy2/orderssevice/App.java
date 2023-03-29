package com.slyadz.misy2.orderssevice;

import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.slyadz.misy2.usersservice.api.event.UserVerificationFailedEventData;
import com.slyadz.misy2.usersservice.api.event.UserVerificationSuccessEventData;

@SpringBootApplication
public class App {
	private final static Logger logger = LoggerFactory.getLogger(App.class);
	private final OrderService orderService;

	public App(OrderService orderService) {
		this.orderService = orderService;
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	Function<String, String> orderCreationChannel() {
		return message -> {
			logger.info("catch message: " + message);
			return "handle message: " + message;
		};
	}

	@Bean
	Consumer<UserVerificationFailedEventData> userVerificationFailed() {
		return message -> {
			logger.info("received UserVerificationFailedEventData message: {orderId: " + message.getOrderId() + "}");
			orderService.rejectOrder(new OrderId(message.getOrderId()));
	   };
	}

	@Bean
	Consumer<UserVerificationSuccessEventData> userVerificationSuccess() {
		return message -> {
			logger.info("received UserVerificationSuccessEventData message: {orderId: " + message.getOrderId() + "}");
			orderService.approveOrder(new OrderId(message.getOrderId()));
	   };
	}

}
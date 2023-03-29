package com.slyadz.misy2.userssevice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.slyadz.misy2.orderssevice.api.OrderTopics;
import com.slyadz.misy2.orderssevice.api.event.OrderCreatedEventData;
import com.slyadz.misy2.userssevice.service.UserService;

@Service
class KafkaListenerService {
	private static Logger logger = LoggerFactory.getLogger(KafkaListenerService.class);
	private final UserService userService;

	public KafkaListenerService(UserService userService) {
		this.userService = userService;
	}	

	@KafkaListener(topics = OrderTopics.CREATED)
	void ordersListener(OrderCreatedEventData orderCreatedEventData) {
		userService.validateOrderByUserId(orderCreatedEventData.getUserId(), orderCreatedEventData.getOrderId());		
	}

}

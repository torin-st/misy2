package com.slyadz.misy2.userssevice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.slyadz.misy2.orderssevice.api.OrderTopics;
import com.slyadz.misy2.orderssevice.api.event.OrderCreatedEventData;
import com.slyadz.misy2.userssevice.service.UserService;

@Service
class KafkaListenerService {
	private final UserService userService;

	public KafkaListenerService(UserService userService) {
		this.userService = userService;
	}	

	@KafkaListener(topics = OrderTopics.CREATED)
	void ordersListener(OrderCreatedEventData orderCreatedEventData) {
		userService.validateOrderByUserId(orderCreatedEventData.getUserId(), orderCreatedEventData.getOrderId());		
	}

}

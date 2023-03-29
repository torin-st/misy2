package com.slyadz.misy2.orderssevice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slyadz.misy2.orderssevice.api.dto.CreateOrderRequest;

@RestController
@RequestMapping(path = "/api/orders")
public class OrderRestController {
	private final static Logger logger = LoggerFactory.getLogger(OrderRestController.class);
	private OrderService orderService;

	public OrderRestController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	public OrderId create(@RequestBody CreateOrderRequest request) {
		var userID = request.getUserID();
		logger.info("userID: " + userID);
		var deliveryTime = request.getDeliveryTime();
		logger.info("deliveryTime: " + deliveryTime);
		var orderId = orderService.createOrder(request.getUserID(), request.getDeliveryTime());
		return orderId;
	}
}

package com.slyadz.misy2.orderssevice.application.internal.services;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.slyadz.misy2.orderssevice.domain.model.Order;
import com.slyadz.misy2.orderssevice.domain.model.OrderId;
import com.slyadz.misy2.orderssevice.domain.model.OrderState;
import com.slyadz.misy2.orderssevice.infrastructure.repositories.springdata.OrderRepository;

@Service
public class OrderService {
	private final static Logger logger = LoggerFactory.getLogger(OrderService.class);
	private final OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public OrderId createOrder(Long userId, LocalDateTime deliveryTime) {
		var random = UUID.randomUUID().toString().toUpperCase();
        var order = new Order(new OrderId(random.substring(0, random.indexOf("-"))), userId, deliveryTime);
		order = orderRepository.save(order);
		return order.getOrderId();
	}

	public void approveOrder(OrderId orderId) {
		var order = orderRepository.findByOrderId(orderId).orElseThrow(() -> new NoSuchElementException("There is no Order with orderId=" + orderId));
		order.setState(OrderState.APPROVED);
		orderRepository.save(order);
		logger.info("order is approved: " + orderId.getOrderId());
	}
	
	public void rejectOrder(OrderId orderId) {
		var order = orderRepository.findByOrderId(orderId).orElseThrow(() -> new NoSuchElementException("There is no Order with orderId=" + orderId));
		order.setState(OrderState.REJECTED);
		orderRepository.save(order);
		logger.info("order is rejected: " + orderId.getOrderId());
	}	

}

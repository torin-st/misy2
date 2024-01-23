package com.slyadz.misy2.orderssevice.domain.model;

import java.time.LocalDateTime;

import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.transaction.event.TransactionalEventListener;

import com.slyadz.misy2.orderssevice.api.event.OrderCreatedEvent;
import com.slyadz.misy2.orderssevice.api.event.OrderCreatedEventData;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order extends AbstractAggregateRoot<Order> {

	@Id
	@GeneratedValue
	private Long id;

	@Embedded
	private OrderId orderId;

	@Enumerated(EnumType.STRING)
	private OrderState state;

	private Long userId;

	private LocalDateTime deliveryTime;

	public Order() {
	}

	public Order(OrderId orderId, Long userId, LocalDateTime deliveryTime) {
		this.orderId = orderId;
		this.userId = userId;
		this.deliveryTime = deliveryTime;
		this.state = OrderState.APPROVAL_PENDING;

		registerEvent(new OrderCreatedEvent(new OrderCreatedEventData(this.getOrderId().getOrderId(), this.userId)));
	}

	public Long getId() {
		return id;
	}

	@TransactionalEventListener
	public void setId(Long id) {
		this.id = id;
	}

	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public LocalDateTime getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(LocalDateTime deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public OrderId getOrderId() {
		return orderId;
	}

	public void setOrderId(OrderId orderId) {
		this.orderId = orderId;
	}

}

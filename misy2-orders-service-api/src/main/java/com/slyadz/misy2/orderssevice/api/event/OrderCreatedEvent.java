package com.slyadz.misy2.orderssevice.api.event;

public class OrderCreatedEvent {
	private OrderCreatedEventData orderCreatedEventData;

	public OrderCreatedEvent(OrderCreatedEventData orderCreatedEventData) {
		this.orderCreatedEventData = orderCreatedEventData;
	}

	public OrderCreatedEventData getOrderCreatedEventData() {
		return orderCreatedEventData;
	}	

}

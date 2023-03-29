package com.slyadz.misy2.orderssevice.api.event;

public class OrderCreatedEventData {
	private String orderId;
	private Long userId;
	

	public OrderCreatedEventData() {
	}

	public OrderCreatedEventData(String orderId, Long userId) {
		this.orderId = orderId;
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public Long getUserId() {
		return userId;
	}

}

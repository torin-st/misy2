package com.slyadz.misy2.orderssevice.api.dto;

public class CreateOrderResponse {
	private long orderId;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public CreateOrderResponse() {
	}

	public CreateOrderResponse(long orderId) {
		this.orderId = orderId;
	}

}

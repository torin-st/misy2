package com.slyadz.misy2.orderssevice.api.dto;

import java.time.LocalDateTime;

public class CreateOrderRequest {
	private long userID;
	private LocalDateTime deliveryTime;

	public CreateOrderRequest() { }

	public CreateOrderRequest(long userID, LocalDateTime deliveryTime) {
		this.userID = userID;
		this.deliveryTime = deliveryTime;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public LocalDateTime getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(LocalDateTime deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

}

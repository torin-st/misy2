package com.slyadz.misy2.usersservice.api.event;

public class UserVerificationFailedEventData {
    private String orderId;
    
    public UserVerificationFailedEventData() {
    }

    public UserVerificationFailedEventData(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
    
}

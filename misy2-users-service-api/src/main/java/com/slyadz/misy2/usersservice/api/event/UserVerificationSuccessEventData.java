package com.slyadz.misy2.usersservice.api.event;

public class UserVerificationSuccessEventData {
    private String orderId;

    public UserVerificationSuccessEventData() {
    }

    public UserVerificationSuccessEventData(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
        
}

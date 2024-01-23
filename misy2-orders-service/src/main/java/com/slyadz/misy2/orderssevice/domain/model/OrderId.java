package com.slyadz.misy2.orderssevice.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrderId {
    @Column(name = "order_id")
    private String orderId;

    public OrderId() {
    }

    public OrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

}

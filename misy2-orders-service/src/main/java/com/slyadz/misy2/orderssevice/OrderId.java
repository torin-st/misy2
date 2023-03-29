package com.slyadz.misy2.orderssevice;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
class OrderId {
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

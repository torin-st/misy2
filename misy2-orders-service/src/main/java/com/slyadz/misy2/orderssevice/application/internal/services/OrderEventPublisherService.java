package com.slyadz.misy2.orderssevice.application.internal.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import com.slyadz.misy2.orderssevice.api.OrderTopics;
import com.slyadz.misy2.orderssevice.api.event.OrderCreatedEvent;

@Service
public class OrderEventPublisherService {

    private final static Logger logger = LoggerFactory.getLogger(OrderEventPublisherService.class);
    
    private StreamBridge streamBridge;

    public OrderEventPublisherService(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @TransactionalEventListener
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        logger.info("get event and resent it: " + event.toString());
        streamBridge.send(OrderTopics.CREATED, event.getOrderCreatedEventData());
    }
}

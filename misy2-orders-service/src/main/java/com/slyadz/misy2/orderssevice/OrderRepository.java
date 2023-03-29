package com.slyadz.misy2.orderssevice;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findByOrderId(OrderId orderId);
}

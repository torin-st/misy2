package com.slyadz.misy2.orderssevice.infrastructure.repositories.springdata;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.slyadz.misy2.orderssevice.domain.model.Order;
import com.slyadz.misy2.orderssevice.domain.model.OrderId;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findByOrderId(OrderId orderId);
}

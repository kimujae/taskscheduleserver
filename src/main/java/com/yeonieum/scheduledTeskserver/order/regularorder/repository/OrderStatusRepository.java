package com.yeonieum.scheduledTeskserver.order.regularorder.repository;


import com.yeonieum.scheduledTeskserver.global.enums.OrderStatusCode;
import com.yeonieum.scheduledTeskserver.order.regularorder.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus,Long> {
    OrderStatus findByStatusName(OrderStatusCode statusName);
}

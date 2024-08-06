package com.yeonieum.scheduledTeskserver.order.regularorder.repository;


import com.yeonieum.scheduledTeskserver.order.regularorder.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
}


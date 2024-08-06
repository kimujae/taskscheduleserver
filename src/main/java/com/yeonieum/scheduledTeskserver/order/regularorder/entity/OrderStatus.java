package com.yeonieum.scheduledTeskserver.order.regularorder.entity;


import com.yeonieum.scheduledTeskserver.global.converter.OrderStatusCodeConverter;
import com.yeonieum.scheduledTeskserver.global.enums.OrderStatusCode;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "order_status")
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_id")
    private Long orderStatusId;

    @Convert(converter = OrderStatusCodeConverter.class)
    @Column(name = "status_name", nullable = false)
    private OrderStatusCode statusName;

    @OneToMany(mappedBy = "orderStatus")
    @Builder.Default
    private List<OrderDetail> orderDetailList = new ArrayList<>();
}


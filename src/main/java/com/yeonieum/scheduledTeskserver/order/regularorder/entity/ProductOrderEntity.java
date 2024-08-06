package com.yeonieum.scheduledTeskserver.order.regularorder.entity;

import com.yeonieum.scheduledTeskserver.global.enums.OrderStatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderEntity {
    Long productId;
    String name;
    int originPrice; // 상품금액
    Double discountAmount; // 상품 할인액
    Double finalPrice; // 최종상품금액
    int quantity;
    @Builder.Default
    OrderStatusCode status = OrderStatusCode.PENDING;
    // 상품 주문 상태
    public void changeStatus(OrderStatusCode status) {
        this.status = status;
    }
}


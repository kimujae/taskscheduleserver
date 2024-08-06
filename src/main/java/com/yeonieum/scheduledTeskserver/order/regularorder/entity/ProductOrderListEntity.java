package com.yeonieum.scheduledTeskserver.order.regularorder.entity;

import lombok.*;

import java.util.List;
@Getter

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProductOrderListEntity {
    List<ProductOrderEntity> productOrderEntityList;
}

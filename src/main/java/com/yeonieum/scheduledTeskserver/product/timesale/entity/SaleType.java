package com.yeonieum.scheduledTeskserver.product.timesale.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "sale_type")
public class SaleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_type_id")
    private Long saleTypeId;

    private Long productId;

    @Column(name = "type_name", nullable = false)
    private String typeName;
}

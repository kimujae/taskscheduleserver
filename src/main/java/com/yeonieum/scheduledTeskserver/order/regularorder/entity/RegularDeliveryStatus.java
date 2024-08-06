package com.yeonieum.scheduledTeskserver.order.regularorder.entity;

import com.yeonieum.scheduledTeskserver.global.converter.RegularDeliveryStatusCodeConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "regular_delivery_status")
public class RegularDeliveryStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "regular_delivery_status_id")
    private Long regularDeliveryStatusId;

    @Convert(converter = RegularDeliveryStatusCodeConverter.class)
    @Column(name = "status_name", nullable = false)
    private RegularDeliveryStatus statusName;
}


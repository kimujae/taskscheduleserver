package com.yeonieum.scheduledTeskserver.product.timesale.entity;

import com.yeonieum.scheduledTeskserver.product.productadvertisement.entity.ServiceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "product_time_sale")
public class ProductTimesale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_time_sale_id")
    private Long productTimeSaleId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "start_datetime", nullable = false)
    private LocalDateTime startDatetime;

    @Column(name = "end_datetime", nullable = false)
    private LocalDateTime endDatetime;

    @Column(name = "discount_rate")
    private int discountRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_status_id", nullable = false)
    private ServiceStatus serviceStatus;
    // 세가지 상태를 가져야한다. pending, inactive, active

    public void changeStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
}

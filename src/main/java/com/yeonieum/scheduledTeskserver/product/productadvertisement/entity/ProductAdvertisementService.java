package com.yeonieum.scheduledTeskserver.product.productadvertisement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "product_advertisement_service")
public class ProductAdvertisementService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_advertisement_service_id")
    private Long productAdvertisementServiceId;
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_status_id", nullable = false)
    private ServiceStatus serviceStatus;
    // 세가지 상태를 가져야한다. pending, inactive, active

    public void changeStatus(ServiceStatus serviceStatus) {
        serviceStatus = serviceStatus;
    }
}

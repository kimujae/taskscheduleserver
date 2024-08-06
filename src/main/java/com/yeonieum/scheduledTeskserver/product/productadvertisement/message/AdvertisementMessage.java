package com.yeonieum.scheduledTeskserver.product.productadvertisement.message;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class AdvertisementMessage {
    private Long productId;
    private LocalDate startDate;
    private LocalDate endDate;
}

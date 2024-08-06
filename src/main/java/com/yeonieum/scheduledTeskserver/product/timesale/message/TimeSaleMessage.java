package com.yeonieum.scheduledTeskserver.product.timesale.message;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class TimeSaleMessage {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Long productId;
}

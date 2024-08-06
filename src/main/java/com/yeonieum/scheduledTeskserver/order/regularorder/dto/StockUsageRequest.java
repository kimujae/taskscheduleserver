package com.yeonieum.scheduledTeskserver.order.regularorder.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class StockUsageRequest {
    @Getter
    @Builder
    public static class OfIncreasing {
        String orderDetailId;
        Long productId;
        int quantity;
        String memberId;
    }

    @Getter
    @Builder
    public static class IncreaseStockUsageList {
        List<OfIncreasing> ofIncreasingList;
    }
}

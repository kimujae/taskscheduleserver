package com.yeonieum.scheduledTeskserver.infrastructure.feignclient;

import com.yeonieum.scheduledTeskserver.order.regularorder.dto.StockUsageRequest;
import com.yeonieum.scheduledTeskserver.order.regularorder.service.RegularOrderScheduleService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "productservice", url = "http://localhost:8020")
public interface ProductServiceFeignClient {
    @GetMapping("/api/inventory/regular-order/stock-usage")
    ResponseEntity<List<RegularOrderScheduleService.OfOrderInformation>> getAvailableRegularDelivery(@RequestBody StockUsageRequest.IncreaseStockUsageList increaseStockUsageList);
}


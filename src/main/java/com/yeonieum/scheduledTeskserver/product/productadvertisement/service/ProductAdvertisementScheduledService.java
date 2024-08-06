package com.yeonieum.scheduledTeskserver.product.productadvertisement.service;

import com.yeonieum.scheduledTeskserver.global.enums.ServiceStatusCode;
import com.yeonieum.scheduledTeskserver.product.productadvertisement.message.AdvertisementMessage;
import com.yeonieum.scheduledTeskserver.product.productadvertisement.repository.ServiceStatusRepository;
import com.yeonieum.scheduledTeskserver.product.productadvertisement.entity.ProductAdvertisementService;
import com.yeonieum.scheduledTeskserver.product.productadvertisement.repository.ProductAdvertisementServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;


@Service
@RequiredArgsConstructor
public class ProductAdvertisementScheduledService {
    private final ThreadPoolTaskScheduler scheduler;
    private final ProductAdvertisementServiceRepository productAdvertisementServiceRepository;
    private final ServiceStatusRepository serviceStatusRepository;

    public void scheduleTask(AdvertisementMessage advertisementMessage) {
        Runnable triggerTask = () -> triggerProductAdvertisement(advertisementMessage.getProductId()); // 메시지에서 상품 id 추출하기
        Instant triggerInstant = advertisementMessage.getStartDate().atStartOfDay().toInstant(ZoneOffset.UTC);
        scheduler.schedule(triggerTask, triggerInstant);

        Runnable inActivateTask = () -> inActivateProductAdvertisement(advertisementMessage.getProductId()); // 메시지에서 상품 id 추출하기
        Instant inActiveInstant = advertisementMessage.getEndDate().atStartOfDay().toInstant(ZoneOffset.UTC);
        scheduler.schedule(triggerTask, inActiveInstant);
    }

    @Transactional
    private void triggerProductAdvertisement(Long productId) {
        ProductAdvertisementService productAdvertisementService = productAdvertisementServiceRepository.findById(productId);
        productAdvertisementService.changeStatus(serviceStatusRepository.findByStatusName(ServiceStatusCode.IN_PROGRESS.getCode()));
        productAdvertisementServiceRepository.save(productAdvertisementService);
    }

    @Transactional
    private void inActivateProductAdvertisement(Long productId) {
        ProductAdvertisementService productAdvertisementService = productAdvertisementServiceRepository.findById(productId);
        productAdvertisementService.changeStatus(serviceStatusRepository.findByStatusName(ServiceStatusCode.ENDED_EVENT.getCode()));
        productAdvertisementServiceRepository.save(productAdvertisementService);
    }
}

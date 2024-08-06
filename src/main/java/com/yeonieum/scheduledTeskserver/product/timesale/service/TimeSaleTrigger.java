package com.yeonieum.scheduledTeskserver.product.timesale.service;

import com.yeonieum.scheduledTeskserver.global.enums.ServiceStatusCode;
import com.yeonieum.scheduledTeskserver.product.productadvertisement.entity.ServiceStatus;
import com.yeonieum.scheduledTeskserver.product.productadvertisement.repository.ServiceStatusRepository;
import com.yeonieum.scheduledTeskserver.product.timesale.entity.ProductTimesale;
import com.yeonieum.scheduledTeskserver.product.timesale.repository.ProductTimeSaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
@RequiredArgsConstructor
public class TimeSaleTrigger {
    private final ProductTimeSaleRepository productTimeSaleRepository;
    private final ServiceStatusRepository serviceStatusRepository;

    @Transactional(propagation = REQUIRES_NEW)
    public void triggerProductAdvertisement(Long productId) {
        ProductTimesale productTimeSale = productTimeSaleRepository.findById(productId);
        ServiceStatus serviceStatus = serviceStatusRepository.findByStatusName(ServiceStatusCode.IN_PROGRESS.getCode());
        productTimeSale.changeStatus(serviceStatus);
        productTimeSaleRepository.save(productTimeSale);
    }

    @Transactional(propagation = REQUIRES_NEW)
    public void inActivateProductAdvertisement(Long productId) {
        ProductTimesale productTimeSale = productTimeSaleRepository.findById(productId);
        ServiceStatus serviceStatus = serviceStatusRepository.findByStatusName(ServiceStatusCode.ENDED_EVENT.getCode());
        productTimeSale.changeStatus(serviceStatus);
        productTimeSaleRepository.save(productTimeSale);
    }
}

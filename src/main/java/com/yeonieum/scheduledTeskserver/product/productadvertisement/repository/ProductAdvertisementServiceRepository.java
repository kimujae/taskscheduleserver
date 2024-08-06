package com.yeonieum.scheduledTeskserver.product.productadvertisement.repository;

import com.yeonieum.scheduledTeskserver.product.productadvertisement.entity.ProductAdvertisementService;
import org.springframework.data.repository.Repository;

public interface ProductAdvertisementServiceRepository extends Repository<ProductAdvertisementService, Long> {
    void save(ProductAdvertisementService productAdvertisementService);
    ProductAdvertisementService findById(Long productAdvertisementServiceId);



}


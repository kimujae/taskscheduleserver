package com.yeonieum.scheduledTeskserver.product.timesale.repository;

import com.yeonieum.scheduledTeskserver.product.timesale.entity.ProductTimesale;
import org.springframework.data.repository.Repository;

public interface ProductTimeSaleRepository extends Repository<ProductTimesale, Long> {
    ProductTimesale findById(Long productTimesaleId);
    ProductTimesale findByProductId(Long productId);
    void save(ProductTimesale productTimeSale);
}

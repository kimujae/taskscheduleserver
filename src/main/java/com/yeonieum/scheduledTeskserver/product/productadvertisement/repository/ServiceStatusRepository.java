package com.yeonieum.scheduledTeskserver.product.productadvertisement.repository;

import com.yeonieum.scheduledTeskserver.product.productadvertisement.entity.ServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceStatusRepository extends JpaRepository<ServiceStatus, Long> {
    ServiceStatus findByStatusName(String statusName);
}

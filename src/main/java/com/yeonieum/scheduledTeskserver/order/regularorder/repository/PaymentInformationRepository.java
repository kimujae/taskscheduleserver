package com.yeonieum.scheduledTeskserver.order.regularorder.repository;

import com.yeonieum.scheduledTeskserver.order.regularorder.entity.PaymentInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInformationRepository extends JpaRepository<PaymentInformation,Long> {
}

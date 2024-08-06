package com.yeonieum.scheduledTeskserver.order.regularorder.repository;

import com.yeonieum.scheduledTeskserver.order.regularorder.entity.RegularDeliveryApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RegularDeliveryApplicationRepository extends JpaRepository<RegularDeliveryApplication, Long> {

    @Query("SELECT DISTINCT a " +
            "FROM RegularDeliveryApplication a " +
            "LEFT JOIN FETCH a.regularDeliveryReservationList r " +
            "WHERE a.nextDeliveryDate = :targetDate " +
            "AND a.regularDeliveryStatus.statusName = 'PENDING'")
    List<RegularDeliveryApplication> findPendingApplicationsWithReservations(@Param("targetDate") LocalDate targetDate);
}

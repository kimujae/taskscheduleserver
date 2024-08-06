package com.yeonieum.scheduledTeskserver;

import com.yeonieum.scheduledTeskserver.order.regularorder.repository.RegularDeliveryApplicationRepository;
import com.yeonieum.scheduledTeskserver.product.timesale.repository.ProductTimeSaleRepository;
import com.yeonieum.scheduledTeskserver.product.timesale.service.TimeSaleTrigger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScheduledTeskserverApplicationTests {
//	@Autowired
//	private ProductAdvertisementScheduledService productAdvertisementScheduledService;
//
//	@Test
//	void test() {
//
//	}



    @Autowired
    private TimeSaleTrigger timeSaleTrigger;

    @Autowired
    private ProductTimeSaleRepository regularDeliveryApplicationRepository;
	@Test
	void contextLoads() {
        regularDeliveryApplicationRepository.findByProductId(1L);
	}

}

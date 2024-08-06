package com.yeonieum.scheduledTeskserver.product.timesale.service;

import com.yeonieum.scheduledTeskserver.product.timesale.message.TimeSaleMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class TimeSaleScheduledService {
    /*
        상품의 판매타입을 변경해야한다.
     */
    private final ThreadPoolTaskScheduler scheduler;
    private final TimeSaleTrigger timeSaleTrigger;

    public void scheduleTask(TimeSaleMessage timeSaleMessage) {
        Runnable triggerTask = () -> timeSaleTrigger.triggerProductAdvertisement(timeSaleMessage.getProductId()); // 메시지에서 상품 id 추출하기
        Instant triggerInstant = timeSaleMessage.getStartDateTime().minusHours(9).toInstant(ZoneOffset.UTC);
        scheduler.schedule(triggerTask, triggerInstant);


        Runnable inActivateTask = () -> timeSaleTrigger.inActivateProductAdvertisement(timeSaleMessage.getProductId()); // 메시지에서 상품 id 추출하기
        Instant inActiveInstant = timeSaleMessage.getEndDateTime().minusHours(9).toInstant(ZoneOffset.UTC);
        scheduler.schedule(inActivateTask, inActiveInstant);
    }
}

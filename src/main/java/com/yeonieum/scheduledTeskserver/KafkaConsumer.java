package com.yeonieum.scheduledTeskserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeonieum.scheduledTeskserver.product.productadvertisement.message.AdvertisementMessage;
import com.yeonieum.scheduledTeskserver.product.productadvertisement.service.ProductAdvertisementScheduledService;
import com.yeonieum.scheduledTeskserver.product.timesale.message.TimeSaleMessage;
import com.yeonieum.scheduledTeskserver.product.timesale.service.TimeSaleScheduledService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final ProductAdvertisementScheduledService productAdvertisementScheduledService;
    private final TimeSaleScheduledService timeSaleScheduledService;
    private final ObjectMapper objectMapper;

    @KafkaListener(id = "advertisement-consumer", topics = "advertisement-topic", groupId = "schedule-group-0", autoStartup = "true")
    public void listenAdvertisementTopic(@Payload String message) {
        try {
            AdvertisementMessage advertisementMessage = objectMapper.readValue(message, AdvertisementMessage.class);
            productAdvertisementScheduledService.scheduleTask(advertisementMessage);
        } catch (JsonProcessingException e) {
            // 무시
        }
    }

    @KafkaListener(id = "timesale-consumer", topics = "timesale-topic", groupId = "schedule-group-0", autoStartup = "true")
    public void listenTimeSaleTopic(@Payload String message){
        try {
            TimeSaleMessage timeSaleMessage = objectMapper.readValue(message, TimeSaleMessage.class);
            timeSaleScheduledService.scheduleTask(timeSaleMessage);
        } catch (JsonProcessingException e) {
            // 무시
        }
    }
}

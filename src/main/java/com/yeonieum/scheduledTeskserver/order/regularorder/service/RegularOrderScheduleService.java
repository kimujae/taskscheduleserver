package com.yeonieum.scheduledTeskserver.order.regularorder.service;

import com.yeonieum.scheduledTeskserver.global.enums.ActiveStatus;
import com.yeonieum.scheduledTeskserver.global.enums.OrderStatusCode;
import com.yeonieum.scheduledTeskserver.infrastructure.feignclient.ProductServiceFeignClient;
import com.yeonieum.scheduledTeskserver.order.regularorder.entity.*;
import com.yeonieum.scheduledTeskserver.order.regularorder.repository.OrderStatusRepository;
import com.yeonieum.scheduledTeskserver.order.regularorder.repository.PaymentInformationRepository;
import com.yeonieum.scheduledTeskserver.order.regularorder.dto.StockUsageRequest;
import com.yeonieum.scheduledTeskserver.order.regularorder.repository.OrderDetailRepository;
import com.yeonieum.scheduledTeskserver.order.regularorder.repository.RegularDeliveryApplicationRepository;
import lombok.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegularOrderScheduleService {

    private final ProductServiceFeignClient productServiceFeignClient;
    private final OrderDetailRepository orderDetailRepository;

    private final PaymentInformationRepository paymentInformationRepository;
    private final RegularDeliveryApplicationRepository regularDeliveryApplicationRepository;

    private final OrderStatusRepository orderStatusRepository;


    /**
     * TODO : 카드 번호, 상점 이름 정보 가져오기
     */
    @Scheduled(cron = "0 0 5 * * * ")
    public void schedule() {
        LocalDate targetDate = LocalDate.now().plusDays(3);
        List<RegularDeliveryApplication> applications = regularDeliveryApplicationRepository.findPendingApplicationsWithReservations(targetDate);

        for(RegularDeliveryApplication application : applications) {
            createOrder(application);
        }
    }

    @Transactional
    public void createOrder(RegularDeliveryApplication application) {
        List<OfOrderInformation> result = checkAvailableOrderProduct(application.getRegularDeliveryReservationList());
        double totalAmount = calculateTotalPaymentAmount(result);
        OrderStatus orderStatus = orderStatusRepository.findByStatusName(OrderStatusCode.PENDING);
        OrderDetail orderDetail = createOrderDetail(makeOrderId(), result, application, orderStatus);
        int discountAmount = calculateTotalDiscountAmount(orderDetail);
        transitionOrderStatusToPaymentCompleted(orderDetail);

        orderDetailRepository.save(orderDetail);
        paymentInformationRepository.save(PaymentInformation.builder()
                .orderDetail(orderDetail)
                .discountAmount(discountAmount)
                .paymentAmount((int)totalAmount)
                .isRefunded(ActiveStatus.INACTIVE)
                .cardNumber("1234-5678-1234-5678")
                .originProductPrice(result.stream().mapToInt(OfOrderInformation::getOriginPrice).sum())
                .build());
    }

    public int calculateTotalDiscountAmount(OrderDetail orderDetail) {
        return (int) orderDetail.getOrderList().getProductOrderEntityList().stream()
                .filter(productOrder -> productOrder.getStatus().equals(OrderStatusCode.PENDING))
                .mapToDouble(ProductOrderEntity::getDiscountAmount)
                .sum();
    }

    public void transitionOrderStatusToPaymentCompleted(OrderDetail orderDetail) {
        orderDetail.getOrderList().getProductOrderEntityList().stream()
                .filter(productOrder -> productOrder.getStatus().equals(OrderStatusCode.PENDING))
                .forEach(productOrder -> productOrder.changeStatus(OrderStatusCode.PAYMENT_COMPLETED));
    }
    public List<OfOrderInformation> checkAvailableOrderProduct (List<RegularDeliveryReservation> deliveryReservation) {
        String orderDetailId = makeOrderId();
        List<OfOrderInformation> response = productServiceFeignClient.getAvailableRegularDelivery(makeRequestObject(deliveryReservation, orderDetailId)).getBody();
        response.stream().filter(ofOrderInformation -> !ofOrderInformation.isAvailable())
                .forEach(ofOrderInformation -> {
                    if (ofOrderInformation.isAvailable()) {
                        ofOrderInformation.cancelOrder();
                    }
                });

        return response;
    }

    public double calculateTotalPaymentAmount(List<OfOrderInformation> orderInformationList) {
        return orderInformationList.stream()
                .mapToDouble(ofOrderInformation -> ofOrderInformation.getFinalPrice() * ofOrderInformation.getQuantity())
                .sum();
    }

    public OrderDetail createOrderDetail(String orderId,
                                         List<OfOrderInformation> result,
                                         RegularDeliveryApplication application,
                                         OrderStatus orderStatus) {

        return OrderDetail.builder()
                .orderDetailId(orderId)
                .customerId(application.getCustomerId())
                .memberId(application.getMemberId())
                .orderMemo("")
                .orderDateTime(LocalDateTime.now())
                .deliveryAddress(application.getAddress())
                .orderStatus(orderStatus)
                .recipient(application.getRecipient())
                .recipientPhoneNumber(application.getRecipientPhoneNumber())
                .storeName("4팀")
                .orderList(ProductOrderListEntity.builder()
                        .productOrderEntityList(result.stream()
                                .map(ofOrderInformation -> ProductOrderEntity.builder()
                                        .productId(ofOrderInformation.getProductId())
                                        .name(ofOrderInformation.getName())
                                        .originPrice(ofOrderInformation.getOriginPrice())
                                        .discountAmount(ofOrderInformation.getOriginPrice() - ofOrderInformation.getFinalPrice())
                                        .finalPrice(ofOrderInformation.getFinalPrice())
                                        .quantity(ofOrderInformation.getQuantity())
                                        .status(ofOrderInformation.getStatus())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .build();
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OfOrderInformation {
        private Long productId;
        private String productName;
        private String name;
        private int originPrice;
        private int regularPrice;
        private double finalPrice;
        private int quantity;
        boolean isAvailable;
        @Builder.Default
        OrderStatusCode status = OrderStatusCode.PENDING;

        public void cancelOrder() {
            // isAvailable 이 false일 경우 status를 CANCELED로
            if (!isAvailable) {
                this.status = OrderStatusCode.CANCELED;
            }
        }
    }

    public StockUsageRequest.IncreaseStockUsageList makeRequestObject(List<RegularDeliveryReservation> reservation,
                                                                      String orderDetailId) {
        List<StockUsageRequest.OfIncreasing> stockUsageDtoList = new ArrayList<>();

        for (RegularDeliveryReservation deliveryReservation : reservation) {
            stockUsageDtoList.add(StockUsageRequest.OfIncreasing.builder()
                    .orderDetailId(orderDetailId)
                    .productId(deliveryReservation.getProductId())
                    .quantity(deliveryReservation.getQuantity())
                    .memberId(deliveryReservation.getMemberId())
                    .build());
        }

        StockUsageRequest.IncreaseStockUsageList increaseStockUsageList =
                StockUsageRequest.IncreaseStockUsageList.builder()
                        .ofIncreasingList(stockUsageDtoList)
                        .build();

        return increaseStockUsageList;
    }

    /**
     * 가상 결제 시스템
     * @return
     */
    private boolean checkPaymentValidation() {
        return true;
    }

    /**
     * 현재 날짜 및 시간과 UUID 4자리 조합으로 주문ID 생성
     * @return
     */
    private String makeOrderId() {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uniqueId = UUID.randomUUID().toString().replace("-", "").substring(0, 4);
        return timestamp + "-" + uniqueId;
    }
}

package com.nhnacademy.illuwa.order.service;

import com.nhnacademy.illuwa.order.client.OrderServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderStatusMemberService {

    private final OrderServiceClient orderServiceClient;

    public void confirmOrder(Long orderId) {
        orderServiceClient.confirmOrder(orderId);
    }

    public void cancelOrder(Long orderId) {
        orderServiceClient.cancelOrder(orderId);
    }

    public void cancelPayment(String orderNumber) {
        orderServiceClient.cancelPayment(orderNumber);
    }

    public void refundOrder(Long orderId) {
        orderServiceClient.refundOrder(orderId);
    }
}

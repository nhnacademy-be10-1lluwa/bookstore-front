package com.nhnacademy.illuwa.order.service.impl;

import com.nhnacademy.illuwa.order.client.CommonOrderClient;
import com.nhnacademy.illuwa.order.dto.command.status.ReturnRequestCreateRequest;
import com.nhnacademy.illuwa.order.dto.types.ReturnReason;
import com.nhnacademy.illuwa.order.service.CommonOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommonOrderServiceImpl implements CommonOrderService {

    private final CommonOrderClient commonOrderClient;

    @Override
    public void confirmOrder(Long orderId) {
        commonOrderClient.confirm(orderId);
    }

    @Override
    public void cancelOrder(Long orderId) {
        commonOrderClient.cancelOrder(orderId);
    }

    @Override
    public void cancelPayment(String orderNumber) {
        commonOrderClient.cancelPayment(orderNumber);
    }

    @Override
    public void refundOrder(Long orderId, ReturnReason returnReason) {
        ReturnRequestCreateRequest request = new ReturnRequestCreateRequest(LocalDateTime.now(), returnReason);
        commonOrderClient.refund(orderId, request);
    }
}

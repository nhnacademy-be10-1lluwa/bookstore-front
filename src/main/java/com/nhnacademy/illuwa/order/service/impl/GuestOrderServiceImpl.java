package com.nhnacademy.illuwa.order.service.impl;

import com.nhnacademy.illuwa.order.client.GuestOrderClient;
import com.nhnacademy.illuwa.order.dto.query.info.OrderCreateResponse;
import com.nhnacademy.illuwa.order.dto.query.detail.OrderResponse;
import com.nhnacademy.illuwa.order.dto.command.create.GuestOrderDirectRequest;
import com.nhnacademy.illuwa.order.dto.query.info.GuestOrderInitDirectResponse;
import com.nhnacademy.illuwa.order.service.GuestOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GuestOrderServiceImpl implements GuestOrderService {

    private final GuestOrderClient guestOrderClient;

    @Override
    public GuestOrderInitDirectResponse initDirect(Long bookId) {
        return guestOrderClient.initDirect(bookId);
    }

    @Override
    public OrderCreateResponse createDirectOrder(GuestOrderDirectRequest request) {
        return guestOrderClient.createDirect(request);
    }

    @Override
    public OrderResponse getOrderHistory(Long orderId) {
        return guestOrderClient.getHistory(orderId);
    }
}

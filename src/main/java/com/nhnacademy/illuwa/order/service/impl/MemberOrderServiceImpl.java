package com.nhnacademy.illuwa.order.service.impl;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.order.client.MemberOrderClient;
import com.nhnacademy.illuwa.order.dto.query.info.OrderCreateResponse;
import com.nhnacademy.illuwa.order.dto.query.list.OrderListResponse;
import com.nhnacademy.illuwa.order.dto.query.detail.OrderResponse;
import com.nhnacademy.illuwa.order.dto.command.create.MemberOrderCartRequest;
import com.nhnacademy.illuwa.order.dto.command.create.MemberOrderDirectRequest;
import com.nhnacademy.illuwa.order.dto.query.info.MemberOrderInitDirectResponse;
import com.nhnacademy.illuwa.order.dto.query.info.MemberOrderInitFromCartResponse;
import com.nhnacademy.illuwa.order.service.MemberOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberOrderServiceImpl implements MemberOrderService {

    private final MemberOrderClient memberOrderClient;

    @Override
    public MemberOrderInitDirectResponse initDirect(Long bookId) {
        return memberOrderClient.initDirect(bookId);
    }

    @Override
    public MemberOrderInitFromCartResponse initFromCart() {
        return memberOrderClient.initFromCart();
    }

    @Override
    public OrderCreateResponse createDirectOrder(MemberOrderDirectRequest request) {
        return memberOrderClient.createDirect(request);
    }

    @Override
    public OrderCreateResponse createCartOrder(MemberOrderCartRequest request) {
        return memberOrderClient.createFromCart(request);
    }

    @Override
    public PageResponse<OrderListResponse> getOrderHistory(int page, int size) {
        return memberOrderClient.getHistory(page, size);
    }

    @Override
    public OrderResponse getOrderDetail(Long orderId) {
        return memberOrderClient.getDetail(orderId);
    }
}

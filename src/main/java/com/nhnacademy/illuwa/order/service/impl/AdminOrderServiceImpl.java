package com.nhnacademy.illuwa.order.service.impl;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.order.client.AdminOrderClient;
import com.nhnacademy.illuwa.order.dto.OrderListResponse;
import com.nhnacademy.illuwa.order.dto.OrderResponse;
import com.nhnacademy.illuwa.order.dto.admin.OrderUpdateStatusDto;
import com.nhnacademy.illuwa.order.dto.types.OrderStatus;
import com.nhnacademy.illuwa.order.service.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {

    private final AdminOrderClient adminOrderClient;

    @Override
    public PageResponse<OrderListResponse> listOrdersByStatus(OrderStatus status, int page, int size) {
        return adminOrderClient.listByStatus(status, page, size);
    }

    @Override
    public OrderResponse getOrderDetail(Long orderId) {
        return adminOrderClient.detail(orderId);
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus status) {
        adminOrderClient.updateStatus(orderId, new OrderUpdateStatusDto(status));
    }

    @Override
    public void runOrderConfirmedBatch() {
        adminOrderClient.runOrderConfirmedUpdate();
    }

    @Override
    public void runMemberGradeBatch() {
        adminOrderClient.runMemberGradeUpdate();
    }

    @Override
    public void cleanAwaitingOrders() {
        adminOrderClient.runOrderCleanup();
    }
}

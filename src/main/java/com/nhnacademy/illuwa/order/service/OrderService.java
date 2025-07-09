package com.nhnacademy.illuwa.order.service;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.order.client.OrderServiceClient;
import com.nhnacademy.illuwa.order.dto.OrderListResponse;
import com.nhnacademy.illuwa.order.dto.OrderResponse;
import com.nhnacademy.illuwa.order.dto.PackagingRequestDto;
import com.nhnacademy.illuwa.order.dto.PackagingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderServiceClient orderServiceClient;

    public List<PackagingResponseDto> getPackagingListFromApi() {
        return orderServiceClient.getPackaging();
    }

    public PackagingResponseDto registerPackaging(PackagingRequestDto packagingRequestDto) {
        return orderServiceClient.createPackaging(packagingRequestDto);
    }

    public PageResponse<OrderListResponse> getMemberOrderHistory(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size
    ) {
        return orderServiceClient.getMemberOrdersHistory(page, size);
    }

    public OrderResponse getOrderDetail(Long orderId) {
        return orderServiceClient.getMemberOrderHistoryDetail(orderId);
    }
}

package com.nhnacademy.illuwa.order.service;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.order.client.OrderServiceClient;
import com.nhnacademy.illuwa.order.dto.OrderListResponse;
import com.nhnacademy.illuwa.order.dto.OrderResponse;
import com.nhnacademy.illuwa.order.dto.PackagingRequestDto;
import com.nhnacademy.illuwa.order.dto.PackagingResponseDto;
import com.nhnacademy.illuwa.order.dto.admin.OrderUpdateStatusDto;
import com.nhnacademy.illuwa.order.dto.member.MemberOrderInitDirectResponse;
import com.nhnacademy.illuwa.order.dto.types.OrderStatus;
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

    // 포장 옵션 등록
    public PackagingResponseDto registerPackaging(PackagingRequestDto packagingRequestDto) {
        return orderServiceClient.createPackaging(packagingRequestDto);
    }

    // 멤버별 주문 내역
    public PageResponse<OrderListResponse> getMemberOrderHistory(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size
    ) {
        return orderServiceClient.getMemberOrdersHistory(page, size);
    }

    // 주문 상세 페이지
    public OrderResponse getOrderDetail(Long orderId) {
        return orderServiceClient.getMemberOrderHistoryDetail(orderId);
    }

    // 회원 바로 주문 정보 불러오기
    public MemberOrderInitDirectResponse getMemberInitDateDirect(Long bookId) {
        return orderServiceClient.fetchMemberDirectInfo(bookId);
    }

    // Pending 상태 주문 조회
    public PageResponse<OrderListResponse> getOrderStatusPending(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        return orderServiceClient.getOrderStatusPending(OrderStatus.Pending, page, size);
    }

    // 어드민 주문 상세 조회
    public OrderResponse getAdminOrderDetail(Long orderId) {
        return orderServiceClient.fetchOrderDetail(orderId);
    }

    // 어드민 주문 상태 변경
    public void updateStatus(Long orderId, OrderStatus status) {
        orderServiceClient.updateOrderStatus(orderId, new OrderUpdateStatusDto(status));
    }
}

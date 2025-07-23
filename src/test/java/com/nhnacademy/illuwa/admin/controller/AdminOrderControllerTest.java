package com.nhnacademy.illuwa.admin.controller;

import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.config.handler.CategoryControllerAdvice;
import com.nhnacademy.illuwa.order.dto.query.detail.OrderResponse;
import com.nhnacademy.illuwa.order.dto.query.list.OrderListResponse;
import com.nhnacademy.illuwa.order.dto.types.OrderStatus;
import com.nhnacademy.illuwa.order.service.AdminOrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AdminOrderController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CategoryControllerAdvice.class})
        })
@AutoConfigureMockMvc(addFilters = false)
public class AdminOrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminOrderService adminOrderService;

    @DisplayName("결제 완료된 주문들 페이지 조회")
    @Test
    void test_getPaidOrders() throws Exception {
        OrderListResponse order1 = new OrderListResponse(
                1L, "20250708-0001", LocalDateTime.of(2025,7,8,14,30), BigDecimal.valueOf(15000), OrderStatus.Confirmed);
        OrderListResponse order2 = new OrderListResponse(
                2L, "20250709-0002", LocalDateTime.of(2025,7,9,9,0), BigDecimal.valueOf(20000), OrderStatus.Delivered);

        PageResponse<OrderListResponse> pageResponse = new PageResponse<>(
                List.of(order1, order2), 3, 25L, 0, 10, false, true);

        when(adminOrderService.listOrdersByStatus(eq(OrderStatus.Confirmed), anyInt(), anyInt()))
                .thenReturn(pageResponse);

        mockMvc.perform(get("/admin/orders")
                        .param("order-status", "Confirmed")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/order/orders"))
                .andExpect(model().attributeExists("orderPage"))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 3));
    }

    @DisplayName("멤버 주문 상세 페이지 조회")
    @Test
    void test_getDetailOrder() throws Exception {
        OrderResponse order = new OrderResponse();
        order.setOrderId(1L);
        order.setOrderNumber("20250708-0001");
        order.setOrderStatus(OrderStatus.Confirmed);
        when(adminOrderService.getOrderDetail(1L)).thenReturn(order);

        mockMvc.perform(get("/admin/orders/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/order/order_detail"))
                .andExpect(model().attributeExists("order"));
    }

    @DisplayName("주문 상태 변경 POST 요청")
    @Test
    void test_updateStatus() throws Exception {
        mockMvc.perform(post("/admin/orders/100/status")
                        .param("orderStatus", "Delivered"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/orders/100"));

        verify(adminOrderService).updateOrderStatus(100L, OrderStatus.Delivered);
    }

    @DisplayName("구매 확정 배치 실행 POST 요청")
    @Test
    void test_orderConfirmedUpdate() throws Exception {
        mockMvc.perform(post("/admin/orders/order-confirmed-update"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));

        verify(adminOrderService).runOrderConfirmedBatch();
    }

    @DisplayName("멤버 등급 업데이트 배치 실행 POST 요청")
    @Test
    void test_memberGradeUpdate() throws Exception {
        mockMvc.perform(post("/admin/orders/member-grade-update"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/members"));

        verify(adminOrderService).runMemberGradeBatch();
    }

    @DisplayName("주문 정리 배치 실행 POST 요청")
    @Test
    void test_orderCleanUp() throws Exception {
        mockMvc.perform(post("/admin/orders/order-cleanup"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));

        verify(adminOrderService).cleanAwaitingOrders();
    }
}

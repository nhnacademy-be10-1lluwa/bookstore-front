package com.nhnacademy.illuwa.order.client;

import com.nhnacademy.illuwa.order.dto.OrderResponse;
import com.nhnacademy.illuwa.order.dto.PackagingRequestDto;
import com.nhnacademy.illuwa.order.dto.PackagingResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "order-service", url = "${api.base-url}")
public interface OrderServiceClient {
    @GetMapping("/api/packaging")
    List<PackagingResponseDto> getPackaging();

    @PostMapping("/api/packaging")
    PackagingResponseDto createPackaging(PackagingRequestDto packagingRequestDto);

    @GetMapping("/order/guest/order-history/{orderNumber}")
    OrderResponse getGuestOrderHistory(@PathVariable("orderNumber") String orderNumber, @RequestParam("contact") String recipientContact);
}

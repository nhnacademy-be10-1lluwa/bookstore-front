package com.nhnacademy.illuwa.order.client;

import com.nhnacademy.illuwa.order.dto.PackagingRequestDto;
import com.nhnacademy.illuwa.order.dto.PackagingResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "order-service", url = "${api.base-url}")
public interface OrderServiceClient {
    @GetMapping("/api/packaging")
    List<PackagingResponseDto> getPackaging();

    @PostMapping("/api/packaging")
    PackagingResponseDto createPackaging(PackagingRequestDto packagingRequestDto);
}

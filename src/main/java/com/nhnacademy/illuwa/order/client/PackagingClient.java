package com.nhnacademy.illuwa.order.client;


import com.nhnacademy.illuwa.order.dto.PackagingRequestDto;
import com.nhnacademy.illuwa.order.dto.PackagingResponseDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "order-service", url = "${api.base-url}", contextId = "packagingClient")
public interface PackagingClient {

    // 포장 옵션 조회
    @GetMapping("/api/packaging")
    List<PackagingResponseDto> getAll();

    // 새로운 포장 옵션 생성
    @PostMapping("/api/packaging")
    PackagingResponseDto create(@RequestBody @Valid PackagingRequestDto request);
}

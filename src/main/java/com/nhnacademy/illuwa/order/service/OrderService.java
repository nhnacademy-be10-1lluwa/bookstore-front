package com.nhnacademy.illuwa.order.service;

import com.nhnacademy.illuwa.order.client.OrderServiceClient;
import com.nhnacademy.illuwa.order.dto.PackagingRequestDto;
import com.nhnacademy.illuwa.order.dto.PackagingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}

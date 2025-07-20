package com.nhnacademy.illuwa.order.service;


import com.nhnacademy.illuwa.order.dto.PackagingRequestDto;
import com.nhnacademy.illuwa.order.dto.PackagingResponseDto;

import java.util.List;

public interface PackagingService {

    // 활성화된 포장 옵션 조회
    List<PackagingResponseDto> getAllPackagingOptions();

    // 새로운 포장 옵션 생성
    void createPackaging(PackagingRequestDto dto);
}

package com.nhnacademy.illuwa.order.service;


import com.nhnacademy.illuwa.order.dto.command.create.PackagingRequest;
import com.nhnacademy.illuwa.order.dto.query.info.PackagingResponse;

import java.util.List;

public interface PackagingService {

    // 활성화된 포장 옵션 조회
    List<PackagingResponse> getAllPackagingOptions();

    // 새로운 포장 옵션 생성
    void createPackaging(PackagingRequest dto);
}

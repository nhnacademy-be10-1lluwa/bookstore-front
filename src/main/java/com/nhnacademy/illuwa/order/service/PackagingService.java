package com.nhnacademy.illuwa.order.service;

import com.nhnacademy.illuwa.order.dto.PackagingRequestDto;
import com.nhnacademy.illuwa.order.dto.PackagingResponseDto;

import java.util.List;

public interface PackagingService {

    List<PackagingResponseDto> getPackagingListFromApi();

    PackagingResponseDto registerPackaging(PackagingRequestDto packagingRequestDto);
}

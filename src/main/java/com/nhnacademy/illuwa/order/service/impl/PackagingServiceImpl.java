package com.nhnacademy.illuwa.order.service.impl;

import com.nhnacademy.illuwa.order.client.PackagingClient;
import com.nhnacademy.illuwa.order.dto.command.create.PackagingRequest;
import com.nhnacademy.illuwa.order.dto.query.info.PackagingResponse;
import com.nhnacademy.illuwa.order.service.PackagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PackagingServiceImpl implements PackagingService {

    private final PackagingClient packagingClient;

    @Override
    public List<PackagingResponse> getAllPackagingOptions() {
        return packagingClient.getAll();
    }

    @Override
    public void createPackaging(PackagingRequest dto) {
        packagingClient.create(dto);
    }
}

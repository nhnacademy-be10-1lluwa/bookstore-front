package com.nhnacademy.illuwa.auth.service;

import com.nhnacademy.illuwa.auth.client.InactiveVerificationServiceClient;
import com.nhnacademy.illuwa.auth.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Service
public class InactiveVerificationService {
    private final InactiveVerificationServiceClient inactiveVerificationServiceClient;

    public InactiveCheckResponse getInactiveMemberInfo(@RequestBody SendVerificationRequest request){
        return inactiveVerificationServiceClient.getInactiveMemberInfo(request);
    }
}

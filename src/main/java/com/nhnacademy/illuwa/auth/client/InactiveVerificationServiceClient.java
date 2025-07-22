package com.nhnacademy.illuwa.auth.client;

import com.nhnacademy.illuwa.auth.dto.message.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service", url = "${api.base-url}", contextId = "userClientForInactiveMember")
public interface InactiveVerificationServiceClient {

    @PostMapping("/api/members/inactive-verifications")
    Boolean sendCode(SendVerificationRequest req);

    @PostMapping("/api/members/inactive-verifications/verify")
    Boolean verify(VerifyCodeRequest req);
}

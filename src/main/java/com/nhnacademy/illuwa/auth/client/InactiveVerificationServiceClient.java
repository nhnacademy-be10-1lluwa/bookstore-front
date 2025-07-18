package com.nhnacademy.illuwa.auth.client;

import com.nhnacademy.illuwa.auth.dto.message.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "${api.base-url}", contextId = "userClientForInactiveMember")
public interface InactiveVerificationServiceClient {
    // 회원 휴면상태 체크
    @PostMapping("/api/members/inactive/check-status")
    InactiveCheckResponse getInactiveMemberInfo(@RequestBody SendVerificationRequest request);

    @PostMapping("/api/members/inactive/code")
    SendMessageResponse sendCode(SendVerificationRequest req);

    @PostMapping("/api/members/inactive/verification")
    VerifyCodeResponse verify(VerifyCodeRequest req);
}

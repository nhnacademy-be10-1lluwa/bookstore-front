package com.nhnacademy.illuwa.auth.client;

import com.nhnacademy.illuwa.auth.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "${api.base-url}", contextId = "userClientForInactiveMember")
public interface InactiveVerificationServiceClient {
    // 회원 휴면상태 체크
    @PostMapping("/api/members/check-status")
    InactiveCheckResponse getInactiveMemberInfo(@RequestBody SendVerificationRequest request);
}

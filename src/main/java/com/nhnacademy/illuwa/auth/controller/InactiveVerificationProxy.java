package com.nhnacademy.illuwa.auth.controller;

import com.nhnacademy.illuwa.auth.client.InactiveVerificationServiceClient;
import com.nhnacademy.illuwa.auth.dto.SendMessageResponse;
import com.nhnacademy.illuwa.auth.dto.SendVerificationRequest;
import com.nhnacademy.illuwa.auth.dto.VerifyCodeRequest;
import com.nhnacademy.illuwa.auth.dto.VerifyCodeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/proxy/members/inactive/verification")
public class InactiveVerificationProxy {

    private final InactiveVerificationServiceClient inactiveVerificationServiceClient;

    /** 인증번호 발송 */
    @PostMapping
    public SendMessageResponse send(@RequestBody SendVerificationRequest req) {
        return inactiveVerificationServiceClient.sendCode(req);
    }

    /** 인증번호 검증 */
    @PostMapping("/verify")
    public VerifyCodeResponse verify(@RequestBody VerifyCodeRequest req) {
        return inactiveVerificationServiceClient.verify(req);
    }
}

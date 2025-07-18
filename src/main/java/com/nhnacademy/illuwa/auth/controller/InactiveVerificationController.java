package com.nhnacademy.illuwa.auth.controller;

import com.nhnacademy.illuwa.auth.client.InactiveVerificationServiceClient;
import com.nhnacademy.illuwa.auth.dto.message.SendMessageResponse;
import com.nhnacademy.illuwa.auth.dto.message.SendVerificationRequest;
import com.nhnacademy.illuwa.auth.dto.message.VerifyCodeRequest;
import com.nhnacademy.illuwa.auth.dto.message.VerifyCodeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/proxy/members/inactive")
public class InactiveVerificationController {

    private final InactiveVerificationServiceClient inactiveVerificationServiceClient;

    /** 인증번호 발송 */
    @PostMapping("/code")
    public SendMessageResponse send(@RequestBody SendVerificationRequest req) {
        return inactiveVerificationServiceClient.sendCode(req);
    }

    /** 인증번호 검증 */
    @PostMapping("/verification")
    public VerifyCodeResponse verify(@RequestBody VerifyCodeRequest req) {
        return inactiveVerificationServiceClient.verify(req);
    }
}

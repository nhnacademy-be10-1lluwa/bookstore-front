package com.nhnacademy.illuwa.auth.controller;

import com.nhnacademy.illuwa.auth.client.InactiveVerificationServiceClient;
import com.nhnacademy.illuwa.auth.dto.message.SendVerificationRequest;
import com.nhnacademy.illuwa.auth.dto.message.VerifyCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/verifications/inactive")
public class InactiveVerificationController {

    private final InactiveVerificationServiceClient inactiveVerificationServiceClient;

    /** 인증번호 발송 */
    @PostMapping("/code")
    public void send(@RequestBody SendVerificationRequest req) {
        inactiveVerificationServiceClient.sendCode(req);
    }

    /** 인증번호 검증 */
    @PostMapping("/verify")
    public void verify(@RequestBody VerifyCodeRequest req) {
        inactiveVerificationServiceClient.verify(req);
    }
}

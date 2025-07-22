package com.nhnacademy.illuwa.auth.controller;

import com.nhnacademy.illuwa.auth.client.InactiveVerificationServiceClient;
import com.nhnacademy.illuwa.auth.dto.message.SendVerificationRequest;
import com.nhnacademy.illuwa.auth.dto.message.VerifyCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class InactiveVerificationController {

    private final InactiveVerificationServiceClient inactiveVerificationServiceClient;

    /** 인증번호 발송 */
    @ResponseBody
    @PostMapping("/verifications/inactive/code")
    public ResponseEntity<Boolean> send(@RequestBody SendVerificationRequest req) {
        return ResponseEntity.ok(inactiveVerificationServiceClient.sendCode(req));
    }

    /** 인증번호 검증 */
    @ResponseBody
    @PostMapping("/verifications/inactive/verify")
    public ResponseEntity<Boolean> verify(@RequestBody VerifyCodeRequest req) {
        return ResponseEntity.ok(inactiveVerificationServiceClient.verify(req));
    }
}

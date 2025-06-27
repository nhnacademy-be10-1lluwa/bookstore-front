package com.nhnacademy.illuwa.auth.controller;

import com.nhnacademy.illuwa.auth.dto.OauthTokenResponse;
import com.nhnacademy.illuwa.auth.service.OauthLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/oauth")
public class OauthCallbackController {
    private final OauthLoginService oauthLoginService;

    @GetMapping("/callback")
    public ResponseEntity<OauthTokenResponse> callback(@RequestParam("code") String code,
                                @RequestParam("state") String state) {
        return ResponseEntity.ok(oauthLoginService.loginWithPayco(code,state));
    }
}

package com.nhnacademy.illuwa.auth.client;

import com.nhnacademy.illuwa.auth.dto.OauthTokenResponse;
import com.nhnacademy.illuwa.auth.dto.PaycoCodeRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", url = "${api.base-url}")
public interface OauthServiceClient {
    @PostMapping("/auth/oauth/payco/token")
    OauthTokenResponse exchangeCodeForToken(@RequestBody PaycoCodeRequest request);
}

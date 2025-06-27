package com.nhnacademy.illuwa.auth.client;

import com.nhnacademy.illuwa.auth.dto.OauthTokenResponse;
import com.nhnacademy.illuwa.auth.dto.PaycoCodeRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name = "auth-service", url = "${auth-service.url}")
public interface OauthServiceClient {
    @PostMapping("/oauth/payco/token")
    OauthTokenResponse exchangeCodeForToken(@RequestBody PaycoCodeRequest request);
}

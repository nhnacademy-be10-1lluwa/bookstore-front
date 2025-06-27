package com.nhnacademy.illuwa.auth.service.impl;

import com.nhnacademy.illuwa.auth.dto.OauthTokenResponse;
import com.nhnacademy.illuwa.auth.dto.PaycoCodeRequest;
import com.nhnacademy.illuwa.auth.service.OauthLoginService;
import com.nhnacademy.illuwa.auth.client.OauthServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthLoginServiceImpl implements OauthLoginService {
    private final OauthServiceClient oauthServiceClient;

    public OauthTokenResponse loginWithPayco(String code, String state){
        PaycoCodeRequest request = new PaycoCodeRequest(code, state);
        return oauthServiceClient.exchangeCodeForToken(request);
    }
}

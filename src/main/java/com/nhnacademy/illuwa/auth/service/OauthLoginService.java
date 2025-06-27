package com.nhnacademy.illuwa.auth.service;

import com.nhnacademy.illuwa.auth.dto.OauthTokenResponse;

public interface OauthLoginService {
    OauthTokenResponse loginWithPayco(String code, String state);
}

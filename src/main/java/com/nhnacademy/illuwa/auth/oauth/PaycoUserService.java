package com.nhnacademy.illuwa.auth.oauth;

import com.nhnacademy.illuwa.auth.client.PaycoUserInfoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class PaycoUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final PaycoUserInfoClient paycoUserInfoClient;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2AccessToken accessToken = userRequest.getAccessToken();
        String tokenValue = accessToken.getTokenValue();

        Map<String, Object> attributes = paycoUserInfoClient.getUserInfo(tokenValue);

        String id = (String) attributes.get("idNo");
        if(id == null) {
            throw new OAuth2AuthenticationException("PAYCO 회원번호(idNo)가 없습니다.");
        }

        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_PAYCO"));

        return new DefaultOAuth2User(authorities, attributes, "idNo");
    }
}


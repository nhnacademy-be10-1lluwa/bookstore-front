package com.nhnacademy.illuwa.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialLoginRequest {
    private String provider;
    private String providerId;
    private Map<String, Object> attributes;

    public static SocialLoginRequest of(String provider, OAuth2User oauth2User) {
        return new SocialLoginRequest(
                provider,
                oauth2User.getName(),
                oauth2User.getAttributes()
        );
    }
}

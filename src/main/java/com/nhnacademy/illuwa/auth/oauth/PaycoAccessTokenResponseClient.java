package com.nhnacademy.illuwa.auth.oauth;

import org.springframework.http.*;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Component
public class PaycoAccessTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "authorization_code");
        form.add("code", request.getAuthorizationExchange().getAuthorizationResponse().getCode());
        form.add("redirect_uri", request.getAuthorizationExchange().getAuthorizationRequest().getRedirectUri());
        form.add("client_id", request.getClientRegistration().getClientId());
        form.add("client_secret", request.getClientRegistration().getClientSecret());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(form, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                request.getClientRegistration().getProviderDetails().getTokenUri(),
                HttpMethod.POST,
                entity,
                Map.class
        );

        Map<String, Object> body = response.getBody();
        if(body == null || body.get("access_token") == null) {
            throw new OAuth2AuthenticationException("Empty or invalid token response from PAYCO");
        }

        String accessToken = (String) body.get("access_token");
        String refreshToken = (String) body.get("refresh_token");
        String tokenTypeValue = (String) body.getOrDefault("token_type", "Bearer");
        OAuth2AccessToken.TokenType tokenType = "Bearer".equalsIgnoreCase(tokenTypeValue) ? OAuth2AccessToken.TokenType.BEARER : null;

        long expiresIn = 3600L;
        Object rawExpires = body.get("expires_in");
        if(rawExpires instanceof String) {
            expiresIn = Long.parseLong((String) rawExpires);
        } else if(rawExpires instanceof Number) {
            expiresIn = ((Number) rawExpires).longValue();
        }

        return OAuth2AccessTokenResponse.withToken(accessToken)
                .tokenType(tokenType)
                .expiresIn(expiresIn)
                .refreshToken(refreshToken)
                .scopes(Set.of("profile"))
                .build();
    }
}

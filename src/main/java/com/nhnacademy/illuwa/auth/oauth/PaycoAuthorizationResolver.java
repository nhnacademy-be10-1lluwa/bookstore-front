package com.nhnacademy.illuwa.auth.oauth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PaycoAuthorizationResolver implements OAuth2AuthorizationRequestResolver {

    private final OAuth2AuthorizationRequestResolver delegate;

    public PaycoAuthorizationResolver(ClientRegistrationRepository repo) {
        this.delegate = new DefaultOAuth2AuthorizationRequestResolver(repo, "/oauth2/authorization");
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        return customize(delegate.resolve(request));
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        return customize(delegate.resolve(request, clientRegistrationId));
    }
    
    private OAuth2AuthorizationRequest customize(OAuth2AuthorizationRequest authRequest) {
        if(authRequest == null) {
            return null;
        }

        String registrationId = (String) authRequest.getAttributes().get(OAuth2ParameterNames.REGISTRATION_ID);
        if("payco".equals(registrationId)) {
            Map<String, Object> extra = new HashMap<>(authRequest.getAdditionalParameters());
            extra.put("serviceProviderCode", "FRIENDS");
            extra.put("userLocale", "ko_KR");

            authRequest = OAuth2AuthorizationRequest.from(authRequest)
                    .additionalParameters(extra)
                    .build();
        }
        return authRequest;
    }
}

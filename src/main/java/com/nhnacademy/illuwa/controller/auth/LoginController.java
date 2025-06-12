package com.nhnacademy.illuwa.controller.auth;

import com.nhnacademy.illuwa.dto.auth.MemberLoginRequest;
import com.nhnacademy.illuwa.dto.auth.MemberLoginResponse;
import com.nhnacademy.illuwa.exception.ApiRequestException;
import com.nhnacademy.illuwa.exception.LoginRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginController {

    private final RestTemplate restTemplate;

    public LoginController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginSubmit(MemberLoginRequest memberLoginRequest) {
        String apiUrl = "http://api서버주소/api/login";

        try {
            ResponseEntity<MemberLoginResponse> response = restTemplate.postForEntity(apiUrl, memberLoginRequest, MemberLoginResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody().isSuccess()) {
                return "redirect:/";
            } else {
                throw new LoginRequestException("로그인 정보가 올바르지 않습니다.");
            }
        } catch (RestClientException e) {
            throw new ApiRequestException("서버와 통신 중 장애가 발생했습니다.");
        }
    }
}

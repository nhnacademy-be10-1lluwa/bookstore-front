package com.nhnacademy.illuwa.controller.auth;

import com.nhnacademy.illuwa.dto.auth.MemberLoginRequest;
import com.nhnacademy.illuwa.dto.auth.MemberLoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String loginSubmit(MemberLoginRequest memberLoginRequest, Model model) {
        String apiUrl = "http://api서버주소/api/login";

        try {
            ResponseEntity<MemberLoginResponse> response = restTemplate.postForEntity(apiUrl, memberLoginRequest, MemberLoginResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody().isSuccess()) {
                return "redirect:/";
            } else {
                model.addAttribute("error", "로그인 정보가 올바르지 않습니다.");
                return "auth/login";
            }
        } catch (RestClientException e) {
            model.addAttribute("error", "서버와 통신 중 문제가 발생했습니다.");
            return "auth/login";
        }
    }
}

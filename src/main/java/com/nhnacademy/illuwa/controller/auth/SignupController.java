package com.nhnacademy.illuwa.controller.auth;

import com.nhnacademy.illuwa.dto.auth.MemberLoginRequest;
import com.nhnacademy.illuwa.dto.auth.MemberLoginResponse;
import com.nhnacademy.illuwa.dto.member.MemberRegisterRequest;
import com.nhnacademy.illuwa.dto.member.MemberRegisterResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Controller
public class SignupController {

    private final RestTemplate restTemplate;

    public SignupController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/signup")
    public String signup() {
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String loginSubmit(MemberRegisterRequest memberRegisterRequest, Model model) {
        String apiUrl = "http://api서버주소/api/login";

        try {
            ResponseEntity<MemberRegisterResponse> response = restTemplate.postForEntity(apiUrl, memberRegisterRequest, MemberRegisterResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody().isSuccess()) {
                return "redirect:/";
            } else {
                model.addAttribute("error", "회원 정보가 올바르지 않습니다.");
                return "auth/signup";
            }
        } catch (RestClientException e) {
            model.addAttribute("error", "서버와 통신 중 문제가 발생했습니다.");
            return "auth/signup";
        }
    }
}

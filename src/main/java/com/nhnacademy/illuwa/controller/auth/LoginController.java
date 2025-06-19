package com.nhnacademy.illuwa.controller.auth;

import com.nhnacademy.illuwa.dto.auth.MemberLoginRequest;
import com.nhnacademy.illuwa.dto.member.MemberResponse;
import com.nhnacademy.illuwa.enums.Role;
import com.nhnacademy.illuwa.service.auth.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginSubmit(MemberLoginRequest memberLoginRequest) {
        MemberResponse memberLoginResponse= loginService.sendLogin(memberLoginRequest);
        if(memberLoginResponse.getRole().equals(Role.ADMIN)) {
            return "redirect:/admin/admin_home";
        }
        return "redirect:/";
    }
}

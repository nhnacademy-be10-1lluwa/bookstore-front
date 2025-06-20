package com.nhnacademy.illuwa.member.controller;

import com.nhnacademy.illuwa.member.dto.MemberLoginRequest;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.enums.Role;
import com.nhnacademy.illuwa.member.serivce.LoginService;
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

package com.nhnacademy.illuwa.auth.controller;

import com.nhnacademy.illuwa.auth.client.AuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final AuthClient authClient;

    @GetMapping("/fast")
    public String testApi() {
        authClient.testFast();
        return "redirect:/";
    }
}

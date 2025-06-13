package com.nhnacademy.illuwa.controller.auth;

import com.nhnacademy.illuwa.dto.auth.MemberLoginRequest;
import com.nhnacademy.illuwa.exception.ApiRequestException;
import com.nhnacademy.illuwa.exception.LoginRequestException;
import com.nhnacademy.illuwa.service.auth.LoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @Test
    void signupPage_get() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"));
    }

    @Test
    void loginSubmit_success() throws Exception {
        MemberLoginRequest request = new MemberLoginRequest(
                "test@example.com", "1234"
        );

        mockMvc.perform(post("/login")
                        .param("email", request.getEmail())
                        .param("password", request.getPassword())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(loginService).sendLogin(any(MemberLoginRequest.class));
    }

    @Test
    void loginSubmit_fail_loginRequest() throws Exception {
        doThrow(new LoginRequestException("로그인 실패")).when(loginService).sendLogin(any());

        mockMvc.perform(post("/login")
                        .param("email", "a@a.com")
                        .param("password", "1234"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void loginSubmit_fail_apiRequest() throws Exception {
        doThrow(new ApiRequestException("서버 연결 실패")).when(loginService).sendLogin(any());

        mockMvc.perform(post("/login")
                        .param("email", "a@a.com")
                        .param("password", "1234"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("error"));
    }
}

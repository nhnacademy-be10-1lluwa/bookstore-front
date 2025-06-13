package com.nhnacademy.illuwa.controller.auth;

import com.nhnacademy.illuwa.dto.member.MemberRegisterRequest;
import com.nhnacademy.illuwa.enums.Role;
import com.nhnacademy.illuwa.exception.ApiRequestException;
import com.nhnacademy.illuwa.exception.SignupRequestException;
import com.nhnacademy.illuwa.service.auth.impl.SignupServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SignupController.class)
public class SignupControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignupServiceImpl signupService;

    @Test
    void signupPage_get() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/signup"));
    }

    @Test
    void signupSubmit_success() throws Exception {
        MemberRegisterRequest request = new MemberRegisterRequest(
                "홍길동", "19900101", "test@example.com", "1234", Role.USER, "01012345678"
        );

        mockMvc.perform(post("/signup")
                        .param("name", request.getName())
                        .param("birth", request.getBirth())
                        .param("email", request.getEmail())
                        .param("password", request.getPassword())
                        .param("role", request.getRole().name())
                        .param("phoneNumber", request.getPhoneNumber())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/auth/login"));

        verify(signupService).sendSignup(any(MemberRegisterRequest.class));
    }

    @Test
    void signupSubmit_fail_signupRequest() throws Exception {
        doThrow(new SignupRequestException("가입 실패")).when(signupService).sendSignup(any());

        mockMvc.perform(post("/signup")
                        .param("name", "a")
                        .param("birth", "19900101")
                        .param("email", "a@a.com")
                        .param("password", "1234")
                        .param("role", "USER")
                        .param("phoneNumber", "01000000000"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/signup"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void signupSubmit_fail_apiRequest() throws Exception {
        doThrow(new ApiRequestException("서버 연결 실패")).when(signupService).sendSignup(any());

        mockMvc.perform(post("/signup")
                        .param("name", "a")
                        .param("birth", "19900101")
                        .param("email", "a@a.com")
                        .param("password", "1234")
                        .param("role", "USER")
                        .param("phoneNumber", "01000000000"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("error"));
    }
}

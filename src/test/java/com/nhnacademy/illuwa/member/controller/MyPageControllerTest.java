package com.nhnacademy.illuwa.member.controller;

import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.enums.Role;
import com.nhnacademy.illuwa.member.enums.Status;
import com.nhnacademy.illuwa.member.service.MemberService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MyPageController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MyPageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    private MemberResponse createMockMember() {
        return MemberResponse.builder()
                .memberId(1L)
                .paycoId("payco123")
                .name("홍길동")
                .birth(LocalDate.of(1990, 1, 1))
                .email("hong@sample.com")
                .role(Role.USER)
                .contact("010-1234-5678")
                .gradeName("일반")
                .point(BigDecimal.valueOf(10000))
                .status(Status.ACTIVE)
                .createdAt(LocalDateTime.now().minusYears(1))
                .lastLoginAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("GET /mypage: 마이페이지 정상 접근 시 뷰 및 모델 반환")
    void myPageTest() throws Exception {
        // given
        MemberResponse mockMember = createMockMember();
        when(memberService.getMember()).thenReturn(mockMember);

        // when & then
        mockMvc.perform(get("/mypage"))
                .andExpect(status().isOk())
                .andExpect(view().name("mypage/mypage"))
                .andExpect(model().attributeExists("member"))
                .andExpect(model().attribute("member", mockMember));

        verify(memberService, times(1)).getMember();
    }
}

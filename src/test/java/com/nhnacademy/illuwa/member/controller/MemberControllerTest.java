package com.nhnacademy.illuwa.member.controller;

import com.nhnacademy.illuwa.common.exception.BackendApiException;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.dto.MemberUpdateRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    private MemberResponse createMember() {
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
    @DisplayName("GET /my-info: 마이페이지 정보 조회 뷰 반환")
    void memberInfoView() throws Exception {
        MemberResponse member = createMember();
        when(memberService.getMember()).thenReturn(member);

        mockMvc.perform(get("/my-info"))
                .andExpect(status().isOk())
                .andExpect(view().name("mypage/section/myinfo"))
                .andExpect(model().attributeExists("member"))
                .andExpect(model().attributeExists("form"))
                .andExpect(model().attribute("mode", "view"));
    }

    @Test
    @DisplayName("POST /my-info/update: 회원정보 정상 수정 시 리다이렉트 및 메시지")
    void updateMember_success() throws Exception {
        when(memberService.updateMember(any(MemberUpdateRequest.class))).thenReturn(any(MemberResponse.class));

        mockMvc.perform(post("/my-info/update")
                        .param("name", "홍길동")
                        .param("currentPassword", "currentPass123")
                        .param("password", "Test1234!")
                        .param("contact", "010-1234-5678"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/my-info"))
                .andExpect(flash().attribute("message", "회원정보가 정상적으로 수정되었습니다!"));
    }

    @Test
    @DisplayName("POST /my-info/update: 회원정보 수정 실패 시 에러 플래시 메시지와 리다이렉트")
    void updateMember_fail() throws Exception {
        doThrow(new BackendApiException("에러메시지", "ERROR_CODE", 400))
                .when(memberService).updateMember(any(MemberUpdateRequest.class));

        MemberUpdateRequest expectedForm = MemberUpdateRequest.builder()
                .name("홍길동")
                .currentPassword("wrongPass")
                .password("Test1234!")
                .contact("010-1234-5678")
                .build();

        mockMvc.perform(post("/my-info/update")
                        .param("name", "홍길동")
                        .param("currentPassword", "wrongPass")
                        .param("password", "Test1234!")
                        .param("contact", "010-1234-5678"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/my-info"))
                .andExpect(flash().attribute("mode", "edit"))
                .andExpect(flash().attribute("form", expectedForm))
                .andExpect(flash().attribute("error", "에러메시지"));
    }

    @Test
    @DisplayName("POST /my-info/delete: 회원 삭제 정상 응답")
    void deleteMember_success() throws Exception {
        doNothing().when(memberService).deleteMember();

        mockMvc.perform(post("/my-info/delete"))
                .andExpect(status().isOk());
        verify(memberService, times(1)).deleteMember();
    }

}

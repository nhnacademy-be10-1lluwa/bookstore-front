package com.nhnacademy.illuwa.member.controller;

import com.nhnacademy.illuwa.common.controller_advice.CategoryControllerAdvice;
import com.nhnacademy.illuwa.grade.enums.GradeName;
import com.nhnacademy.illuwa.member.service.AdminMemberService;
import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.enums.Role;
import com.nhnacademy.illuwa.member.enums.Status;
import com.nhnacademy.illuwa.pointhistory.dto.PointHistoryResponse;
import com.nhnacademy.illuwa.pointhistory.enums.PointHistoryType;
import com.nhnacademy.illuwa.pointhistory.enums.PointReason;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AdminMemberController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CategoryControllerAdvice.class})
        })
@AutoConfigureMockMvc(addFilters = false)
public class AdminMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @MockBean
    private AdminMemberService adminMemberService;

    @Test
    @DisplayName("회원 관리 페이지 뷰 반환")
    public void adminMemberManagementTest() throws Exception {
        mockMvc.perform(get("/admin/member-management"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/member/member_management"));
    }

    @Test
    @DisplayName("회원 리스트 페이지 뷰 및 모델 데이터 반환")
    public void memberListTest() throws Exception {
        List<MemberResponse> members = List.of(
                MemberResponse.builder()
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
                        .build(),
                MemberResponse.builder()
                        .memberId(2L)
                        .paycoId("payco456")
                        .name("임꺽정")
                        .birth(LocalDate.of(1990, 2, 2))
                        .email("imggukjung@sample.com")
                        .role(Role.USER)
                        .contact("010-5678-1234")
                        .gradeName("일반")
                        .point(BigDecimal.valueOf(10000))
                        .status(Status.ACTIVE)
                        .createdAt(LocalDateTime.now().minusYears(1))
                        .lastLoginAt(LocalDateTime.now())
                        .build()
        );
        PageResponse<MemberResponse> pageResponse = new PageResponse<>(
                members, 5, 50L, 0, 5, false, true
        );
        when(adminMemberService.getPagedMemberList(any(), eq(0), eq(5))).thenReturn(pageResponse);

        mockMvc.perform(get("/admin/member-list")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/member/member_list"))
                .andExpect(model().attributeExists(
                        "memberList", "memberCount", "currentPage", "pageSize", "totalPages", "lastPageIndex"
                ));
    }

    @Test
    @DisplayName("등급별 보너스 포인트 지급 후 등급 리스트 페이지로 리다이렉트")
    public void giveBonusTest() throws Exception {
        GradeName grade = GradeName.BASIC;
        BigDecimal point = BigDecimal.valueOf(1000);

        List<PointHistoryResponse> mockResponse = List.of(
                new PointHistoryResponse(
                1L,
                        PointHistoryType.EARN,
                        PointReason.JOIN,
                        point,
                        BigDecimal.valueOf(5000),
                        LocalDateTime.now()
                )
        );

        when(adminMemberService.givePointToGrade(eq(grade), eq(point))).thenReturn(mockResponse);

        mockMvc.perform(post("/admin/point-bonus")
                        .param("grade", grade.name())
                        .param("point", point.toPlainString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/member-list?grade=" + grade.name()));

        verify(adminMemberService, times(1)).givePointToGrade(grade, point);
    }

    @Test
    @DisplayName("회원 주문 리스트 페이지 뷰 반환")
    public void memberOrderListTest() throws Exception {
        mockMvc.perform(get("/admin/member-orderlist"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/member/member_orderlist"));
    }

    @Test
    @DisplayName("비회원 주문 리스트 페이지 뷰 반환")
    public void guestOrderListTest() throws Exception {
        mockMvc.perform(get("/admin/guest-orderlist"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/member/guest_orderlist"));
    }
}

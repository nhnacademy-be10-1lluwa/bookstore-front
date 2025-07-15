package com.nhnacademy.illuwa.member.controller;

import com.nhnacademy.illuwa.member.service.AdminMemberService;
import com.nhnacademy.illuwa.common.dto.PageResponse;
import com.nhnacademy.illuwa.member.dto.MemberResponse;
import com.nhnacademy.illuwa.member.enums.Role;
import com.nhnacademy.illuwa.member.enums.Status;
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
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminMemberController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminMemberService adminMemberService;

    @Test
    @DisplayName("회원 관리 페이지 뷰 반환")
    void adminMemberManagementTest() throws Exception {
        mockMvc.perform(get("/admin/member-management"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/member/member_management"));
    }

//    @Test
//    @DisplayName("회원 리스트 페이지 뷰 및 모델 데이터 반환")
//    void memberListTest() throws Exception {
//        List<MemberResponse> members = List.of(
//                MemberResponse.builder()
//                        .memberId(1L)
//                        .paycoId("payco123")
//                        .name("홍길동")
//                        .birth(LocalDate.of(1990, 1, 1))
//                        .email("hong@sample.com")
//                        .role(Role.USER)
//                        .contact("010-1234-5678")
//                        .gradeName("일반")
//                        .point(BigDecimal.valueOf(10000))
//                        .status(Status.ACTIVE)
//                        .createdAt(LocalDateTime.now().minusYears(1))
//                        .lastLoginAt(LocalDateTime.now())
//                        .build(),
//                MemberResponse.builder()
//                        .memberId(2L)
//                        .paycoId("payco456")
//                        .name("임꺽정")
//                        .birth(LocalDate.of(1990, 2, 2))
//                        .email("imggukjung@sample.com")
//                        .role(Role.USER)
//                        .contact("010-5678-1234")
//                        .gradeName("일반")
//                        .point(BigDecimal.valueOf(10000))
//                        .status(Status.ACTIVE)
//                        .createdAt(LocalDateTime.now().minusYears(1))
//                        .lastLoginAt(LocalDateTime.now())
//                        .build()
//        );
//        PageResponse<MemberResponse> pageResponse = new PageResponse<>(
//                members, 5, 50L, 0, 5, false, true
//        );
//        when(adminMemberService.getPagedMemberList(0, 5)).thenReturn(pageResponse);
//
//        mockMvc.perform(get("/admin/member-list")
//                        .param("page", "0")
//                        .param("size", "5"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("admin/member/member_list"))
//                .andExpect(model().attributeExists(
//                        "memberList", "memberCount", "currentPage", "pageSize", "totalPages", "lastPageIndex"
//                ));
//    }

    @Test
    @DisplayName("회원 주문 리스트 페이지 뷰 반환")
    void memberOrderListTest() throws Exception {
        mockMvc.perform(get("/admin/member-orderlist"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/member/member_orderlist"));
    }

    @Test
    @DisplayName("비회원 주문 리스트 페이지 뷰 반환")
    void guestOrderListTest() throws Exception {
        mockMvc.perform(get("/admin/guest-orderlist"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/member/guest_orderlist"));
    }
}

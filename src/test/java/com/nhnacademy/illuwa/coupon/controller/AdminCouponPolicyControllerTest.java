package com.nhnacademy.illuwa.coupon.controller;

import com.nhnacademy.illuwa.admin.controller.AdminPolicyController;
import com.nhnacademy.illuwa.common.controller_advice.CategoryControllerAdvice;
import com.nhnacademy.illuwa.coupon.dto.couponPolicy.CouponPolicyFrom;
import com.nhnacademy.illuwa.coupon.dto.couponPolicy.CouponPolicyResponse;
import com.nhnacademy.illuwa.coupon.dto.couponPolicy.CouponPolicyUpdateRequest;
import com.nhnacademy.illuwa.coupon.service.CouponPolicyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AdminPolicyController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {com.nhnacademy.illuwa.common.controller_advice.CategoryControllerAdvice.class})})

@AutoConfigureMockMvc(addFilters = false)
class AdminCouponPolicyControllerTest {

    @MockBean
    private CouponPolicyService couponPolicyService;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("쿠폰 정책 목록 페이지 반환")
    void couponPolicyListTest() throws Exception {
        List<CouponPolicyResponse> couponPolicy = List.of(
                CouponPolicyResponse.builder()
                        .id(1L)
                        .code("TEST_CODE")
                        .discountType("AMOUNT")
                        .minOrderAmount(BigDecimal.valueOf(20000))
                        .discountAmount(BigDecimal.valueOf(5000))
                        .status("INACTIVE")
                        .build()
        );

        when(couponPolicyService.getAllPolices()).thenReturn(couponPolicy);

        mockMvc.perform(get("/admin/policy/coupon"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/policy/coupon_policy_view_list"))
                .andExpect(model().attribute("couponPolicies", hasSize(1)))
                .andExpect(model().attribute("couponPolicies", couponPolicy));
    }

    @Test
    @DisplayName("쿠폰 정책 등록 폼 반환")
    void couponPolicyCreateFromTest() throws Exception {
        mockMvc.perform(get("/admin/policy/coupon/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/policy/coupon_policy_create"))
                .andExpect(model().attributeExists("policyFrom"));
    }

    @Test
    @DisplayName("쿠폰 정책 폼 데이터 전송 -> 성공")
    void registerCouponPolicyTest() throws Exception {
        mockMvc.perform(post("/admin/policy/coupon/create")
                        .param("code", "TEST_CODE")
                        .param("discountType", "PERCENT")
                        .param("minOrderAmount", "20000")
                        .param("discountPercent", "30")
                        .param("maxDiscountAmount", "10000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/policy/coupon"));

        // 서비스 등록 메서드가 호출 됐는지 검증
        verify(couponPolicyService, times(1)).createCouponPolicy(any(CouponPolicyFrom.class));
    }

    @Test
    @DisplayName("쿠폰 정책 수정 페이지 반환")
    void showUpdateFromTest() throws Exception {
        CouponPolicyResponse response = CouponPolicyResponse.builder()
                .id(1L)
                .code("TEST_CODE")
                .discountType("AMOUNT")
                .minOrderAmount(BigDecimal.valueOf(20000))
                .discountAmount(BigDecimal.valueOf(5000))
                .status("INACTIVE")
                .build();

        when(couponPolicyService.getPolicyByCode("TEST_CODE")).thenReturn(response);

        mockMvc.perform(get("/admin/policy/coupon/TEST_CODE/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/policy/coupon_policy_update"))
                .andExpect(model().attribute("policy", response));
    }

    @Test
    @DisplayName("쿠폰 정책 수정 데이터 전송")
    void updatePolicyTest() throws Exception {
        mockMvc.perform(post("/admin/policy/coupon/TEST_CODE/update")
                        .param("minOrderAmount", "10000")
                        .param("discountAmount", "3000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/policy/coupon"));

        verify(couponPolicyService, times(1)).updateCouponPolicy(eq("TEST_CODE"), any(CouponPolicyUpdateRequest.class));
    }

    @Test
    @DisplayName("쿠폰 정책 삭제 (= 비활성화)")
    void deletePolicyTest() throws Exception {

        mockMvc.perform(post("/admin/policy/coupon/TEST_CODE/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/policy/coupon"));

        // 서비스 삭제 호출 검증
        verify(couponPolicyService, times(1)).deleteCouponPolicy("TEST_CODE");
    }

    @Test
    @DisplayName("정책 페이지 반환")
    void policyTest() throws Exception {
        mockMvc.perform(get("/admin/policy/policy"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/policy/policy"));
    }
}



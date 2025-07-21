package com.nhnacademy.illuwa.coupon.controller;

import com.nhnacademy.illuwa.admin.controller.AdminCouponController;
import com.nhnacademy.illuwa.admin.controller.AdminPolicyController;
import com.nhnacademy.illuwa.book.dto.BookDetailResponse;
import com.nhnacademy.illuwa.book.service.BookService;
import com.nhnacademy.illuwa.common.ca.CategoryControllerAdvice;
import com.nhnacademy.illuwa.coupon.dto.coupon.CouponForm;
import com.nhnacademy.illuwa.coupon.dto.coupon.CouponResponse;
import com.nhnacademy.illuwa.coupon.dto.coupon.CouponUpdateRequest;
import com.nhnacademy.illuwa.coupon.dto.couponPolicy.CouponPolicyResponse;
import com.nhnacademy.illuwa.coupon.service.CouponPolicyService;
import com.nhnacademy.illuwa.coupon.service.CouponService;
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
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = AdminCouponController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CategoryControllerAdvice.class})})

@AutoConfigureMockMvc(addFilters = false)
class AdminCouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CouponService couponService;

    @MockBean
    private CouponPolicyService couponPolicyService;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("쿠폰 목록 페이지 반환")
    void couponListTest() throws Exception {
        List<CouponResponse> coupons = List.of(
                CouponResponse.builder()
                        .id(1L)
                        .couponName("testCouponName")
                        .code("TEST_CODE")
                        .validFrom(LocalDate.now())
                        .validTo(LocalDate.now().plusMonths(1L))
                        .couponType("WELCOME")
                        .issueCount(BigDecimal.TWO)
                        .build(),
                CouponResponse.builder()
                        .id(2L)
                        .couponName("testCouponName2")
                        .code("TEST_CODE")
                        .validFrom(LocalDate.now())
                        .validTo(LocalDate.now().plusYears(1L))
                        .couponType("BIRTHDAY")
                        .issueCount(BigDecimal.ONE).build()
        );

        when(couponService.getAllCoupons()).thenReturn(coupons);

        mockMvc.perform(get("/admin/coupon/coupon"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/coupon/coupon_view_list"))
                .andExpect(model().attribute("coupons", hasSize(2)))
                .andExpect(model().attribute("coupons", coupons));
    }

    @Test
    @DisplayName("쿠폰 등록 폼 반환")
    void couponPolicyCreateFromTest() throws Exception {
        mockMvc.perform(get("/admin/coupon/coupon/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/coupon/coupon_create"))
                .andExpect(model().attributeExists("policyList"))
                .andExpect(model().attributeExists("bookList"))
                .andExpect(model().attributeExists("categoryList"))
                .andExpect(model().attributeExists("couponForm"));
    }

    @Test
    @DisplayName("쿠폰 등록 폼 전송")
    void registerCouponTest() throws Exception {
        mockMvc.perform(post("/admin/coupon/coupon/create")
                        .param("couponName", "테스트쿠폰이름")
                        .param("policyCode", "TEST_CODE")
                        .param("validFrom", "2025-07-21")
                        .param("validTo", "2026-07-21")
                        .param("couponType", "WELCOME")
                        .param("issueCount", "100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/coupon/coupon"));

        verify(couponService, times(1)).createCoupon(any(CouponForm.class));
    }

    @Test
    @DisplayName("쿠폰 수정 폼 반환")
    void showCouponUpdateFormTest() throws Exception {
        CouponResponse response = CouponResponse.builder()
                .id(1L)
                .couponName("testCouponName")
                .code("TEST_CODE")
                .validFrom(LocalDate.now())
                .validTo(LocalDate.now().plusMonths(1L))
                .couponType("WELCOME")
                .issueCount(BigDecimal.TWO)
                .build();

        when(couponService.getCouponById(1L)).thenReturn(response);

        mockMvc.perform(get("/admin/coupon/coupon/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/coupon/coupon_update"))
                .andExpect(model().attribute("coupon", response));
    }

    @Test
    @DisplayName("쿠폰 수정 데이터 전송")
    void updateCouponTest() throws Exception {
        mockMvc.perform(post("/admin/coupon/coupon/1/update")
                        .param("comment", "쿠폰 수량 추가 +50")
                        .param("issueCount", "50"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/coupon/coupon"));

        verify(couponService, times(1)).updateCoupon(eq(1L), any(CouponUpdateRequest.class));
    }

    @Test
    @DisplayName("쿠폰 삭제")
    void deleteCouponTest() throws Exception {
        mockMvc.perform(post("/admin/coupon/coupon/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/coupon/coupon"));

        verify(couponService, times(1)).deleteCoupon(1L);
    }
}

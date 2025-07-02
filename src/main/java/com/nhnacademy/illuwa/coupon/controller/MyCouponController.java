package com.nhnacademy.illuwa.coupon.controller;

import com.nhnacademy.illuwa.coupon.dto.MemberCouponResponse;
import com.nhnacademy.illuwa.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MyCouponController {
//    private final CouponService couponService;

    @GetMapping("/mycoupon")
    public String myCoupon(Model model) {
//        List<MemberCouponResponse> couponResponses = couponService.getCoupons("example");

        // 테스트용 List 실제 Api 작동시 삭제 후 위에 있는 couponResponses 사용하기
        List<MemberCouponResponse> couponResponses = Arrays.asList(
                new MemberCouponResponse(1L, "test", "test@test.com", "웰컴쿠폰", 1L, "WELCOME_DC50P", false, null,
                        LocalDateTime.of(2025, 7, 2, 10, 0), LocalDateTime.of(2025, 8, 1, 5, 5)),
                new MemberCouponResponse(2L, "test", "test@test.com", "잘가쿠폰", 2L, "WELCOME_DC50P", false, null,
                        LocalDateTime.of(2025, 3, 2, 3, 0), LocalDateTime.of(2025, 6, 1, 6, 6)),
                new MemberCouponResponse(3L, "test", "test@test.com", "복귀쿠폰", 3L, "WELCOME_DC50P", true, LocalDateTime.of(2025, 8, 1, 5, 5),
                        LocalDateTime.of(2025, 1, 2, 1, 0), LocalDateTime.of(2025, 9, 1, 8, 55)),
                new MemberCouponResponse(4L, "test", "test@test.com", "호구쿠폰", 4L, "WELCOME_DC50P", true, LocalDateTime.of(2025, 8, 1, 5, 5),
                        LocalDateTime.of(2025, 2, 2, 7, 0), LocalDateTime.of(2025, 10, 1, 7, 0))
        );

        List<MemberCouponResponse> availableCoupons = couponResponses.stream()
                .filter(coupon -> !coupon.isUsed())
                .toList();

        List<MemberCouponResponse> usedCoupons = couponResponses.stream()
                .filter(MemberCouponResponse::isUsed)
                .toList();

        model.addAttribute("availableCoupons", availableCoupons);
        model.addAttribute("usedCoupons", usedCoupons);

        return "user/mycoupon";
    }
}

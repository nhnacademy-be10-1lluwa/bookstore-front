package com.nhnacademy.illuwa.payment.controller;

import com.nhnacademy.illuwa.payment.client.PaymentServiceClient;
import com.nhnacademy.illuwa.payment.dto.PaymentConfirmRequest;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentServiceClient paymentServiceClient;

    @GetMapping("/success")
    public String handlePaymentSuccess(@RequestParam(name = "orderId") String orderNumber,
                                       @RequestParam(name = "paymentKey") String paymentKey,
                                       @RequestParam(name = "amount") int amount,
                                       RedirectAttributes redirectAttributes) {

        PaymentConfirmRequest request = new PaymentConfirmRequest(orderNumber, paymentKey, amount);

        try {
            paymentServiceClient.confirmPayment(request);
            redirectAttributes.addFlashAttribute("message", "결제가 완료되었습니다.");
            return "redirect:/order/complete/" + orderNumber;
        } catch (FeignException e) {
            redirectAttributes.addFlashAttribute("error", "결제 승인 실패: " + e.getMessage());
            return "redirect:/payment/fail";
        }
    }
}

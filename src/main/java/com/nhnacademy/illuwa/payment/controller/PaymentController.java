package com.nhnacademy.illuwa.payment.controller;

import com.nhnacademy.illuwa.payment.client.PaymentServiceClient;
import com.nhnacademy.illuwa.payment.dto.PaymentConfirmRequest;
import com.nhnacademy.illuwa.payment.dto.PaymentResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
                                       Model model) {

        PaymentConfirmRequest request = new PaymentConfirmRequest(orderNumber, paymentKey, amount);

        try {
            PaymentResponse paymentResponse = paymentServiceClient.confirmPayment(request);

            model.addAttribute("payment", paymentResponse);

            return "payment/success";
        } catch (FeignException e) {
            return "payment/fail";
        }
    }
}

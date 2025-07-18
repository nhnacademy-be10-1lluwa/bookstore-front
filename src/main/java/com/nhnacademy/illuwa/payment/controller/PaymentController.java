package com.nhnacademy.illuwa.payment.controller;

import com.nhnacademy.illuwa.order.client.OrderServiceClient;
import com.nhnacademy.illuwa.payment.client.PaymentServiceClient;
import com.nhnacademy.illuwa.payment.dto.PaymentConfirmRequest;
import com.nhnacademy.illuwa.payment.dto.PaymentResponse;
import com.nhnacademy.illuwa.payment.dto.PaymentRefundRequest;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentServiceClient paymentServiceClient;
    private final OrderServiceClient orderServiceClient;

    @GetMapping("/success")
    public String handlePaymentSuccess(@RequestParam(name = "orderId") String orderNumber,
                                       @RequestParam(name = "paymentKey") String paymentKey,
                                       @RequestParam(name = "amount") int amount,
                                       Model model) {

        PaymentConfirmRequest request = new PaymentConfirmRequest(orderNumber, paymentKey, amount);

        try {
            PaymentResponse paymentResponse = paymentServiceClient.confirmPayment(request);
            orderServiceClient.notificatePaymentSuccess(orderNumber);
            model.addAttribute("payment", paymentResponse);

            return "payment/success";
        } catch (FeignException e) {
            return "payment/fail";
        }
    }

    @PostMapping("/{orderNumber}/refund")
    public String refundOrder(@PathVariable String orderNumber, @RequestParam String cancelReason) {
        PaymentRefundRequest request = new PaymentRefundRequest(orderNumber, cancelReason);
        paymentServiceClient.requestRefund(request);
        orderServiceClient.cancelOrder(orderNumber);

        return "redirect:/order-list/";
    }
}

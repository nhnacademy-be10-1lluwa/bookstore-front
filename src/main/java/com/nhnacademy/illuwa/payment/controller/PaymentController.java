package com.nhnacademy.illuwa.payment.controller;

import com.nhnacademy.illuwa.order.client.CommonOrderClient;
import com.nhnacademy.illuwa.order.service.GuestOrderService;
import com.nhnacademy.illuwa.payment.client.PaymentServiceClient;
import com.nhnacademy.illuwa.payment.dto.PaymentConfirmRequest;
import com.nhnacademy.illuwa.payment.dto.PaymentRefundRequest;
import com.nhnacademy.illuwa.payment.dto.PaymentResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentServiceClient paymentServiceClient;
    private final CommonOrderClient commonOrderClient;

    @GetMapping("/success")
    public String handlePaymentSuccess(@RequestParam(name = "orderId") String orderNumber,
                                       @RequestParam(name = "paymentKey") String paymentKey,
                                       @RequestParam(name = "amount") int amount,
                                       Model model) {
        StopWatch sw =  new StopWatch("payment-success");
        sw.start("confirmPayment");

        PaymentConfirmRequest request = new PaymentConfirmRequest(orderNumber, paymentKey, amount);
        try {
            PaymentResponse paymentResponse = paymentServiceClient.confirmPayment(request);
            sw.stop();
            log.info("[/success] total={}ms detail=\n{}", sw.getTotalTimeMillis(), sw.prettyPrint());
            model.addAttribute("payment", paymentResponse);
            return "payment/success";
        } catch (FeignException e) {
            if(sw.isRunning())
                sw.stop();
            log.warn("[/success] failed after {}ms", sw.getTotalTimeMillis(), e);
            return "payment/fail";
        }
    }

    @PostMapping("/{orderNumber}/refund")
    public String refundOrder(@PathVariable String orderNumber, @RequestParam String cancelReason) {
        PaymentRefundRequest request = new PaymentRefundRequest(orderNumber, cancelReason);
        paymentServiceClient.requestRefund(request);
        commonOrderClient.cancelPayment(orderNumber);

        return "redirect:/orders";
    }
}

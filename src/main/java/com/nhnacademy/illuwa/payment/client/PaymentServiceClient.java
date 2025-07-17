package com.nhnacademy.illuwa.payment.client;

import com.nhnacademy.illuwa.payment.dto.PaymentConfirmRequest;
import com.nhnacademy.illuwa.payment.dto.PaymentResponse;
import com.nhnacademy.illuwa.payment.dto.PaymentRefundRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "${api.base-url}")
public interface PaymentServiceClient {

    @PostMapping("/api/payments/confirm")
    PaymentResponse confirmPayment(@RequestBody PaymentConfirmRequest request);

    @PostMapping("/api/payments/refund")
    PaymentResponse requestRefund(@RequestBody PaymentRefundRequest refundRequest);
}

package com.nhnacademy.illuwa.payment.client;

import com.nhnacademy.illuwa.payment.dto.PaymentConfirmRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "${api.base-url}")
public interface PaymentServiceClient {

    @PostMapping("/api/payments/confirm")
    void confirmPayment(@RequestBody PaymentConfirmRequest request);

}

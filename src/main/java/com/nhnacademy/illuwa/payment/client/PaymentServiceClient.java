package com.nhnacademy.illuwa.payment.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "payment-service", url = "${api.base-url}")
public interface PaymentServiceClient {
}

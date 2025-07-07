package com.nhnacademy.illuwa.cart.client;

import com.nhnacademy.illuwa.cart.dto.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "product-service", url = "${api.base-url}", contextId = "cartClient")
public interface CartServiceClient {


}

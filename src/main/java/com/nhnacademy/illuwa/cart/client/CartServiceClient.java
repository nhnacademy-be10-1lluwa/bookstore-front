package com.nhnacademy.illuwa.cart.client;

import com.nhnacademy.illuwa.cart.dto.BookCartRequest;
import com.nhnacademy.illuwa.cart.dto.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service", url = "${api.base-url}", contextId = "cartClient")
public interface CartServiceClient {

    @GetMapping("/api/cart")
    CartResponse getCart();

    @DeleteMapping("/api/cart")
    void clearCart();

    @PostMapping("/api/cart/book")
    BookCartRequest addBookCart(@RequestBody BookCartRequest bookCartRequest);

    @DeleteMapping("/api/cart/book")
    void removeBookCart(@RequestBody BookCartRequest bookCartRequest);
}

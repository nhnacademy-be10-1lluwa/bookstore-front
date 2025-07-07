package com.nhnacademy.illuwa.cart.service;

import com.nhnacademy.illuwa.book.client.ProductServiceClient;
import com.nhnacademy.illuwa.cart.client.CartServiceClient;
import com.nhnacademy.illuwa.cart.dto.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartServiceClient productServiceClient;


    public CartResponse getCart() {
        return productServiceClient.getCart();
    }

    public void clearCart() {
        productServiceClient.clearCart();
    }
}

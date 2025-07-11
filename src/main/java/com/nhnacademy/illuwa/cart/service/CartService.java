package com.nhnacademy.illuwa.cart.service;

import com.nhnacademy.illuwa.cart.client.CartServiceClient;
import com.nhnacademy.illuwa.cart.dto.BookCartRequest;
import com.nhnacademy.illuwa.cart.dto.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartServiceClient cartServiceClient;

    public CartResponse getCart() {
        return cartServiceClient.getCart();
    }

    public void clearCart() {
        cartServiceClient.clearCart();
    }

    public void addBookCart(BookCartRequest request) {
        cartServiceClient.addBookCart(request);
    }

    public void removeBookCart(BookCartRequest request) {
        cartServiceClient.removeBookCart(request); }
}

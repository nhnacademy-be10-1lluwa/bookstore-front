package com.nhnacademy.illuwa.cart.controller;

import com.nhnacademy.illuwa.cart.dto.BookCartResponse;
import com.nhnacademy.illuwa.cart.dto.CartResponse;
import com.nhnacademy.illuwa.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartService cartService;

    @GetMapping("/cart")
    public String getCart(Model model) {

        CartResponse cartResponse = cartService.getCart();
        List<BookCartResponse> books = cartResponse.getBooks();

        model.addAttribute("books", books);

        return "user/cart";
    }

    @DeleteMapping("/cart")
    public void deleteCart() {
        cartService.clearCart();
    }
}

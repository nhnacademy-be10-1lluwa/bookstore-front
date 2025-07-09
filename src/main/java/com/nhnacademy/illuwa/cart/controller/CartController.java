package com.nhnacademy.illuwa.cart.controller;

import com.nhnacademy.illuwa.cart.dto.BookCartResponse;
import com.nhnacademy.illuwa.cart.dto.CartRequest;
import com.nhnacademy.illuwa.cart.dto.CartResponse;
import com.nhnacademy.illuwa.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartService cartService;

//    @GetMapping("/cart")
//    public String getCart(Model model) {
//
//        CartResponse cartResponse = cartService.getCart();
//        List<BookCartResponse> books = cartResponse.getBooks();
//
//        model.addAttribute("books", books);
//
//        return "user/cart";
//    }

    // 뷰 렌더링 (브라우저 직접 접근)
    @GetMapping(value = "/cart",produces = MediaType.TEXT_HTML_VALUE)
    public String viewCartPage(Model model) {
        CartResponse dto = cartService.getCart();
        model.addAttribute("cart", dto);
        return "user/cart";  // Thymeleaf 뷰
    }

    // JSON 응답 (JS fetch 또는 AJAX)
    @GetMapping(value = "/cart", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CartResponse getCartJson() {
        return cartService.getCart();
    }


    @DeleteMapping("/cart")
    public void deleteCart() {
        cartService.clearCart();
    }
}

package com.nhnacademy.illuwa.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    @GetMapping("/cart")
    public String cart() {
        return "user/cart";
    }
}

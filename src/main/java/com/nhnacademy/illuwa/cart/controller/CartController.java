package com.nhnacademy.illuwa.cart.controller;

import com.nhnacademy.illuwa.cart.dto.BookCartRequest;
import com.nhnacademy.illuwa.cart.dto.CartResponse;
import com.nhnacademy.illuwa.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartService cartService;

    @GetMapping("/cart")
    public String viewCartPage(Model model) {
        CartResponse cartResponse = cartService.getCart();
        model.addAttribute("cartResponse", cartResponse);
        return "user/cart";
    }

    @DeleteMapping("/cart")
    @ResponseBody
    public ResponseEntity<Void> deleteCart() {
       cartService.clearCart();
       return ResponseEntity.noContent().build();
    }

    @PostMapping("/cart/add")
    public String addBookToCart(@RequestParam("bookId") Long bookId,
                                @RequestParam("quantity") int quantity) {

        log.info("Received SSR cart add request - BookId: {}, Quantity: {}", bookId,quantity);

        BookCartRequest request = new BookCartRequest(null, bookId,quantity);
        cartService.addBookCart(request);

        return "redirect:/cart";
    }

    @DeleteMapping("/cart/book")
    @ResponseBody
    public ResponseEntity<Void> removeCartBookAjax(@RequestBody BookCartRequest request) {
        log.info("Received AJAX cart remove request - BookId: {}", request.getBookId());

        cartService.removeBookCart(request);

        return ResponseEntity.noContent().build();

    }
}

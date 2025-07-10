package com.nhnacademy.illuwa.cart.controller;

import com.nhnacademy.illuwa.cart.client.CartServiceClient;
import com.nhnacademy.illuwa.cart.dto.BookCartRequest;
import com.nhnacademy.illuwa.cart.dto.BookCartResponse;
import com.nhnacademy.illuwa.cart.dto.CartRequest;
import com.nhnacademy.illuwa.cart.dto.CartResponse;
import com.nhnacademy.illuwa.cart.service.CartService;
import com.nhnacademy.illuwa.common.exception.BackendApiException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartService cartService;
    private final CartServiceClient cartServiceClient;

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

    // SSR 방식의 장바구니 페이지 렌더링
    @GetMapping("/cart")
    public String viewCartPage(Model model) {
        CartResponse cartResponse = cartService.getCart();
        model.addAttribute("cartResponse", cartResponse); // 모델에 데이터 추가
        return "user/cart"; // user/cart.html 템플릿 반환 (이 HTML에 데이터를 채워줌)
    }

    @DeleteMapping("/cart")
    @ResponseBody
    public ResponseEntity<Void> deleteCart() {
       cartService.clearCart();
       return ResponseEntity.noContent().build();
    }

    @PostMapping("/cart/add")
    public String addBookToCart(@RequestParam("bookId") Long bookId,
                                @RequestParam("amount") int amount,
                                RedirectAttributes redirectAttributes) { // 리다이렉트 시 메시지 전달용

        log.info("Received SSR cart add request - BookId: {}, Amount: {}", bookId, amount);

        // BookCartRequest DTO 생성
        BookCartRequest request = new BookCartRequest(null, bookId, amount); // memberId는 게이트웨이에서 주입될 것이므로 여기서는 null
        cartService.addBookCart(request);

        return "redirect:/books/list"; // 도서 목록 페이지로 리다이렉트
    }

}

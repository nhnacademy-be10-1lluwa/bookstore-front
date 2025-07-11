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
                                @RequestParam("amount") int amount) { // 리다이렉트 시 메시지 전달용

        log.info("Received SSR cart add request - BookId: {}, Amount: {}", bookId, amount);

        // BookCartRequest DTO 생성
        BookCartRequest request = new BookCartRequest(null, bookId, amount); // memberId는 게이트웨이에서 주입될 것이므로 여기서는 null
        cartService.addBookCart(request);

        return "redirect:/books/list"; // 도서 목록 페이지로 리다이렉트
    }

    // bookstore-front/cart/controller/CartController.java
    @DeleteMapping("/cart/book") // 이 경로를 /cart/book으로 유지
    @ResponseBody
    public ResponseEntity<Void> removeCartBookAjax(@RequestBody BookCartRequest request) { // ★ @RequestBody 사용
        log.info("Received AJAX cart remove request - BookId: {}", request.getBookId());

        // request 객체에 memberId가 설정되지 않을 수 있으므로, 여기서 게이트웨이에서 받은 X-USER-ID 헤더를 사용해야 합니다.
        // 현재 CartService에 memberId를 넘기는 메소드가 없으므로 CartService를 수정하거나
        // CartServiceClient를 통해 product-service의 deleteBook API를 직접 호출해야 합니다.

        // (가정) CartService가 memberId를 받아서 product-service에 전달한다고 가정
        // CartService의 removeBookCart 메소드 시그니처가 변경되어야 할 수도 있습니다.

        cartService.removeBookCart(request); // Feign Client를 통해 product-service로 요청 위임

        return ResponseEntity.noContent().build(); // 성공 시 204 No Content 응답 (AJAX에 적합)

    }
}

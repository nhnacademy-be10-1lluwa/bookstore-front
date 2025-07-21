package com.nhnacademy.illuwa.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.illuwa.cart.dto.BookCartRequest;
import com.nhnacademy.illuwa.cart.dto.BookCartResponse;
import com.nhnacademy.illuwa.cart.dto.CartResponse;
import com.nhnacademy.illuwa.cart.service.CartService;
import com.nhnacademy.illuwa.common.controller_advice.CategoryControllerAdvice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CartController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                        classes = {CategoryControllerAdvice.class})
        })
@AutoConfigureMockMvc(addFilters = false)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @MockBean
    private CartService cartService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("장바구니 조회")
    void viewCartPageTest() throws Exception {
        BookCartResponse testBook1 = new BookCartResponse(1L, "Test Book 1", 2, 10000, "http://example.com/img1.jpg");
        BookCartResponse testBook2 = new BookCartResponse(2L, "Test Book 2", 1, 5000, "http://example.com/img2.jpg");

        BigDecimal totalPrice = BigDecimal.valueOf(testBook1.getSalePrice()).multiply(BigDecimal.valueOf(testBook1.getAmount()))
                .add(BigDecimal.valueOf(testBook2.getSalePrice()).multiply(BigDecimal.valueOf(testBook2.getAmount())));

        CartResponse mockCartResponse = new CartResponse(
                1L,
                Arrays.asList(testBook1, testBook2),
                totalPrice
        );
        when(cartService.getCart()).thenReturn(mockCartResponse);

        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/cart"))
                .andExpect(model().attributeExists("cartResponse"))
                .andExpect(model().attribute("cartResponse", mockCartResponse));

        verify(cartService, times(1)).getCart();
    }

    @Test
    @DisplayName("장바구니 도서 등록")
    void addCartTest() throws Exception {

        BookCartRequest testRequest = new BookCartRequest(null, 1L, 5);
        doNothing().when(cartService).addBookCart(any(BookCartRequest.class));

        mockMvc.perform(post("/cart/add")
                .param("bookId", String.valueOf(testRequest.getBookId()))
                .param("quantity", String.valueOf(testRequest.getAmount())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));

        verify(cartService, times(1)).addBookCart(any(BookCartRequest.class));
    }

    @Test
    @DisplayName("장바구니 전체 삭제")
    void deleteCartTest() throws Exception {
        doNothing().when(cartService).clearCart();

        mockMvc.perform(delete("/cart")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(cartService, times(1)).clearCart();
    }

    @Test
    @DisplayName("장바구니 개 삭제")
    void updateCartTest() throws Exception {
        BookCartRequest testRequest = new BookCartRequest(1L, 1L, 1);
        doNothing().when(cartService).removeBookCart(any(BookCartRequest.class));

        mockMvc.perform(delete("/cart/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andExpect(status().isNoContent());

        verify(cartService, times(1)).removeBookCart(any(BookCartRequest.class));

    }


}


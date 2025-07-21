package com.nhnacademy.illuwa.cart.service;

import com.nhnacademy.illuwa.cart.client.CartServiceClient;
import com.nhnacademy.illuwa.cart.dto.BookCartRequest;
import com.nhnacademy.illuwa.cart.dto.BookCartResponse;
import com.nhnacademy.illuwa.cart.dto.CartResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    CartServiceClient cartServiceClient;

    @InjectMocks
    CartService cartService;

    @Test
    @DisplayName("CartService -> getCart() 테스트")
    void testGetCart() {
        BookCartResponse testBook1 = new BookCartResponse(1L, "Test Book 1", 3, 10000, "http://example.com/img1.jpg");
        BookCartResponse testBook2 = new BookCartResponse(2L, "Test Book 1", 1, 5000, "http://example.com/img2.jpg");

        BigDecimal totalPrice =
                BigDecimal.valueOf(testBook1.getSalePrice()).multiply(BigDecimal.valueOf(testBook1.getAmount()))
                        .add(BigDecimal.valueOf(testBook2.getSalePrice()).multiply(BigDecimal.valueOf(testBook2.getAmount())));

        CartResponse testCart = new CartResponse(
                1L,
                Arrays.asList(testBook1, testBook2),
                totalPrice
        );

        when(cartService.getCart()).thenReturn(testCart);

        CartResponse actualCart = cartService.getCart();

        assertEquals(testCart, actualCart);

        verify(cartServiceClient, times(1)).getCart();
    }

    @Test
    @DisplayName("CartService -> clearCart() 테스트")
    void testClearCart() {
        doNothing().when(cartServiceClient).clearCart();

        assertDoesNotThrow(() -> cartService.clearCart());

        verify(cartServiceClient, times(1)).clearCart();
    }

    @Test
    @DisplayName("CartService -> addBookCart() 테스트")
    void testAddBookCart() {
        BookCartRequest request = new BookCartRequest(1L, 1L, 3);

        when(cartServiceClient.addBookCart(any(BookCartRequest.class))).thenReturn(request);

        assertDoesNotThrow(() -> cartService.addBookCart(request));

        verify(cartServiceClient, times(1)).addBookCart(request);
    }

    @Test
    @DisplayName("CartService -> removeBookCart() 테스트")
    void testRemoveBookCart() {
        BookCartRequest request = new BookCartRequest(1L, 1L, 1);

        doNothing().when(cartServiceClient).removeBookCart(any(BookCartRequest.class));

        assertDoesNotThrow(() -> cartService.removeBookCart(request));

        verify(cartServiceClient, times(1)).removeBookCart(request);
    }

}


package com.nhnacademy.illuwa.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class CartResponse {

    private Long cartId;
    private List<BookCartResponse> bookCarts;
    private BigDecimal totalPrice;
}

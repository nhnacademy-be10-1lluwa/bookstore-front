package com.nhnacademy.illuwa.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookCartResponse {
    private Long bookId;
    private String title;
    private int amount;

    private int    salePrice;
    private String imgUrl;
}

package com.nhnacademy.illuwa.cart.dto;

import lombok.*;

@Data
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

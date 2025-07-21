package com.nhnacademy.illuwa.cart.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookCartRequest {

    private Long memberId;
    private Long bookId;
    // 수량
    private int amount;
}

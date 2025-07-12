package com.nhnacademy.illuwa.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookItemOrderDto {
    @JsonProperty("id")
    private Long bookId;
    private String title;
    private BigDecimal regularPrice;
    private BigDecimal salePrice;
}

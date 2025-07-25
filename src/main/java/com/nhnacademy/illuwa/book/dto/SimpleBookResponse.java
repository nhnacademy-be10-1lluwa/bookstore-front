package com.nhnacademy.illuwa.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleBookResponse {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String description;
    private String isbn;
    private BigDecimal salePrice;
    private BigDecimal regularPrice;
    private String cover;
    private String status;
}

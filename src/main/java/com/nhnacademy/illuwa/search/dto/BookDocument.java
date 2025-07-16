package com.nhnacademy.illuwa.search.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//BookDocument에 대응
public class BookDocument {
    private Long id;
    private String title;
    private String description;
    private String author;
    private String publisher;
    private String isbn;
    private BigDecimal salePrice;
    private String thumbnailUrl;
    private LocalDate publishedDate;
}
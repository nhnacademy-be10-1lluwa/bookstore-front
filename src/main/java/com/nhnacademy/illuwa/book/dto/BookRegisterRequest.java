package com.nhnacademy.illuwa.book.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRegisterRequest {
    private String title;
    private String author;
    private String publisher;
    private String contents;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String pubDate;
    private String isbn;
    private int regularPrice;
    private int salePrice;
    private String description;
    private MultipartFile imageFile;
    private Integer count;
    private Long categoryId;
}


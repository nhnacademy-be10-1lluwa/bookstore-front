package com.nhnacademy.illuwa.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRegisterRequest {
    private String title;
    private String contents;
    private String description;
}

package com.nhnacademy.illuwa.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewResponse {
    private Long reviewId;
    private String reviewTitle;
    private String reviewContent;
    private Integer reviewRating;
    private LocalDateTime reviewDate;
    private Long bookId;
    private Long memberId;
    private List<String> reviewImageUrls;
}

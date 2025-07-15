package com.nhnacademy.illuwa.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewLikeResponse {
    private boolean likedByMe;
    private Long likeCount;
}

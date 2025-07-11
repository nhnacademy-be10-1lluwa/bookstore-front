package com.nhnacademy.illuwa.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private Long commentId;
    private String commentContents;
    private LocalDateTime commentDate;
    private Long reviewId;
    private Long memberId;
}

package com.nhnacademy.illuwa.review.service;

import com.nhnacademy.illuwa.review.client.ReviewServiceClient;
import com.nhnacademy.illuwa.review.dto.CommentRequest;
import com.nhnacademy.illuwa.review.dto.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ReviewServiceClient reviewServiceClient;

    public CommentResponse createComment(Long bookId, Long reviewId, CommentRequest request) {

        return reviewServiceClient.createComment(bookId, reviewId, request);
    }

    public List<CommentResponse> getCommentList(Long bookId, Long reviewId) {

        return reviewServiceClient.getCommentList(bookId, reviewId);
    }

    public CommentResponse updateComment(Long bookId, Long reviewId, Long commentId, CommentRequest request) {

        return reviewServiceClient.updateComment(bookId, reviewId, commentId, request);
    }
}
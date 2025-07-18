package com.nhnacademy.illuwa.review.controller;

import com.nhnacademy.illuwa.review.dto.CommentRequest;
import com.nhnacademy.illuwa.review.dto.CommentResponse;
import com.nhnacademy.illuwa.review.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews/{review-id}/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @ResponseBody
    public CommentResponse createComment(@PathVariable(name = "review-id") long reviewId,
                                         @RequestBody @Valid CommentRequest request) {

        return commentService.createComment(reviewId, request);
    }

    @GetMapping
    @ResponseBody
    public List<CommentResponse> getCommentList(@PathVariable(name = "review-id") long reviewId) {

        return commentService.getCommentList(reviewId);
    }

    @PostMapping("/{comment-id}")
    @ResponseBody
    public CommentResponse updateComment(@PathVariable(name = "review-id") Long reviewId,
                                         @PathVariable(name = "comment-id") Long commentId,
                                         @RequestBody @Valid CommentRequest request) {

        return commentService.updateComment(reviewId, commentId, request);
    }
}

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
@RequestMapping("/books/{bookId}/reviews/{reviewId}/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @ResponseBody
    public CommentResponse createComment(@PathVariable Long bookId,
                                         @PathVariable Long reviewId,
                                         @RequestBody @Valid CommentRequest request) {

        return commentService.createComment(bookId, reviewId, request);
    }

    @GetMapping
    @ResponseBody
    public List<CommentResponse> getCommentList(@PathVariable Long bookId, @PathVariable Long reviewId) {
        return commentService.getCommentList(bookId, reviewId);
    }

    @PostMapping("/{commentId}")
    @ResponseBody
    public CommentResponse updateComment(@PathVariable Long bookId,
                                         @PathVariable Long reviewId,
                                         @PathVariable Long commentId,
                                         @RequestBody @Valid CommentRequest request) {

        return commentService.updateComment(bookId, reviewId, commentId, request);
    }
}

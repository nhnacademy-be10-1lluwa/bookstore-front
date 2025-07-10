package com.nhnacademy.illuwa.review.controller;

import com.nhnacademy.illuwa.review.client.ReviewServiceClient;
import com.nhnacademy.illuwa.review.dto.CommentRequest;
import com.nhnacademy.illuwa.review.dto.CommentResponse;
import com.nhnacademy.illuwa.user.member.client.MemberServiceClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books/{bookId}/reviews/{reviewId}/comments")
public class CommentController {
//    private final ReviewServiceClient reviewServiceClient;
//    private final MemberServiceClient memberServiceClient;
//
//    @PostMapping
//    public String createComment(Model model){
//        model
//        return "";
//    }
//
//    @GetMapping
//    public ResponseEntity<List<CommentResponse>> getCommentList(@PathVariable Long bookId,
//                                                                @PathVariable Long reviewId) {
//
//        Long memberId = memberServiceClient.getMember().getMemberId();
//        return reviewServiceClient.getCommentList(memberId, bookId, reviewId);
//    }
//
//    @PatchMapping("/{commentId}")
//    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long bookId,
//                                                         @PathVariable Long reviewId,
//                                                         @PathVariable Long commentId,
//                                                         @RequestBody @Valid CommentRequest request) {
//        Long memberId = memberServiceClient.getMember().getMemberId();
//        return reviewServiceClient.updateComment(memberId, bookId, reviewId, commentId, request);
//    }
}

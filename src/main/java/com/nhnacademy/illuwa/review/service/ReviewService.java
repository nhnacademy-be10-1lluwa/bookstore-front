package com.nhnacademy.illuwa.review.service;

import com.nhnacademy.illuwa.review.client.ReviewServiceClient;
import com.nhnacademy.illuwa.review.dto.ReviewRequest;
import com.nhnacademy.illuwa.review.dto.ReviewResponse;
import com.nhnacademy.illuwa.user.member.client.MemberServiceClient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService{
    private final ReviewServiceClient reviewServiceClient;
    private final MemberServiceClient memberServiceClient;

    public void createReview(ReviewRequest request, List<MultipartFile> images) throws Exception {
        reviewServiceClient.createReview(request, images);
    }

    public void updateReview(Long bookId, Long reviewId, Long memberId, ReviewRequest request, List<MultipartFile> images) throws Exception {
        reviewServiceClient.updateReview(bookId, reviewId, memberId, request, images);
    }

    public ReviewResponse getReview(Long bookId, Long reviewId, Long memberId) {
        return reviewServiceClient.getReviewDetails(bookId, reviewId, memberId);
    }
//    public<ReviewResponse> getReviewPages(Long bookId,  pageable, Long memberId) {
//
//    }
//

    public long getMemberId(){
        return memberServiceClient.getMember().getMemberId();
    }
}
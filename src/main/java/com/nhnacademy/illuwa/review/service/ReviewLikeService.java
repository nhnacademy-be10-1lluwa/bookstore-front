package com.nhnacademy.illuwa.review.service;

import com.nhnacademy.illuwa.review.client.ReviewServiceClient;
import com.nhnacademy.illuwa.review.dto.ReviewLikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewLikeService {
    private final ReviewServiceClient reviewServiceClient;

    public ReviewLikeResponse toggleLike(Long reviewId){
        return reviewServiceClient.toggleLike(reviewId);
    }

    public Map<Long, Long> getLikeCountsFromReviews(List<Long> reviewIds) {
        return reviewServiceClient.getLikeCountsFromReviews(reviewIds);
    }

    public List<Long> getMyLikedReviews(List<Long> reviewIds) {
        return reviewServiceClient.getMyLikedReviews(reviewIds);
    }
}

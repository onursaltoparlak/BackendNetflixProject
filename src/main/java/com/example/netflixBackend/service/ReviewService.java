package com.example.netflixBackend.service;

import com.example.netflixBackend.dto.ReviewRequest;
import com.example.netflixBackend.dto.ReviewResponse;
import java.util.List;

public interface ReviewService {
    ReviewResponse createReview(ReviewRequest request);
    ReviewResponse updateReview(Long reviewId, ReviewRequest request);
    void deleteReview(Long reviewId);
    List<ReviewResponse> getReviewsByContent(Long contentId);
    List<ReviewResponse> getMyReviews();
} 